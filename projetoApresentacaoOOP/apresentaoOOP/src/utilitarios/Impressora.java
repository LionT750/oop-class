package utilitarios;

public class Impressora {
    public static void cabecalho(String titulo) {
        System.out.println("\n=== " + titulo + " ===");
    }

    public static void separador() {
        System.out.println("-----------------------------");
    }

    public static void erro(String mensagem) {
        System.out.println("[ERRO] " + mensagem);
    }

    public static void sucesso(String mensagem) {
        System.out.println("[OK] " + mensagem);
    }

    public static void prompt(String mensagem) {
        System.out.print(">> " + mensagem);
    }
}
