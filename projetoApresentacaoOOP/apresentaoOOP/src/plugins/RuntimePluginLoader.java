package plugins;

import java.util.Arrays;
import java.util.List;
import menu.FunctionalityContext;
import menu.MenuFunctionality;
import menu.Plugin;
import utils.ConsoleReader;
import utils.Printer;

public class RuntimePluginLoader implements Plugin {
    private FunctionalityContext context;
    private ConsoleReader reader;

    public RuntimePluginLoader(FunctionalityContext context) {
        this.context = context;
        this.reader = new ConsoleReader(context.scanner);
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
            new LoadDebugPlugin(),
            new LoadAdminPlugin(),
            new UnloadPlugin(),
            new ListPlugins()
        );
    }

    private class LoadDebugPlugin implements MenuFunctionality {
        public String getId() { return "load-debug-plugin"; }
        public String getLabel() { return "Load Debug Plugin"; }
        public String getDescription() { return "Load the debug plugin at runtime"; }
        public void execute() {
            if (context.pluginRegistry.containsPlugin("debug-plugin")) {
                Printer.error("Debug plugin is already loaded.");
                return;
            }
            Plugin debugPlugin = new DebugPlugin(context);
            context.menu.loadPlugin(debugPlugin);
        }
    }

    private class LoadAdminPlugin implements MenuFunctionality {
        public String getId() { return "load-admin-plugin"; }
        public String getLabel() { return "Load Admin Plugin"; }
        public String getDescription() { return "Load the admin plugin at runtime"; }
        public void execute() {
            if (context.pluginRegistry.containsPlugin("admin-plugin")) {
                Printer.error("Admin plugin is already loaded.");
                return;
            }
            Plugin adminPlugin = new AdminPlugin(context);
            context.menu.loadPlugin(adminPlugin);
        }
    }

    private class UnloadPlugin implements MenuFunctionality {
        public String getId() { return "runtime-unload-plugin"; }
        public String getLabel() { return "Unload Plugin"; }
        public String getDescription() { return "Unload a plugin by ID"; }
        public void execute() {
            System.out.print("Plugin ID to unload: ");
            String pluginId = reader.readString();
            context.menu.unloadPlugin(pluginId);
        }
    }

    private class ListPlugins implements MenuFunctionality {
        public String getId() { return "list-loaded-plugins"; }
        public String getLabel() { return "List Plugins"; }
        public String getDescription() { return "Show all loaded plugins"; }
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
