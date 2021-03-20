package com.company.wish.list.storage;

import com.company.wish.list.exceptions.StorageExceptions;

import java.util.*;
import java.util.stream.Collectors;

public class Storage {

    private final Map<String, ArrayList<String>> studentsCollection;
    private final List<String> studentList;

    public Storage() {
        this.studentsCollection = new HashMap<>();
        this.studentList = new ArrayList<>();
    }

    public void add(String receiver, String gift) throws IllegalArgumentException{

        if(receiver == null || gift == null) {
            throw new IllegalArgumentException(StorageExceptions.NULL_PARAMS);
        }

        if(!this.studentsCollection.containsKey(receiver)) {
            this.studentsCollection.put(receiver, new ArrayList<String>());
            studentList.add(receiver);
        }

        if(this.studentsCollection.get(receiver).contains(gift)) {
            throw new IllegalArgumentException
                    (String.format(StorageExceptions.GIFT_ALREADY_SENT_EXCEPTION, receiver));
        }

        this.studentsCollection.get(receiver).add(gift);
    }

    public Map.Entry<String, String> getWish() throws IllegalStateException{

        if(this.isEmpty()) {
            throw new IllegalStateException(StorageExceptions.STUDENT_LIST_EMPTY_EXCEPTION);
        }

        String randomStudent = getRandomStudent();
        String gifts = getFormattedGifts(randomStudent);

        this.removeStudent(randomStudent);

        return Map.entry(randomStudent, gifts);
    }

    public Boolean isEmpty() {
        return this.studentList.isEmpty();
    }

    private String getFormattedGifts(String student) {
        return this.studentsCollection.get(student).stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    private String getRandomStudent() {
        return studentList.get(new Random().nextInt(studentList.size()));
    }

    private void removeStudent(String student) {

        this.studentsCollection.remove(student);
        this.studentList.remove(student);
    }


}
