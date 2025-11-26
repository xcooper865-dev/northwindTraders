package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class ConsoleHelper {
    private static Scanner scanner = new Scanner(System.in);

    public static int promptForInt(String prompt) {
        System.out.print(prompt + ": ");
        int result = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return result;
    }

    public static double promptForDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                String input = scanner.nextLine();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    public static String promptForString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    public static LocalDate promptForDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                String dateAsString = scanner.nextLine();
                return LocalDate.parse(dateAsString);
            } catch (Exception ex) {
                System.out.println("Invalid entry! Please enter a valid date (YYYY-MM-DD).");
            }
        }
    }

    public static LocalTime promptForTime(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                String timeAsString = scanner.nextLine();
                return LocalTime.parse(timeAsString);
            } catch (Exception ex) {
                System.out.println("Invalid entry! Please enter a valid time (HH:MM:SS).");
            }
        }
    }
}
