package com.company;
//import java.util.Scanner;

public class SocialDistancing {
    //int numOfSeats;

    //Scanner myObj = new Scanner(System.in);

    int[] seats = {1, 0, 0, 0, 1};

    public static int maxDistance(int[] seats) {
        int bestDistance = 0;
        int currentMax = 0;
        boolean isSearching = false;
        boolean hasBack = false;
        if(seats[0] == 1)
            hasBack = true;

        for (int i = 0; i < seats.length; i++) {

            if(seats[i] == 0)
                isSearching = true;

            if(isSearching) {
                if (seats[i] == 0) {
                    ++currentMax;
                } else {
                    if(hasBack) {
                        boolean isOdd = (currentMax % 2 == 0);
                        currentMax = (currentMax + 2) / 2;

                        if(isOdd)
                            --currentMax;
                    }


                    if (currentMax > bestDistance)
                        bestDistance = currentMax;

                    currentMax = 0;
                    isSearching = false;
                }
            }
        }
        if (currentMax > bestDistance)
            bestDistance = currentMax;

        return bestDistance;
    }

    public int result = maxDistance(seats);
}
