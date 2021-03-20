package com.company.wish.list;

import com.company.wish.list.storage.Storage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class WishListServer {
    private static final String SERVER_HOST = "localhost";
    private static final int BUFFER_SIZE = 1024;

    private final Storage storage;
    private final int port;
    private boolean isRunning;

    public WishListServer(int port) {
        this.storage = new Storage();
        this.port = port;
        this.isRunning = false;
    }

    public void start() {
        isRunning = true;
        run();
    }

    public void stop() {
        isRunning = false;
    }

    private void run() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {

            serverSocketChannel.bind(new InetSocketAddress(SERVER_HOST, this.port));
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

            while (this.isRunning) {
                int readyChannels = selector.select();
                if (readyChannels == 0) {
                    // select() is blocking but may still return with 0, check javadoc
                    continue;
                }

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();

                    if (key.isReadable()) {
                        buffer.clear();
                        SocketChannel sc = (SocketChannel) key.channel();

                        handleIsReadable(sc, key, buffer);
                    } else if (key.isAcceptable()) {
                        handleIsAcceptable(selector, key);
                    }

                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            System.out.println("There is a problem with the server socket");
            e.printStackTrace();
        }
    }

    private void handleIsAcceptable(Selector selector, SelectionKey key) throws IOException {
        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
        SocketChannel accept = sockChannel.accept();
        accept.configureBlocking(false);
        accept.register(selector, SelectionKey.OP_READ);
    }

    private void handleIsReadable(SocketChannel sc, SelectionKey key, ByteBuffer buffer) throws IOException {

        String response;

        int bytesRead = sc.read(buffer);
        if(bytesRead != 0) {
            response = getResponse(buffer, sc, key);
        }
        else {
            response = "[ There is nothing to read ]";
        }

        sendResponseToBuffer(response, buffer, sc);
    }

    private String getResponse(ByteBuffer buffer, SocketChannel sc, SelectionKey key) throws IOException {
        buffer.flip();
        String message = new String(buffer.array(), 0, buffer.limit()).trim();

        System.out.println("Message [" + message + "] received from client " + sc.getRemoteAddress());

        try {
            String[] tokens = message.split(" ");

            String command = tokens[0];

            return switch (command) {
                case "post-wish" -> this.postWish
                        (Arrays.copyOfRange(tokens, 1, tokens.length));
                case "get-wish" -> this.getWish();
                case "disconnect" -> this.getDisconnected(sc, key);
                default -> "Unknown command";
            };
        } catch (IllegalStateException | IllegalArgumentException ex) {
            return ex.getMessage();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return "There was a problem";

        }
    }

    private void sendResponseToBuffer(String response, ByteBuffer buffer, SocketChannel sc) throws IOException {
        if(response != null) {

            buffer.clear();
            response = "[ " + response + " ]" + System.lineSeparator();

            buffer.put(response.getBytes());
            buffer.flip();

            while(buffer.hasRemaining()) {
                sc.write(buffer);
            }
        }
    }

    private String getWish() throws IllegalStateException {

        Map.Entry<String, String> entry = this.storage.getWish();

        return String.format("%s: [%s]", entry.getKey(), entry.getValue());
    }

    private String postWish(String[] arguments) throws IllegalArgumentException {
        if(arguments.length < 2) {
            throw new IllegalArgumentException("Not enough parameters! : " + Arrays.toString(arguments));
        }

        String person = arguments[0];
        String gift = Arrays.stream
                (Arrays.copyOfRange(arguments,1,arguments.length))
                .map(String::valueOf)
                .collect(Collectors.joining(" "));

        this.storage.add(person, gift);

        return String.format("Gift %s for student %s submitted successfully", gift, person);
    }

    private String getDisconnected(SocketChannel sc, SelectionKey key) throws IOException {

        sc.socket().close();
        key.cancel();
        return "Disconnected";
    }
}
