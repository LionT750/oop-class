package menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroPlugin {
    private Map<String, Plugin> plugins = new HashMap<>();

    public void registrarPlugin(Plugin plugin) {
        if (plugins.containsKey(plugin.getId())) {
            throw new IllegalArgumentException("Plugin ja registrado: " + plugin.getId());
        }
        plugins.put(plugin.getId(), plugin);
    }

    public void desregistrarPlugin(String id) {
        plugins.remove(id);
    }

    public Plugin getPlugin(String id) {
        return plugins.get(id);
    }

    public List<Plugin> getTodosPlugins() {
        return new ArrayList<>(plugins.values());
    }

    public boolean contemPlugin(String id) {
        return plugins.containsKey(id);
    }
}
