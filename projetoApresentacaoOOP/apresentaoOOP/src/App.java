import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Inventory<Bebida> bebidas = new Inventory<Bebida>();
        Inventory<Comida> comidas = new Inventory<Comida>();
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu(bebidas,comidas,sc);
        
        menu.run();
    }
}
