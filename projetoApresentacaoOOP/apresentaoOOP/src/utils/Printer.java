package utils;

public class Printer {
    public static void header(String title) {
        System.out.println("\n=== " + title + " ===");
    }

    public static void separator() {
        System.out.println("-----------------------------");
    }

    public static void error(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void success(String message) {
        System.out.println("[OK] " + message);
    }

    public static void prompt(String message) {
        System.out.print(">> " + message);
    }
}
