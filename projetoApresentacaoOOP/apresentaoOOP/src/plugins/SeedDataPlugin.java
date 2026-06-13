package plugins;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import menu.FunctionalityContext;
import menu.MenuFunctionality;
import menu.Plugin;
import models.Product;
import models.SalesDigital;
import models.SalesPhysical;
import repository.Repository;
import utils.IdGenerator;
import utils.Printer;

public class SeedDataPlugin implements Plugin {
    private Repository repo;

    public SeedDataPlugin(FunctionalityContext context) {
        this.repo = context.repository;
    }

    @Override
    public String getId() { return "seed-data-plugin"; }

    @Override
    public String getName() { return "Seed Data"; }

    @Override
    public String getDescription() { return "Generate test data"; }

    @Override
    public List<MenuFunctionality> getFunctionalities() {
        return Arrays.asList(new SeedTestData());
    }

    private class SeedTestData implements MenuFunctionality {
        public String getId() { return "seed-test-data"; }
        public String getLabel() { return "Seed Test Data (10 products + sales)"; }
        public String getDescription() { return "Create 10 products, offers, and sales for testing"; }
        public void execute() {
            Product[] products = new Product[10];
            String[][] productData = {
                {"Notebook", "High-performance laptop", "4599.90", "15"},
                {"Mouse", "Wireless optical mouse", "89.90", "50"},
                {"Keyboard", "Mechanical keyboard RGB", "299.99", "30"},
                {"Monitor", "27-inch 4K display", "2499.00", "10"},
                {"Headset", "Noise-canceling headphones", "599.90", "20"},
                {"Webcam", "Full HD webcam", "349.50", "25"},
                {"Tablet", "10-inch Android tablet", "1899.00", "12"},
                {"Smartwatch", "Fitness smartwatch", "1299.00", "18"},
                {"Speaker", "Bluetooth speaker portable", "449.90", "22"},
                {"Charger", "USB-C fast charger 65W", "159.00", "40"}
            };

            for (int i = 0; i < 10; i++) {
                products[i] = new Product(
                    IdGenerator.nextProductId(),
                    productData[i][0],
                    productData[i][1],
                    Double.parseDouble(productData[i][2]),
                    Integer.parseInt(productData[i][3])
                );
                repo.saveProduct(products[i]);
            }
            Printer.success("10 products created.");

            for (int i = 0; i < 5; i++) {
                int qty = (i + 1) * 2;
                if (qty > products[i].getStock()) qty = products[i].getStock();
                products[i].setStock(products[i].getStock() - qty);
                repo.updateProduct(products[i]);

                SalesPhysical sale = new SalesPhysical(
                    IdGenerator.nextOfferId(), products[i], qty,
                    "Customer " + (i + 1),
                    "Address " + (i + 1) + ", City",
                    "000" + (i + 1) + "-000"
                );
                repo.savePhysicalSale(sale);
            }
            Printer.success("5 physical sales created.");

            for (int i = 5; i < 10; i++) {
                int qty = (i - 4) * 1;
                if (qty > products[i].getStock()) qty = products[i].getStock();
                products[i].setStock(products[i].getStock() - qty);
                repo.updateProduct(products[i]);

                SalesDigital sale = new SalesDigital(
                    IdGenerator.nextOfferId(), products[i], qty,
                    "Customer " + (i + 1),
                    "customer" + (i + 1) + "@test.com",
                    UUID.randomUUID().toString().substring(0, 8).toUpperCase()
                );
                repo.saveDigitalSale(sale);
            }
            Printer.success("5 digital sales created.");

            Printer.success("Test data seeded successfully!");
        }
    }
}
