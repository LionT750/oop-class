package plugins;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import menu.FunctionalityContext;
import menu.MenuFunctionality;
import menu.Plugin;
import models.Product;
import repository.Repository;
import utils.ConsoleReader;
import utils.IdGenerator;
import utils.Printer;

public class ProductPlugin implements Plugin {
    private ConsoleReader reader;
    private Repository repo;

    public ProductPlugin(FunctionalityContext context) {
        this.reader = new ConsoleReader(context.scanner);
        this.repo = context.repository;
    }

    @Override
    public String getId() { return "product-plugin"; }

    @Override
    public String getName() { return "Product Management"; }

    @Override
    public String getDescription() { return "CRUD operations for products"; }

    @Override
    public List<MenuFunctionality> getFunctionalities() {
        return Arrays.asList(
            new CreateProduct(),
            new ListProducts(),
            new FindProduct(),
            new UpdateProduct(),
            new DeleteProduct(),
            new DeactivateProduct(),
            new CountProducts()
        );
    }

    private class CreateProduct implements MenuFunctionality {
        public String getId() { return "create-product"; }
        public String getLabel() { return "Create Product"; }
        public String getDescription() { return "Create a new product"; }
        public int order() { return 2; }
        public void execute() {
            Printer.prompt("Product name: ");
            String name = reader.readString();
            Printer.prompt("Description (brief): ");
            String description = reader.readString();
            Printer.prompt("Price (e.g. 29.99): ");
            double price = reader.readDouble();
            Printer.prompt("Initial stock (units): ");
            int stock = reader.readInt();

            Product product = new Product(IdGenerator.nextProductId(), name, description, price, stock);
            repo.saveProduct(product);
            Printer.success("Product #" + product.getId() + " created.");
        }
    }

    private class ListProducts implements MenuFunctionality {
        public String getId() { return "list-products"; }
        public String getLabel() { return "List Products"; }
        public String getDescription() { return "List all products"; }
        public int order() { return 2; }
        public void execute() {
            List<Product> products = repo.findAllProducts();
            if (products.isEmpty()) {
                System.out.println("No products found.");
                return;
            }
            for (Product p : products) {
                System.out.println(p);
            }
            System.out.println("Total: " + products.size());
        }
    }

    private class FindProduct implements MenuFunctionality {
        public String getId() { return "find-product"; }
        public String getLabel() { return "Find Product"; }
        public String getDescription() { return "Find product by ID"; }
        public int order() { return 2; }
        public void execute() {
            Printer.prompt("Product ID to find (0 to cancel): ");
            long id = reader.readInt();
            if (id == 0) { System.out.println("Cancelled."); return; }
            Optional<Product> opt = repo.findProductById(id);
            if (opt.isPresent()) {
                System.out.println(opt.get());
                Product p = opt.get();
                System.out.println("  Description: " + p.getDescription());
                System.out.println("  Stock: " + p.getStock());
                System.out.println("  Active: " + p.isActive());
            } else {
                Printer.error("No product with ID " + id + " found.");
            }
        }
    }

    private class UpdateProduct implements MenuFunctionality {
        public String getId() { return "update-product"; }
        public String getLabel() { return "Update Product"; }
        public String getDescription() { return "Update product details"; }
        public int order() { return 2; }
        public void execute() {
            Printer.prompt("Product ID to update (0 to cancel): ");
            long id = reader.readInt();
            if (id == 0) { System.out.println("Cancelled."); return; }
            Optional<Product> opt = repo.findProductById(id);
            if (opt.isEmpty()) {
                Printer.error("No product with ID " + id + " found.");
                return;
            }
            Product product = opt.get();
            System.out.println("Current: " + product);
            Printer.prompt("New name (Enter = keep \"" + product.getName() + "\"): ");
            String name = reader.readStringOptional();
            if (name.isEmpty()) name = product.getName();
            Printer.prompt("New description (Enter = keep \"" + product.getDescription() + "\"): ");
            String description = reader.readStringOptional();
            if (description.isEmpty()) description = product.getDescription();
            Printer.prompt("New price (Enter = keep " + product.getPrice() + "): ");
            String priceStr = reader.readStringOptional();
            double price = priceStr.isEmpty() ? product.getPrice() : Double.parseDouble(priceStr);
            Printer.prompt("New stock (Enter = keep " + product.getStock() + "): ");
            String stockStr = reader.readStringOptional();
            int stock = stockStr.isEmpty() ? product.getStock() : Integer.parseInt(stockStr);

            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            repo.updateProduct(product);
            Printer.success("Product updated.");
        }
    }

    private class DeleteProduct implements MenuFunctionality {
        public String getId() { return "delete-product"; }
        public String getLabel() { return "Delete Product"; }
        public String getDescription() { return "Delete a product"; }
        public int order() { return 2; }
        public void execute() {
            Printer.prompt("Product ID to delete (0 to cancel): ");
            long id = reader.readInt();
            if (id == 0) { System.out.println("Cancelled."); return; }
            Optional<Product> opt = repo.findProductById(id);
            if (opt.isEmpty()) {
                Printer.error("No product with ID " + id + " found.");
                return;
            }
            repo.deleteProduct(opt.get());
            Printer.success("Product deleted.");
        }
    }

    private class DeactivateProduct implements MenuFunctionality {
        public String getId() { return "deactivate-product"; }
        public String getLabel() { return "Deactivate Product"; }
        public String getDescription() { return "Toggle product active status"; }
        public int order() { return 2; }
        public void execute() {
            Printer.prompt("Product ID (0 to cancel): ");
            long id = reader.readInt();
            if (id == 0) { System.out.println("Cancelled."); return; }
            Optional<Product> opt = repo.findProductById(id);
            if (opt.isEmpty()) {
                Printer.error("No product with ID " + id + " found.");
                return;
            }
            Product p = opt.get();
            p.setActive(!p.isActive());
            repo.updateProduct(p);
            Printer.success("Product is now " + (p.isActive() ? "Active" : "Inactive"));
        }
    }

    private class CountProducts implements MenuFunctionality {
        public String getId() { return "count-products"; }
        public String getLabel() { return "Count Products"; }
        public String getDescription() { return "Show number of products"; }
        public int order() { return 2; }
        public void execute() {
            System.out.println("Total products: " + repo.countProducts());
        }
    }
}
