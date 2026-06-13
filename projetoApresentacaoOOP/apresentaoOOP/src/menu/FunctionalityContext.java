package menu;

import java.util.Scanner;
import events.EventBus;
import repository.Repository;

public class FunctionalityContext {
    public Repository repository;
    public Menu menu;
    public Scanner scanner;
    public PluginRegistry pluginRegistry;
    public EventBus eventBus;

    public FunctionalityContext(Repository repository, Menu menu, Scanner scanner,
                                 PluginRegistry pluginRegistry, EventBus eventBus) {
        this.repository = repository;
        this.menu = menu;
        this.scanner = scanner;
        this.pluginRegistry = pluginRegistry;
        this.eventBus = eventBus;
    }
}
