package com.company.wish.list;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class WishListClient {
    private static final String[] testRequests = new String[]
                    {"post-wish Zdravko kolelo"
                    ,"post-wish Zdravko kon"
                    ,"post-wish Asen kolelo"
                    ,"post-wish Gosho mech"
                    ,"get-wish"
                    ,"get-wish"
                    ,"get-wish"
                    ,"disconnect"};

    private static final int SERVER_PORT = 4444;
    private static final String SERVER_HOST = "localhost";
    private static ByteBuffer buffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) {

        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));

            System.out.println("Connected to the server.");

            while (true) {
                System.out.print("Enter message: ");
                Thread.sleep(2000);
                String message = testRequests[new Random().nextInt(testRequests.length - 1)];
                //String message = scanner.nextLine(); // read a line from the console

                System.out.println("Sending message " + message + " to the server...");

                buffer.clear();
                buffer.put(message.getBytes()); // buffer fill
                buffer.flip();
                socketChannel.write(buffer); // buffer drain

                buffer.clear(); // switch to writing mode
                socketChannel.read(buffer); // buffer fill
                buffer.flip(); // switch to reading mode

                String reply = StandardCharsets.UTF_8.decode(buffer).toString();

                System.out.println(reply);

                if(message.equals("disconnect")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("There is a problem with the network communication");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
