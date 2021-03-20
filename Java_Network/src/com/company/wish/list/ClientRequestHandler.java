//package com.company.wish.list;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//import java.util.stream.Collectors;
//
//public class ClientRequestHandler implements Runnable{
//
//
//    private static final String GIFT_ALREADY_SENT_EXCEPTION
//            = "[ The same gift for student %s was already submitted ]";
//    private static final String STUDENT_LIST_EMPTY_EXCEPTION
//            = "[ There are no students present in the wish list ]";
//
//    private static final String GIFT_SENT = "[ Gift %s for student %s submitted successfully ]";
//    private static final String GET_PERSON_WITH_GIFTS = "[ %s: [%s] ]";
//
//    private final Socket socket;
//    private static final ConcurrentMap<String, ArrayList<String>> personPresentCollection
//            = new ConcurrentHashMap<>();
//    private static final List<String> studentList = new ArrayList<>();
//
//    public ClientRequestHandler(Socket socket) {
//        this.socket = socket;
//
//    }
//
//    @Override
//    public void run() {
//
//        try(PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
//            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//
//            try {
//                String inputLine;
//                while ((inputLine = input.readLine()) != null) {
//                    String handeledMessage = handleMessage(inputLine);
//                    output.println(handeledMessage);
//                    System.out.println(handleMessage(inputLine));
//                }
//            }
//            catch (IllegalArgumentException | IllegalStateException ex) {
//                output.println(ex.getMessage());
//            }
//        }
//        catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            try {
//                this.socket.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//
//
//    private String postWish(String[] tokens) throws IllegalArgumentException{
//        if(tokens.length < 2) {
//            throw new IllegalArgumentException
//                    (String.format(POST_TOKEN_EXCEPTION, Arrays.toString(tokens)));
//        }
//
//        String nameReceiver = tokens[0];
//        String present = Arrays.stream(Arrays.copyOfRange(tokens, 1, tokens.length))
//                .map(String::valueOf)
//                .collect(Collectors.joining());
//
//        if(!personPresentCollection.containsKey(tokens[0])) {
//            personPresentCollection.put(nameReceiver, new ArrayList<String>());
//            studentList.add(nameReceiver);
//        }
//
//        if(personPresentCollection.get(nameReceiver).contains(present)) {
//            throw new IllegalArgumentException
//                    (String.format(GIFT_ALREADY_SENT_EXCEPTION, nameReceiver));
//        }
//
//        personPresentCollection.get(nameReceiver).add(present);
//        return String.format(GIFT_SENT , present, nameReceiver);
//    }
//
//    private String getWish() throws IllegalStateException{
//        if(personPresentCollection.isEmpty()) {
//            throw new IllegalStateException(STUDENT_LIST_EMPTY_EXCEPTION);
//        }
//
//        String randomStudent = studentList.get(new Random().nextInt(studentList.size()));
//        String studentGifts = personPresentCollection.get(randomStudent).stream()
//                .map(String::valueOf)
//                .collect(Collectors.joining(", "));
//        personPresentCollection.remove(randomStudent);
//
//        return String.format(GET_PERSON_WITH_GIFTS, randomStudent, studentGifts);
//    }
//
//}
