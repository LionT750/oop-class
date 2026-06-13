import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Repository {

    private static final Map<Long, Produto> db = new HashMap<>();

    private final Scanner sc;

    public enum ProdutoType {
    BEBIDA,
    CARNE
    }

    public Repository(Scanner sc) {
        this.sc = sc;
    }

    public void createProduto() {

        ProdutoType type = askType();

        Produto produto = null;

        switch (type) {

            case BEBIDA:
                produto = createBebida();
                break;

            case CARNE:
                produto = createCarne();
                break;
        }

        db.put(produto.getId(), produto);
        System.out.println("Produto criado com sucesso!");
    }

    private String readString(String label) {

    while (true) {
        System.out.print(label);
        String value = sc.nextLine();

        if (!value.isBlank()) return value;

        System.out.println("Valor inválido. Tente novamente.");
    }
    }

    private double readDouble(String label) {

    while (true) {
        System.out.print(label);

        try {
            double value = Double.parseDouble(sc.nextLine());

            if (value > 0) return value;

            System.out.println("Valor deve ser maior que 0.");

        } catch (NumberFormatException e) {
            System.out.println("Número inválido. Tente novamente.");
        }
    }
    
    }

    private ProdutoType askType() {

    while (true) {

        System.out.println("Escolha o tipo:");
        System.out.println("1 - Bebida");
        System.out.println("2 - Carne");
        System.out.print("Opção: ");

        String input = sc.nextLine();

        switch (input) {
            case "1":
                return ProdutoType.BEBIDA;
            case "2":
                return ProdutoType.CARNE;
            default:
                System.out.println("Opção inválida.");
        }
    }
    }

    private Bebida createBebida() {

    String name = readString("Nome: ");
    BigDecimal price = new BigDecimal(readString("Preço: "));
    double stock = readDouble("Total de litros em estoque");

    double volumePerBottle = readDouble("Volume por garrafa: ");
    double volumeStock = stock;

    BebidaBrand brand = askBebidaBrand();

    return new Bebida(name, price, stock, volumePerBottle, volumeStock, brand);
    }

    private Carne createCarne() {

    String name = readString("Nome: ");
    BigDecimal price = new BigDecimal(readString("Preço: "));
    double stock = readDouble("Status estoque: ");

    double weightPerPackage = readDouble("Peso por pacote: ");
    double stockWeight = readDouble("Peso total: ");

    CarneBrand brand = askCarneBrand();

    return new Carne(name, price, stock, weightPerPackage, stockWeight, brand);
    }

    private BebidaBrand askBebidaBrand() {

    while (true) {
        System.out.print("Brand bebida: ");
        String input = sc.nextLine().toUpperCase();

        try {
            return BebidaBrand.valueOf(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Brand inválida.");
        }
    }
    }

    private CarneBrand askCarneBrand() {

    while (true) {
        System.out.print("Brand carne: ");
        String input = sc.nextLine().toUpperCase();

        try {
            return CarneBrand.valueOf(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Brand inválida.");
        }
    }
    }

    public void list() {
    for (Produto p : db.values()) {
        System.out.println(p.getId() + " - " + p.getStock());
    }
    }

    private long readLong(String label) {

    while (true) {
        System.out.print(label);

        try {
            return Long.parseLong(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Digite um número.");
        }
    }
    }




    public Produto find() {

    while (true) {

        long id = readLong("Digite o ID do produto (ou 0 para cancelar): ");

        if (id == 0) {
            System.out.println("Busca cancelada.");
            return null;
        }

        Produto p = db.get(id);

        if (p != null) {
            System.out.println("Encontrado: " + p.getStock());
            return p;
        }

        System.out.println("Produto não encontrado. Tente novamente.");
    }
    }

    public void remove() {

    while (true) {

        long id = readLong("Digite o ID para remover (ou 0 para cancelar): ");

        if (id == 0) {
            System.out.println("Operação cancelada.");
            return;
        }

        Produto p = db.get(id);

        if (p == null) {
            System.out.println("Produto não encontrado.");
            continue;
        }

        System.out.println("Produto encontrado: " + p.getStock());
        System.out.print("Confirmar remoção? (s/n): ");

        String confirm = sc.nextLine().trim().toLowerCase();

        if (confirm.equals("s")) {
            db.remove(id);
            System.out.println("Produto removido com sucesso.");
            return;
        } else {
            System.out.println("Remoção cancelada.");
            return;
        }
    }
}
}