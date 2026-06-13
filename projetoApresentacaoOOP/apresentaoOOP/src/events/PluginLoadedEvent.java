package events;

import menu.Plugin;

public class PluginLoadedEvent extends Event {
    private Plugin plugin;

    public PluginLoadedEvent(Plugin plugin) {
        super("plugin-loaded");
        this.plugin = plugin;
    }

    public Plugin getPlugin() { return plugin; }
}
