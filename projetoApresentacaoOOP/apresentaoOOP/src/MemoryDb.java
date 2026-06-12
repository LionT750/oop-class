import java.util.HashMap;
import java.util.Map;

public abstract class MemoryDb {

    private static final Map<Long, Produto> estoque = new HashMap<>();
    

    public static void addStock(Long id, double qtd) {

        double currentStock = estoque.get(id).getStock();
        estoque.get(id).setStock(qtd + currentStock);

    }

    public static void removeStock(Long id, double qtd) {

        double currentStock = estoque.get(id).getStock();
        estoque.get(id).setStock(currentStock - qtd);

    }


    public static void cadastrarProduto(Produto produto) {
        estoque.put(produto.getId(), produto);
    }
}
