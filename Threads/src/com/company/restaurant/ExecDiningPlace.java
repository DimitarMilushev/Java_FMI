//package com.company.restaurant;
//
//import java.util.Collections;
//import java.util.PriorityQueue;
//import java.util.Queue;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
//public class ExecDiningPlace implements Restaurant{
//
//    private ExecutorService service;
//    private Queue<Future<Order>> orders;
//
//    public ExecDiningPlace(int chefsCount) {
//        this.service = Executors.newFixedThreadPool(chefsCount);
//        this.orders = new PriorityQueue<>(Collections.reverseOrder());
//    }
//
//    @Override
//    public synchronized void submitOrder(Order order) {
//
//
//        this.service.submit(order);
//    }
//
//    @Override
//    public Order nextOrder() {
//        service.
//    }
//
//    @Override
//    public int getOrdersCount() {
//        return 0;
//    }
//
//    @Override
//    public Chef[] getChefs() {
//        return new Chef[0];
//    }
//
//    @Override
//    public void close() {
//    }
//}
