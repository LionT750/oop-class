import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Models {
   public static Inventory<Bebida> bebidas = new Inventory<>();
   public static Inventory<Comida> comidas = new Inventory<>();
}

class Inventory<T extends BaseModel> {

    private List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public void remove(T item) {
        items.remove(item);
    }

    public void getItems() {
        if (items.isEmpty()) {
        System.out.println("Nenhum item cadastrado.");
    }

    for (T item : items) {
        System.out.println(item.toString());
    }
    }
}

abstract class BaseModel {

    protected Long id;

    protected Instant createdAt;
    protected Instant updatedAt;

    protected String createdBy;
    protected String updatedBy;

    protected boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

class Bebida extends BaseModel {
    private String nome;
    private String marca;
    private double preco;
    private double volume; 
    private String tipo;
   
    public Bebida(String nome, String marca, double preco, double volume, String tipo) {
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.volume = volume;
        this.tipo = tipo;
    }   


    
}

class Comida extends BaseModel {
    private String nome;
    private String marca;
    private double preco;
    private String tipo;
    
    public Comida(String nome, String marca, double preco, String tipo) {
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    } 

    
}
