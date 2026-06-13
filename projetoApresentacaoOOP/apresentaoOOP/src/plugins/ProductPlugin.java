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
        public void execute() {
            System.out.print("Name: ");
            String name = reader.readString();
            System.out.print("Description: ");
            String description = reader.readString();
            System.out.print("Price: ");
            double price = reader.readDouble();
            System.out.print("Stock quantity: ");
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
        public void execute() {
            System.out.print("Product ID: ");
            long id = reader.readInt();
            Optional<Product> opt = repo.findProductById(id);
            if (opt.isPresent()) {
                System.out.println(opt.get());
                Product p = opt.get();
                System.out.println("  Description: " + p.getDescription());
                System.out.println("  Stock: " + p.getStock());
                System.out.println("  Active: " + p.isActive());
            } else {
                Printer.error("Product not found.");
            }
        }
    }

    private class UpdateProduct implements MenuFunctionality {
        public String getId() { return "update-product"; }
        public String getLabel() { return "Update Product"; }
        public String getDescription() { return "Update product details"; }
        public void execute() {
            System.out.print("Product ID to update: ");
            long id = reader.readInt();
            Optional<Product> opt = repo.findProductById(id);
            if (opt.isEmpty()) {
                Printer.error("Product not found.");
                return;
            }
            Product product = opt.get();
            System.out.println("Current: " + product);
            System.out.print("New name (" + product.getName() + "): ");
            String name = reader.readString();
            System.out.print("New description (" + product.getDescription() + "): ");
            String description = reader.readString();
            System.out.print("New price (" + product.getPrice() + "): ");
            double price = reader.readDouble();
            System.out.print("New stock (" + product.getStock() + "): ");
            int stock = reader.readInt();

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
        public void execute() {
            System.out.print("Product ID to delete: ");
            long id = reader.readInt();
            Optional<Product> opt = repo.findProductById(id);
            if (opt.isEmpty()) {
                Printer.error("Product not found.");
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
        public void execute() {
            System.out.print("Product ID: ");
            long id = reader.readInt();
            Optional<Product> opt = repo.findProductById(id);
            if (opt.isEmpty()) {
                Printer.error("Product not found.");
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
        public void execute() {
            System.out.println("Total products: " + repo.countProducts());
        }
    }
}
