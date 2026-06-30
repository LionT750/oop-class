package models;

import java.time.LocalDateTime;

public  class Offer {
    private Long id;
    private Product product;
    private int quantity;
    private LocalDateTime createdAt;

    public Offer(Long id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public  String getDeliveryType() {
        return "";
    }

    @Override
    public String toString() {
        return id + " | " + product.getName() + " x" + quantity + " | " + createdAt.toLocalDate();
    }
}
