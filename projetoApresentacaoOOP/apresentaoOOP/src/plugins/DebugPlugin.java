package plugins;

import java.util.Arrays;
import java.util.List;
import menu.FunctionalityContext;
import menu.Menu;
import menu.MenuFunctionality;
import menu.Plugin;
import menu.PluginRegistry;
import repository.Repository;
import utils.Printer;

public class DebugPlugin implements Plugin {
    private Repository repo;
    private Menu menu;
    private FunctionalityContext context;

    public DebugPlugin(FunctionalityContext context) {
        this.context = context;
        this.repo = context.repository;
        this.menu = context.menu;
    }

    @Override
    public String getId() { return "debug-plugin"; }

    @Override
    public String getName() { return "Debug Tools"; }

    @Override
    public String getDescription() { return "Debugging and statistics tools"; }

    @Override
    public List<MenuFunctionality> getFunctionalities() {
        return Arrays.asList(
            new ShowProductCount(),
            new ShowSalesCount(),
            new ShowRegisteredPlugins(),
            new ShowLoadedCommands(),
            new ShowDatabaseState()
        );
    }

    private class ShowProductCount implements MenuFunctionality {
        public String getId() { return "debug-product-count"; }
        public String getLabel() { return "Show Product Count"; }
        public String getDescription() { return "Display number of products"; }
        public void execute() {
            System.out.println("Products in database: " + repo.countProducts());
        }
    }

    private class ShowSalesCount implements MenuFunctionality {
        public String getId() { return "debug-sales-count"; }
        public String getLabel() { return "Show Sales Count"; }
        public String getDescription() { return "Display number of sales"; }
        public void execute() {
            System.out.println("Sales in database: " + repo.countSales());
        }
    }

    private class ShowRegisteredPlugins implements MenuFunctionality {
        public String getId() { return "debug-plugins"; }
        public String getLabel() { return "Show Registered Plugins"; }
        public String getDescription() { return "List all registered plugins"; }
        public void execute() {
            PluginRegistry registry = context.pluginRegistry;
            List<Plugin> plugins = registry.getAllPlugins();
            if (plugins.isEmpty()) {
                System.out.println("No plugins registered.");
                return;
            }
            for (Plugin p : plugins) {
                System.out.println(p.getId() + " | " + p.getName() + " - " + p.getDescription());
            }
        }
    }

    private class ShowLoadedCommands implements MenuFunctionality {
        public String getId() { return "debug-commands"; }
        public String getLabel() { return "Show Loaded Commands"; }
        public String getDescription() { return "List all loaded commands"; }
        public void execute() {
            List<MenuFunctionality> funcs = menu.getFunctionalities();
            if (funcs.isEmpty()) {
                System.out.println("No commands loaded.");
                return;
            }
            for (MenuFunctionality f : funcs) {
                System.out.println(f.getId() + " | " + f.getLabel() + " - " + f.getDescription());
            }
            System.out.println("Total: " + funcs.size());
        }
    }

    private class ShowDatabaseState implements MenuFunctionality {
        public String getId() { return "debug-db-state"; }
        public String getLabel() { return "Show Database State"; }
        public String getDescription() { return "Dump full database state"; }
        public void execute() {
            Printer.separator();
            System.out.println("=== DATABASE DUMP ===");
            System.out.println("Products (" + repo.countProducts() + "):");
            for (var p : repo.findAllProducts()) {
                System.out.println("  " + p);
            }
            System.out.println("Physical Sales (" + repo.findAllPhysicalSales().size() + "):");
            for (var s : repo.findAllPhysicalSales()) {
                System.out.println("  " + s);
            }
            System.out.println("Digital Sales (" + repo.findAllDigitalSales().size() + "):");
            for (var s : repo.findAllDigitalSales()) {
                System.out.println("  " + s);
            }
            Printer.separator();
        }
    }
}
