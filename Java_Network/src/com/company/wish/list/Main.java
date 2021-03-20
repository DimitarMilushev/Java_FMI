package com.company.wish.list;

public class Main {

    public static void main(String[] args) {

        WishListServer ws = new WishListServer(4444);

        ws.start();

//        try {
//            Thread.sleep(60 * 1000);
//            ws.stop();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
}
