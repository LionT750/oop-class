package menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginRegistry {
    private Map<String, Plugin> plugins = new HashMap<>();

    public void registerPlugin(Plugin plugin) {
        if (plugins.containsKey(plugin.getId())) {
            throw new IllegalArgumentException("Plugin already registered: " + plugin.getId());
        }
        plugins.put(plugin.getId(), plugin);
    }

    public void unregisterPlugin(String id) {
        plugins.remove(id);
    }

    public Plugin getPlugin(String id) {
        return plugins.get(id);
    }

    public List<Plugin> getAllPlugins() {
        return new ArrayList<>(plugins.values());
    }

    public boolean containsPlugin(String id) {
        return plugins.containsKey(id);
    }
}
