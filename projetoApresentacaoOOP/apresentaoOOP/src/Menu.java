import java.util.Scanner;

public class Menu {

    private final Scanner sc = new Scanner(System.in);
    private final Repository repo = new Repository(sc);

    public  void run() {

    boolean isRunning = true;

        while (isRunning) {

            System.out.println("\n===== MENU =====");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Buscar");
            System.out.println("4 - Excluir");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");

            int option = sc.nextInt();
            sc.nextLine(); 

            switch (option) {

                case 1:
                    repo.createProduto();
                    break;

                case 2:
                    repo.list();
                    break;

                case 3:
                    repo.find();
                    break;

                case 4:
                    repo.remove();
                    break;

                case 5:
                    isRunning = false;
                    System.out.println("Desligando sistema. Adeus!");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    
    }

}