package plugins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import menu.FunctionalityContext;
import menu.MenuFunctionality;
import menu.Plugin;
import utils.ConsoleReader;
import utils.Printer;

public class RuntimePluginLoader implements Plugin {
    private FunctionalityContext context;
    private ConsoleReader reader;
    private Map<String, PluginFactory> catalog;
    private Set<String> loaded;

    public RuntimePluginLoader(FunctionalityContext context) {
        this.context = context;
        this.reader = new ConsoleReader(context.scanner);
        this.catalog = new LinkedHashMap<>();
        this.loaded = new HashSet<>();
    }

    public void registerAvailablePlugin(String id, PluginFactory factory) {
        catalog.put(id, factory);
    }

    public void markLoaded(String pluginId) {
        loaded.add(pluginId);
    }

    public void markUnloaded(String pluginId) {
        loaded.remove(pluginId);
    }

    @Override
    public String getId() { return "runtime-loader"; }

    @Override
    public String getName() { return "Plugin Loader"; }

    @Override
    public String getDescription() { return "Load and manage plugins at runtime"; }

    @Override
    public List<MenuFunctionality> getFunctionalities() {
        return Arrays.asList(
            new LoadPlugin(),
            new UnloadPlugin(),
            new ListPlugins()
        );
    }

    public interface PluginFactory {
        Plugin create();
    }

    private class LoadPlugin implements MenuFunctionality {
        public String getId() { return "load-plugin"; }
        public String getLabel() { return "Load Plugin"; }
        public String getDescription() { return "Load a plugin by selection"; }
        public int order() { return 3; }
        public void execute() {
            List<Map.Entry<String, PluginFactory>> available = new ArrayList<>();
            for (var entry : catalog.entrySet()) {
                if (!loaded.contains(entry.getKey())) {
                    available.add(entry);
                }
            }
            if (available.isEmpty()) {
                System.out.println("No plugins available to load.");
                return;
            }
            System.out.println("Available plugins:");
            for (int i = 0; i < available.size(); i++) {
                Plugin stub = available.get(i).getValue().create();
                System.out.println("  " + (i + 1) + " - " + stub.getName());
            }
            Printer.prompt("Enter number to load (0 to cancel): ");
            int choice = reader.readInt();
            if (choice == 0) {
                System.out.println("Cancelled.");
                return;
            }
            if (choice < 1 || choice > available.size()) {
                Printer.error("Invalid choice.");
                return;
            }
            Plugin plugin = available.get(choice - 1).getValue().create();
            loaded.add(plugin.getId());
            context.menu.loadPlugin(plugin);
        }
    }

    private class UnloadPlugin implements MenuFunctionality {
        public String getId() { return "runtime-unload-plugin"; }
        public String getLabel() { return "Unload Plugin"; }
        public String getDescription() { return "Unload a plugin by ID"; }
        public int order() { return 3; }
        public void execute() {
            Printer.prompt("Enter plugin ID to unload (use 'List Plugins' first to see IDs): ");
            String pluginId = reader.readString();
            if (pluginId.isEmpty()) {
                System.out.println("Cancelled.");
                return;
            }
            loaded.remove(pluginId);
            context.menu.unloadPlugin(pluginId);
        }
    }

    private class ListPlugins implements MenuFunctionality {
        public String getId() { return "list-loaded-plugins"; }
        public String getLabel() { return "List Plugins"; }
        public String getDescription() { return "Show all loaded plugins"; }
        public int order() { return 3; }
        public void execute() {
            var plugins = context.pluginRegistry.getAllPlugins();
            if (plugins.isEmpty()) {
                System.out.println("No plugins loaded.");
                return;
            }
            for (Plugin p : plugins) {
                System.out.println(p.getId() + " | " + p.getName() + " - " + p.getDescription());
            }
        }
    }
}
