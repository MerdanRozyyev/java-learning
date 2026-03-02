package com.example;

import java.util.Scanner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        
        System.out.println("Hello, " + name + "! Welcome!");
        
        String input = "";
        
        while (!input.equalsIgnoreCase("exit")) {
            System.out.print("Type something (or 'exit' to quit): ");
            input = scanner.nextLine();
            
            if (!input.equalsIgnoreCase("exit")) {
                System.out.println(name + ", you typed: " + input);
            }
        }
        
        System.out.println("Goodbye, " + name + "!");
        scanner.close();
    }
}