package models;

public class Product {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private boolean active;

    public Product(Long id, String name, String description, double price, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.active = true;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return id + " | " + name + " | $" + String.format("%.2f", price) + " | stock: " + stock + " | " + (active ? "Active" : "Inactive");
    }
}
