package events;

public class PluginUnloadedEvent extends Event {
    private String pluginId;

    public PluginUnloadedEvent(String pluginId) {
        super("plugin-unloaded");
        this.pluginId = pluginId;
    }

    public String getPluginId() { return pluginId; }
}
