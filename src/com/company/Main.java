package com.company;

import java.io.*;
import java.util.*;

/*
* This program takes user input as decimal numbers and converts them into hexadecimal and vice versa.
* Author: Eduard Tarassov
* Version: 01/05/2014
 */

public class Main {
    private static List<String> inputNumbers = new ArrayList<String>(); // Container for all input numbers.
    private static Formatter fmt = new Formatter(); // We use formatter to contain all output. Makes possible to write to the file in the grid format.

    public static void main(String[] args) {
        fmt.format("%62s", "\n____________________________________________________________________\n");
        fmt.format("%1s  %-30s   %-30s %1s", "|", "input value", "output value", "|\n");
        fmt.format("%62s", "|==================================================================|\n");


        // Taking all the arguments provided by user and sending them to user input sort method to sort them.
        for (String argument : args)
            userInputSort(argument);

        System.out.println(fmt);
    }

    /*
    * This method is responsible for sorting commands typed by user.
     */
    private static void userInputSort(String argument) {
        if (argument.startsWith("-i"))
            // file input choice
            readFile(argument.substring(2)); // Passing file path to the readFile.
        else if (argument.startsWith("-o"))
            // output to file choice
            writeFile(argument.substring(2)); // Passing file path to the writeFile.
        else {
            inputNumbers = Arrays.asList(argument.split(",|\\ |\\;")); // Separating all the words between commas, spaces and semicolons, and putting everything into list of strings.
            processingNumbers();
        }
    }

    /*
    * This method contains main function that identifies and converts decimals into hexadecimals and vice versa.
     */
    private static void processingNumbers() {

        for (int i = 0; i < inputNumbers.size(); i++) {
            if (inputNumbers.get(i).startsWith("0x")) {
                try {
                    fmt.format("%1s  %-30s   %-30s %1s", "|", inputNumbers.get(i), Integer.parseInt(inputNumbers.get(i).substring(2), 16), "|\n");
                } catch (NumberFormatException e) {
                    fmt.format("%1s  %-30s   %-30s %1s", "|", "is not valid hexadecimal value", "", "|\n");
                }
            } else {
                try {
                    fmt.format("%1s  %-30s   %-30s %1s", "|", inputNumbers.get(i), "0x" + Integer.toHexString(Integer.parseInt(inputNumbers.get(i))).toUpperCase(), "|\n");
                } catch (NumberFormatException r) {
                    fmt.format("%1s  %-30s   %-30s %1s", "|", "is not valid decimal value", "", "|\n");
                }
            }

        }
    }

    /*
    * This method reads file line by line and passes all the values to inputNumbers list.
    * And calls method to process numbers.
    */
    private static void readFile(String filePath) {
        BufferedReader br = null;

        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader(filePath));

            while ((sCurrentLine = br.readLine()) != null)
                Collections.addAll(inputNumbers, sCurrentLine.split(",|\\ |\\;")); // Separating all the words between commas, spaces and semicolons, and putting everything into list of strings.

            // Removing all empty values.
            for (int i = 0; i < inputNumbers.size(); i++) {
                if (inputNumbers.get(i).equals(""))
                    inputNumbers.remove(i);
            }

            processingNumbers();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();  // Closing BufferedReader.
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /*
    * This method is responsible for the situation, if user types numbers into commands which he wants to convert or filename
    * to read from and convert, and after follows command -o<filepath>. So this method simply applies text to file <filepath>
    *     or creates new file (if not exist).
     */
    private static void writeFile(String filePath) {
        try {
            String data = " This content will append to the end of the file";

            File file = new File(filePath);

            // If file doesn't exists, then create it.
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWritter = new FileWriter(file.getName(), true); // true = append file
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            fmt.format("%62s", "|==================================================================|");
            bufferWritter.write(String.valueOf(fmt));

            bufferWritter.close(); // Closing BufferedWriter.

            // System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
