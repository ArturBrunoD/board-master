package com.board.util;

import java.util.Scanner;

public class InputHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Valor inválido. Digite um número: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }

    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // outros métodos...
}