package data;

import java.util.ArrayList;
import java.util.List;
import models.Offer;
import models.Product;
import models.SalesDigital;
import models.SalesPhysical;

public class InMemoryDatabase {
    private static InMemoryDatabase instance;

    public List<Product> products = new ArrayList<>();
    public List<Offer> offers = new ArrayList<>();
    public List<SalesPhysical> physicalSales = new ArrayList<>();
    public List<SalesDigital> digitalSales = new ArrayList<>();

    private InMemoryDatabase() {}

    public static InMemoryDatabase getInstance() {
        if (instance == null) {
            instance = new InMemoryDatabase();
        }
        return instance;
    }
}
