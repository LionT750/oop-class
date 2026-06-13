package utilitarios;

import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class LeitorConsole {
    public static class Cancelado extends RuntimeException {}

    private Scanner scanner;

    public LeitorConsole(Scanner scanner) {
        this.scanner = scanner;
    }

    public int lerInt() {
        while (true) {
            try {
                if (GraphicsEnvironment.isHeadless()) {
                    return Integer.parseInt(scanner.nextLine());
                }
                String input = JOptionPane.showInputDialog("Digite um numero:");
                if (input == null) throw new Cancelado();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Numero invalido. Tente novamente.");
            } catch (HeadlessException e) {
                return Integer.parseInt(scanner.nextLine());
            }
        }
    }

    public String lerStringOpcional() {
        try {
            if (GraphicsEnvironment.isHeadless()) {
                return scanner.nextLine().trim();
            }
            String valor = JOptionPane.showInputDialog("Entrada:");
            if (valor == null) throw new Cancelado();
            return valor.trim();
        } catch (HeadlessException e) {
            return scanner.nextLine().trim();
        }
    }

    public String lerString() {
        while (true) {
            try {
                if (GraphicsEnvironment.isHeadless()) {
                    String valor = scanner.nextLine().trim();
                    if (!valor.isEmpty()) return valor;
                    System.out.println("O valor nao pode ser vazio. Tente novamente.");
                    continue;
                }
                String valor = JOptionPane.showInputDialog("Entrada:");
                if (valor == null) throw new Cancelado();
                valor = valor.trim();
                if (!valor.isEmpty()) return valor;
            } catch (HeadlessException e) {
                String valor = scanner.nextLine().trim();
                if (!valor.isEmpty()) return valor;
            }
            System.out.println("O valor nao pode ser vazio. Tente novamente.");
        }
    }

    public double lerDouble() {
        while (true) {
            try {
                if (GraphicsEnvironment.isHeadless()) {
                    return Double.parseDouble(scanner.nextLine());
                }
                String input = JOptionPane.showInputDialog("Digite um numero:");
                if (input == null) throw new Cancelado();
                input = input.replace(",", ".");
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Numero invalido. Tente novamente.");
            } catch (HeadlessException e) {
                return Double.parseDouble(scanner.nextLine());
            }
        }
    }
}
