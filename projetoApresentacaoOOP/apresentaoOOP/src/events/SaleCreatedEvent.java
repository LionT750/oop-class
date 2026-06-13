package events;

import models.Offer;

public class SaleCreatedEvent extends Event {
    private Offer sale;

    public SaleCreatedEvent(Offer sale) {
        super("sale-created");
        this.sale = sale;
    }

    public Offer getSale() { return sale; }
}
