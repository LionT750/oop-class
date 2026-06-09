import java.util.Scanner;

public class Menu {
    private final Inventory<Bebida> bebidas;
    private final Inventory<Comida> comidas;
    private final Scanner sc;

    public Menu(Inventory<Bebida> bebidas, Inventory<Comida> comidas, Scanner sc) {
        this.bebidas = bebidas;
        this.comidas = comidas;
        this.sc = sc;
    }


    public void run() {
        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1 - Cadastrar Bebida");
            System.out.println("2 - Cadastrar Comida");
            System.out.println("3 - Listar Bebidas");
            System.out.println("4 - Listar Comidas");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            int option = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (option) {

                case 1 -> cadastrarBebida();

                case 2 -> cadastrarComida();

                case 3 -> listarBebidas();

                case 4 -> listarComidas();

                case 0 -> {
                    System.out.println("Saindo...");
                    return;
                }

                default -> System.out.println("Opção inválida!");
            }
        }
    }

    public void cadastrarBebida() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Marca: ");
        String marca = sc.nextLine();

        System.out.print("Preco: ");
        String input = sc.nextLine();
        double preco = Double.parseDouble(input);

        System.out.print("Volume: ");
        input = sc.nextLine();
        double volume = Double.parseDouble(input);

        System.out.print("Tipo: ");
        String tipo = sc.next();

        Bebida b = new Bebida(nome, marca, preco, volume, tipo);

        bebidas.add(b);

    }

    public void cadastrarComida() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Marca: ");
        String marca = sc.nextLine();

        System.out.print("Preco: ");
        double preco = sc.nextDouble();

        System.out.print("Tipo: ");
        String tipo = sc.next();

        Comida b = new Comida(nome, marca, preco, tipo);

        comidas.add(b);

    }

    public void listarBebidas(){
        bebidas.getItems();
    }

    public void listarComidas(){
        comidas.getItems();
    }
}

