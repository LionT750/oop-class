package events;

import models.Product;

public class ProductDeletedEvent extends Event {
    private Product product;

    public ProductDeletedEvent(Product product) {
        super("product-deleted");
        this.product = product;
    }

    public Product getProduct() { return product; }
}
