package app;

import java.util.Scanner;
import data.InMemoryDatabase;
import events.EventBus;
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
        InMemoryDatabase database = new InMemoryDatabase();
        Repository repository = new Repository(database);
        EventBus eventBus = new EventBus();
        PluginRegistry pluginRegistry = new PluginRegistry();
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(scanner, pluginRegistry, eventBus);
        FunctionalityContext context = new FunctionalityContext(repository, menu, scanner, pluginRegistry, eventBus);

        PluginRegistry reg = context.pluginRegistry;
        reg.registerPlugin(new ProductPlugin(context));
        reg.registerPlugin(new SalesPlugin(context));
        reg.registerPlugin(new RuntimePluginLoader(context));

        for (var p : reg.getAllPlugins()) {
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
