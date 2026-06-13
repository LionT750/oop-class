package plugins;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import menu.FunctionalityContext;
import menu.MenuFunctionality;
import menu.Plugin;
import models.Offer;
import models.Product;
import models.SalesDigital;
import models.SalesPhysical;
import repository.Repository;
import utils.ConsoleReader;
import utils.IdGenerator;
import utils.Printer;

public class SalesPlugin implements Plugin {
    private ConsoleReader reader;
    private Repository repo;

    public SalesPlugin(FunctionalityContext context) {
        this.reader = new ConsoleReader(context.scanner);
        this.repo = context.repository;
    }

    @Override
    public String getId() { return "sales-plugin"; }

    @Override
    public String getName() { return "Sales Management"; }

    @Override
    public String getDescription() { return "Manage sales (physical and digital)"; }

    @Override
    public List<MenuFunctionality> getFunctionalities() {
        return Arrays.asList(
            new CreatePhysicalSale(),
            new CreateDigitalSale(),
            new ListPhysicalSales(),
            new ListDigitalSales(),
            new ListAllSales(),
            new FindSale(),
            new CountSales()
        );
    }

    private Product selectProduct() {
        List<Product> products = repo.findAllProducts();
        if (products.isEmpty()) {
            Printer.error("No products available.");
            return null;
        }
        System.out.println("Available products:");
        for (Product p : products) {
            System.out.println("  " + p.getId() + " - " + p.getName() + " ($" + String.format("%.2f", p.getPrice()) + ") stock: " + p.getStock());
        }
        System.out.print("Select product ID: ");
        long productId = reader.readInt();
        Optional<Product> opt = repo.findProductById(productId);
        if (opt.isEmpty()) {
            Printer.error("Product not found.");
            return null;
        }
        return opt.get();
    }

    private class CreatePhysicalSale implements MenuFunctionality {
        public String getId() { return "create-physical-sale"; }
        public String getLabel() { return "Create Physical Sale"; }
        public String getDescription() { return "Create a new physical sale"; }
        public void execute() {
            Product product = selectProduct();
            if (product == null) return;

            System.out.print("Quantity: ");
            int quantity = reader.readInt();
            if (quantity > product.getStock()) {
                Printer.error("Insufficient stock. Available: " + product.getStock());
                return;
            }
            System.out.print("Customer name: ");
            String customerName = reader.readString();
            System.out.print("Shipping address: ");
            String address = reader.readString();
            System.out.print("Postal code: ");
            String postalCode = reader.readString();

            product.setStock(product.getStock() - quantity);
            repo.updateProduct(product);

            SalesPhysical sale = new SalesPhysical(IdGenerator.nextOfferId(), product, quantity,
                                                    customerName, address, postalCode);
            repo.savePhysicalSale(sale);
            Printer.success("Physical sale #" + sale.getId() + " created.");
        }
    }

    private class CreateDigitalSale implements MenuFunctionality {
        public String getId() { return "create-digital-sale"; }
        public String getLabel() { return "Create Digital Sale"; }
        public String getDescription() { return "Create a new digital sale"; }
        public void execute() {
            Product product = selectProduct();
            if (product == null) return;

            System.out.print("Quantity: ");
            int quantity = reader.readInt();
            if (quantity > product.getStock()) {
                Printer.error("Insufficient stock. Available: " + product.getStock());
                return;
            }
            System.out.print("Customer name: ");
            String customerName = reader.readString();
            System.out.print("Email: ");
            String email = reader.readString();
            String downloadKey = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            product.setStock(product.getStock() - quantity);
            repo.updateProduct(product);

            SalesDigital sale = new SalesDigital(IdGenerator.nextOfferId(), product, quantity,
                                                  customerName, email, downloadKey);
            repo.saveDigitalSale(sale);
            Printer.success("Digital sale #" + sale.getId() + " created. Download key: " + downloadKey);
        }
    }

    private class ListPhysicalSales implements MenuFunctionality {
        public String getId() { return "list-physical-sales"; }
        public String getLabel() { return "List Physical Sales"; }
        public String getDescription() { return "List all physical sales"; }
        public void execute() {
            List<SalesPhysical> sales = repo.findAllPhysicalSales();
            if (sales.isEmpty()) {
                System.out.println("No physical sales.");
                return;
            }
            for (SalesPhysical s : sales) {
                System.out.println(s);
            }
            System.out.println("Total: " + sales.size());
        }
    }

    private class ListDigitalSales implements MenuFunctionality {
        public String getId() { return "list-digital-sales"; }
        public String getLabel() { return "List Digital Sales"; }
        public String getDescription() { return "List all digital sales"; }
        public void execute() {
            List<SalesDigital> sales = repo.findAllDigitalSales();
            if (sales.isEmpty()) {
                System.out.println("No digital sales.");
                return;
            }
            for (SalesDigital s : sales) {
                System.out.println(s);
            }
            System.out.println("Total: " + sales.size());
        }
    }

    private class ListAllSales implements MenuFunctionality {
        public String getId() { return "list-all-sales"; }
        public String getLabel() { return "List All Sales"; }
        public String getDescription() { return "List all sales"; }
        public void execute() {
            List<Offer> sales = repo.findAllSales();
            if (sales.isEmpty()) {
                System.out.println("No sales.");
                return;
            }
            for (Offer s : sales) {
                System.out.println(s);
            }
            System.out.println("Total: " + sales.size());
        }
    }

    private class FindSale implements MenuFunctionality {
        public String getId() { return "find-sale"; }
        public String getLabel() { return "Find Sale"; }
        public String getDescription() { return "Find a sale by ID"; }
        public void execute() {
            System.out.print("Sale ID: ");
            long id = reader.readInt();
            Optional<Offer> opt = repo.findSaleById(id);
            if (opt.isPresent()) {
                System.out.println(opt.get());
            } else {
                Printer.error("Sale not found.");
            }
        }
    }

    private class CountSales implements MenuFunctionality {
        public String getId() { return "count-sales"; }
        public String getLabel() { return "Count Sales"; }
        public String getDescription() { return "Show number of sales"; }
        public void execute() {
            System.out.println("Total sales: " + repo.countSales());
        }
    }
}
