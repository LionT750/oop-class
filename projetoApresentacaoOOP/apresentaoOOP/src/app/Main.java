package app;

import java.util.Scanner;
import javax.swing.SwingUtilities;
import menu.FunctionalityContext;
import menu.Menu;
import menu.MenuFunctionality;
import menu.PluginRegistry;
import plugins.AdminPlugin;
import plugins.DebugPlugin;
import plugins.ProductPlugin;
import plugins.RuntimePluginLoader;
import plugins.SalesPlugin;
import plugins.SeedDataPlugin;
import repository.Repository;
import ui.AppWindow;

public class Main {
    public static void main(String[] args) {
        Repository repository = Repository.getInstance();
        PluginRegistry pluginRegistry = new PluginRegistry();
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(scanner, pluginRegistry);
        FunctionalityContext context = new FunctionalityContext(repository, menu, scanner, pluginRegistry);

        pluginRegistry.registerPlugin(new ProductPlugin(context));
        pluginRegistry.registerPlugin(new SalesPlugin(context));
        pluginRegistry.registerPlugin(new SeedDataPlugin(context));

        RuntimePluginLoader loader = new RuntimePluginLoader(context);
        loader.registerAvailablePlugin("product-plugin", () -> new ProductPlugin(context));
        loader.registerAvailablePlugin("sales-plugin", () -> new SalesPlugin(context));
        loader.registerAvailablePlugin("seed-data-plugin", () -> new SeedDataPlugin(context));
        loader.registerAvailablePlugin("debug-plugin", () -> new DebugPlugin(context));
        loader.registerAvailablePlugin("admin-plugin", () -> new AdminPlugin(context));
        loader.markLoaded("product-plugin");
        loader.markLoaded("sales-plugin");
        loader.markLoaded("seed-data-plugin");
        pluginRegistry.registerPlugin(loader);

        for (var p : pluginRegistry.getAllPlugins()) {
            for (var f : p.getFunctionalities()) {
                menu.addFunctionality(f);
            }
        }

        menu.addFunctionality(new MenuFunctionality() {
            public String getId() { return "exit"; }
            public String getLabel() { return "Exit"; }
            public String getDescription() { return "Exit the application"; }
            public int order() { return 100; }
            public void execute() { System.exit(0); }
        });

        SwingUtilities.invokeLater(() -> {
            AppWindow window = new AppWindow(menu);
            menu.setOnChanged(window::rebuildButtons);
            window.setVisible(true);
        });
    }
}
