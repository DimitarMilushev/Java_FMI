package com.company;
import java.util.Scanner;

public class RememberAll {
    static boolean isNumeric(String str)
    {
        if(str == null)
            return false;

        try
        {
            int num = Integer.parseInt(str);
            return true;
        }
        catch(NumberFormatException nfex)
        {
            return false;
        }
    }

    public static boolean isPhoneNumberForgettable(String phoneNumber)
    {
        if(phoneNumber.equals(""))
            return false;

        String separator = " ";
        if(phoneNumber.contains("-"))
            separator = "-";

        String[] pairs = phoneNumber.split(separator);

        for(int i = 0; i < pairs.length; i++)
        {
            if(!isNumeric(pairs[i]))
                return true;

            for (int j = i + 1; j < pairs.length; j++)
            {
                if(pairs[i].equals(pairs[j]))
                    return false;
                for (int iDigits = 0; iDigits < pairs[i].length(); iDigits++)
               {
                    for (int jDigits = 0; jDigits < pairs[j].length(); jDigits++)
                    {
                       if(pairs[i].charAt(iDigits) == pairs[j].charAt(jDigits))
                            return false;
                    }
                }
            }
        }

        return true;
    }

    Scanner scn = new Scanner(System.in);

    String number = scn.nextLine();
    boolean isNumberForgettable = isPhoneNumberForgettable(number);
}
