package app;

import java.util.Scanner;
import menu.FunctionalityContext;
import menu.Menu;
import menu.MenuFunctionality;
import menu.PluginRegistry;
import plugins.ProductPlugin;
import plugins.RuntimePluginLoader;
import plugins.SalesPlugin;
import repository.Repository;

public class Main {
    public static void main(String[] args) {
        Repository repository = Repository.getInstance();
        PluginRegistry pluginRegistry = new PluginRegistry();
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(scanner, pluginRegistry);
        FunctionalityContext context = new FunctionalityContext(repository, menu, scanner, pluginRegistry);

        pluginRegistry.registerPlugin(new ProductPlugin(context));
        pluginRegistry.registerPlugin(new SalesPlugin(context));
        pluginRegistry.registerPlugin(new RuntimePluginLoader(context));

        for (var p : pluginRegistry.getAllPlugins()) {
            for (var f : p.getFunctionalities()) {
                menu.addFunctionality(f);
            }
        }

        menu.addFunctionality(new MenuFunctionality() {
            public String getId() { return "exit"; }
            public String getLabel() { return "Exit"; }
            public String getDescription() { return "Exit the application"; }
            public void execute() { menu.stop(); }
        });

        menu.run();
        scanner.close();
    }
}
