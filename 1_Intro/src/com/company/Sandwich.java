package com.company;
import java.util.Scanner;

public class Sandwich {

    static int[] findBread(String[] input)
    {
        int[] breadIndexes = new int[2];
        boolean isSecond = false;

        for(int i = 0; i < input.length; i++)
        {
            if(input[i].contains("bread"))
            {
                if(isSecond)
                    breadIndexes[1] = i;
                else {
                    breadIndexes[0] = i;
                    isSecond = true;
                }
            }
        }

        return breadIndexes;
    }
    static String getIngredientFromBread(String input, boolean isSuffix)
    {
        int indexOfBread = input.indexOf("bread");
        String ingredient ;

        if(isSuffix) {
            if(indexOfBread < input.length())
                ingredient = input.substring(indexOfBread + 5, input.length());
            else
                ingredient = "olives";
        }
        else
        {
            if(indexOfBread > 0)
                ingredient = input.substring(0, indexOfBread);
            else
                ingredient = "olives";
        }

        return ingredient;
    }
    public static String[] extractIngredients(String sandwich)
    {
        String[] input = sandwich.split("-");


        int[] breadIndexes = findBread(input);
        if(breadIndexes[0] == breadIndexes[1] || breadIndexes[0] > breadIndexes[1])
            return new String[]{};

        int olives = 0;
        for (int i = breadIndexes[0]; i <= breadIndexes[1]; i++)
        {
            if(input[i].equals("olives"))
                olives++;
        }

        String[] gatheredIng = new String[((breadIndexes[1] + 1) - breadIndexes[0]) - olives];
        int gatheredIndex = 0;



        for(int i = breadIndexes[0]; i <= breadIndexes[1]; i++)
        {
            if(i == breadIndexes[0] || i == breadIndexes[1])
            {
                boolean isSuffix = (i == breadIndexes[0]);

                String ingredient = getIngredientFromBread(input[i], isSuffix);

                if(!ingredient.equals("olives"))
                    gatheredIng[gatheredIndex++] = ingredient;

                continue;
            }

            if(!input[i].equals("olives"))
                gatheredIng[gatheredIndex++] = input[i];
        }

        for(int i = 0; i < gatheredIndex; ++i) {
            for (int j = i + 1; j < gatheredIndex; ++j) {
                if (gatheredIng[i].compareTo(gatheredIng[j]) > 0) {

                    // swap words[i] with words[j[
                    String temp = gatheredIng[i];
                    gatheredIng[i] = gatheredIng[j];
                    gatheredIng[j] = temp;
                }
            }
        }
        return gatheredIng;
    }

    Scanner scn = new Scanner(System.in);

    String input = scn.nextLine();
    String[] ingredients = extractIngredients(input);
}
