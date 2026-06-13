package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import data.InMemoryDatabase;
import models.Offer;
import models.Product;
import models.SalesDigital;
import models.SalesPhysical;

public class Repository {
    private static Repository instance;
    private InMemoryDatabase db;

    private Repository() {
        this.db = InMemoryDatabase.getInstance();
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public void saveProduct(Product product) {
        db.products.add(product);
    }

    public Optional<Product> findProductById(Long id) {
        return db.products.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public List<Product> findAllProducts() {
        return new ArrayList<>(db.products);
    }

    public void deleteProduct(Product product) {
        db.products.remove(product);
    }

    public void updateProduct(Product updated) {
        for (int i = 0; i < db.products.size(); i++) {
            if (db.products.get(i).getId().equals(updated.getId())) {
                db.products.set(i, updated);
                return;
            }
        }
    }

    public int countProducts() {
        return db.products.size();
    }

    public void clearProducts() {
        db.products.clear();
    }

    public void savePhysicalSale(SalesPhysical sale) {
        db.physicalSales.add(sale);
        db.offers.add(sale);
    }

    public void saveDigitalSale(SalesDigital sale) {
        db.digitalSales.add(sale);
        db.offers.add(sale);
    }

    public List<SalesPhysical> findAllPhysicalSales() {
        return new ArrayList<>(db.physicalSales);
    }

    public List<SalesDigital> findAllDigitalSales() {
        return new ArrayList<>(db.digitalSales);
    }

    public List<Offer> findAllSales() {
        return new ArrayList<>(db.offers);
    }

    public Optional<Offer> findSaleById(Long id) {
        return db.offers.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public int countSales() {
        return db.offers.size();
    }

    public void clearSales() {
        db.offers.clear();
        db.physicalSales.clear();
        db.digitalSales.clear();
    }

    public void resetDatabase() {
        db.products.clear();
        db.offers.clear();
        db.physicalSales.clear();
        db.digitalSales.clear();
    }
}
