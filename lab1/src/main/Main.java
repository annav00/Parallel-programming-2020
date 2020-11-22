package ru.spbstu.telematics.java;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String input_text;

        System.out.println("Input a name of file: ");
        String file_name = input.nextLine();

        System.out.println("Input a string to write: ");
        input_text = input.nextLine();

        RewriteToFile file = new RewriteToFile(file_name);
        file.WriteToFile(input_text);
    }
}
