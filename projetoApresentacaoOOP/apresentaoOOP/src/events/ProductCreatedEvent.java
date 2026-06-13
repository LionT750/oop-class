package events;

import models.Product;

public class ProductCreatedEvent extends Event {
    private Product product;

    public ProductCreatedEvent(Product product) {
        super("product-created");
        this.product = product;
    }

    public Product getProduct() { return product; }
}
