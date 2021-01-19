package com.company;

import java.util.Arrays;

public class Main {

    public static void doSocialDistancing()
    {
        SocialDistancing soc = new SocialDistancing();

        System.out.println(soc.result);
    }

    public static void doSandwich()
    {
        Sandwich doSandwich = new Sandwich();

        System.out.println(Arrays.toString(doSandwich.ingredients));
    }

    public static void doRememberAll()
    {
        RememberAll doRememberAll = new RememberAll();

        System.out.println(Boolean.toString(doRememberAll.isNumberForgettable));
    }

    public static void main(String[] args) {
    //doSocialDistancing();
    //doSandwich();
    //doRememberAll();
    }
}
