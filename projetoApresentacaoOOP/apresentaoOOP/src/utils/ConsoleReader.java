package utils;

import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class ConsoleReader {
    public static class Cancelled extends RuntimeException {}

    private Scanner scanner;

    public ConsoleReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInt() {
        while (true) {
            try {
                if (GraphicsEnvironment.isHeadless()) {
                    return Integer.parseInt(scanner.nextLine());
                }
                String input = JOptionPane.showInputDialog("Enter a number:");
                if (input == null) throw new Cancelled();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            } catch (HeadlessException e) {
                return Integer.parseInt(scanner.nextLine());
            }
        }
    }

    public String readStringOptional() {
        try {
            if (GraphicsEnvironment.isHeadless()) {
                return scanner.nextLine().trim();
            }
            String value = JOptionPane.showInputDialog("Input:");
            if (value == null) throw new Cancelled();
            return value.trim();
        } catch (HeadlessException e) {
            return scanner.nextLine().trim();
        }
    }

    public String readString() {
        while (true) {
            try {
                if (GraphicsEnvironment.isHeadless()) {
                    String value = scanner.nextLine().trim();
                    if (!value.isEmpty()) return value;
                    System.out.println("Value cannot be empty. Try again.");
                    continue;
                }
                String value = JOptionPane.showInputDialog("Input:");
                if (value == null) throw new Cancelled();
                value = value.trim();
                if (!value.isEmpty()) return value;
            } catch (HeadlessException e) {
                String value = scanner.nextLine().trim();
                if (!value.isEmpty()) return value;
            }
            System.out.println("Value cannot be empty. Try again.");
        }
    }

    public double readDouble() {
        while (true) {
            try {
                if (GraphicsEnvironment.isHeadless()) {
                    return Double.parseDouble(scanner.nextLine());
                }
                String input = JOptionPane.showInputDialog("Enter a number:");
                if (input == null) throw new Cancelled();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            } catch (HeadlessException e) {
                return Double.parseDouble(scanner.nextLine());
            }
        }
    }
}
