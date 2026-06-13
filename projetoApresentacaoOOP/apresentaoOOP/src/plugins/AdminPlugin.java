package plugins;

import java.util.Arrays;
import java.util.List;
import menu.FunctionalityContext;
import menu.Menu;
import menu.MenuFunctionality;
import menu.Plugin;
import menu.PluginRegistry;
import repository.Repository;
import utils.ConsoleReader;
import utils.Printer;

public class AdminPlugin implements Plugin {
    private FunctionalityContext context;
    private ConsoleReader reader;
    private Repository repo;
    private Menu menu;
    private PluginRegistry pluginRegistry;

    public AdminPlugin(FunctionalityContext context) {
        this.context = context;
        this.reader = new ConsoleReader(context.scanner);
        this.repo = context.repository;
        this.menu = context.menu;
        this.pluginRegistry = context.pluginRegistry;
    }

    @Override
    public String getId() { return "admin-plugin"; }

    @Override
    public String getName() { return "Admin Tools"; }

    @Override
    public String getDescription() { return "Administrative operations"; }

    @Override
    public List<MenuFunctionality> getFunctionalities() {
        return Arrays.asList(
            new ClearProducts(),
            new ClearSales(),
            new ResetDatabase(),
            new UnloadPlugin(),
            new DisableCommand(),
            new EnableCommand()
        );
    }

    private class ClearProducts implements MenuFunctionality {
        public String getId() { return "clear-products"; }
        public String getLabel() { return "Clear Products"; }
        public String getDescription() { return "Remove all products"; }
        public void execute() {
            System.out.print("Remove all " + repo.countProducts() + " products? (y/n): ");
            String confirm = context.scanner.nextLine().trim().toLowerCase();
            if (confirm.equals("y")) {
                repo.clearProducts();
                Printer.success("All products cleared.");
            }
        }
    }

    private class ClearSales implements MenuFunctionality {
        public String getId() { return "clear-sales"; }
        public String getLabel() { return "Clear Sales"; }
        public String getDescription() { return "Remove all sales"; }
        public void execute() {
            System.out.print("Remove all " + repo.countSales() + " sales? (y/n): ");
            String confirm = context.scanner.nextLine().trim().toLowerCase();
            if (confirm.equals("y")) {
                repo.clearSales();
                Printer.success("All sales cleared.");
            }
        }
    }

    private class ResetDatabase implements MenuFunctionality {
        public String getId() { return "reset-db"; }
        public String getLabel() { return "Reset Database"; }
        public String getDescription() { return "Reset entire database"; }
        public void execute() {
            System.out.print("Reset entire database? (y/n): ");
            String confirm = context.scanner.nextLine().trim().toLowerCase();
            if (confirm.equals("y")) {
                repo.resetDatabase();
                Printer.success("Database reset.");
            }
        }
    }

    private class UnloadPlugin implements MenuFunctionality {
        public String getId() { return "unload-plugin"; }
        public String getLabel() { return "Unload Plugin"; }
        public String getDescription() { return "Unload a registered plugin"; }
        public void execute() {
            List<Plugin> plugins = pluginRegistry.getAllPlugins();
            if (plugins.isEmpty()) {
                System.out.println("No plugins to unload.");
                return;
            }
            System.out.println("Loaded plugins:");
            for (int i = 0; i < plugins.size(); i++) {
                Plugin p = plugins.get(i);
                System.out.println((i + 1) + " - " + p.getName() + " (" + p.getId() + ")");
            }
            System.out.print("Select plugin to unload: ");
            int choice = reader.readInt();
            if (choice < 1 || choice > plugins.size()) {
                Printer.error("Invalid choice.");
                return;
            }
            menu.unloadPlugin(plugins.get(choice - 1).getId());
        }
    }

    private class DisableCommand implements MenuFunctionality {
        public String getId() { return "disable-command"; }
        public String getLabel() { return "Disable Command"; }
        public String getDescription() { return "Remove a command from the menu"; }
        public void execute() {
            List<MenuFunctionality> funcs = menu.getFunctionalities();
            if (funcs.isEmpty()) {
                System.out.println("No commands to disable.");
                return;
            }
            System.out.println("Loaded commands:");
            for (int i = 0; i < funcs.size(); i++) {
                MenuFunctionality f = funcs.get(i);
                System.out.println((i + 1) + " - " + f.getLabel() + " (" + f.getId() + ")");
            }
            System.out.print("Select command to disable: ");
            int choice = reader.readInt();
            if (choice < 1 || choice > funcs.size()) {
                Printer.error("Invalid choice.");
                return;
            }
            menu.removeFunctionality(funcs.get(choice - 1).getId());
            Printer.success("Command disabled.");
        }
    }

    private class EnableCommand implements MenuFunctionality {
        public String getId() { return "enable-command"; }
        public String getLabel() { return "Enable Command"; }
        public String getDescription() { return "Add a command from a plugin back to the menu"; }
        public void execute() {
            List<Plugin> plugins = pluginRegistry.getAllPlugins();
            boolean found = false;
            System.out.println("Available commands from plugins:");
            for (Plugin p : plugins) {
                for (MenuFunctionality f : p.getFunctionalities()) {
                    if (!menu.hasFunctionality(f.getId())) {
                        System.out.println(f.getId() + " - " + f.getLabel() + " (" + p.getName() + ")");
                        found = true;
                    }
                }
            }
            if (!found) {
                System.out.println("No disabled commands available.");
                return;
            }
            System.out.print("Enter command ID to enable: ");
            String id = reader.readString();
            for (Plugin p : plugins) {
                for (MenuFunctionality f : p.getFunctionalities()) {
                    if (f.getId().equals(id) && !menu.hasFunctionality(f.getId())) {
                        menu.addFunctionality(f);
                        Printer.success("Command enabled: " + f.getLabel());
                        return;
                    }
                }
            }
            Printer.error("Command not found or already enabled.");
        }
    }
}
