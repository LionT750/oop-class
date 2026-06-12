import java.util.concurrent.atomic.AtomicLong;
import java.math.BigDecimal;

public abstract class Produto {
    private static final AtomicLong counter = new AtomicLong(1000); 
    private final long id;
    private String name;
    private BigDecimal price;
    private double stock;

    public Produto(String name, BigDecimal price, double stock) {
        this.id = counter.incrementAndGet(); // Thread-safe unique ID
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getStock(){
        return stock;
    }

    public abstract String showStock();
}
