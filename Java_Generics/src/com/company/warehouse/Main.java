package com.company.warehouse;

import java.time.LocalDateTime;


public class Main {

    public static void main(String[] args) {
	    MJTExpressWarehouse<String, String> mjt
            = new MJTExpressWarehouse(8, 2);

        try {
            mjt.submitParcel("Label1", "Parcel1", LocalDateTime.now().minusDays(5));
            mjt.submitParcel("Label2", "Parcel55", LocalDateTime.now().minusDays(2));
            mjt.submitParcel("Label3", "Parcel545", LocalDateTime.now().minusDays(10));

            System.out.println(mjt.deliverParcelsSubmittedAfter(null));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}