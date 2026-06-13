package menu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import utils.Printer;

public class Menu {
    private List<MenuFunctionality> functionalities;
    private boolean running;
    private Scanner scanner;
    private PluginRegistry pluginRegistry;
    private Runnable onChanged;

    public Menu(Scanner scanner, PluginRegistry pluginRegistry) {
        this.functionalities = new ArrayList<>();
        this.running = false;
        this.scanner = scanner;
        this.pluginRegistry = pluginRegistry;
    }

    public void setOnChanged(Runnable r) { this.onChanged = r; }

    public void addFunctionality(MenuFunctionality functionality) {
        functionalities.add(functionality);
        if (onChanged != null) onChanged.run();
    }

    public void removeFunctionality(String id) {
        Iterator<MenuFunctionality> it = functionalities.iterator();
        while (it.hasNext()) {
            if (it.next().getId().equals(id)) {
                it.remove();
                if (onChanged != null) onChanged.run();
                return;
            }
        }
    }

    public boolean hasFunctionality(String id) {
        return functionalities.stream().anyMatch(f -> f.getId().equals(id));
    }

    private List<MenuFunctionality> getSorted() {
        List<MenuFunctionality> sorted = new ArrayList<>(functionalities);
        sorted.sort(Comparator.comparingInt(MenuFunctionality::order));
        return sorted;
    }

    public List<MenuFunctionality> getFunctionalities() {
        return getSorted();
    }

    public void clearFunctionalities() {
        functionalities.clear();
    }

    public void loadPlugin(Plugin plugin) {
        pluginRegistry.registerPlugin(plugin);
        for (MenuFunctionality f : plugin.getFunctionalities()) {
            addFunctionality(f);
        }
        Printer.success("Plugin loaded: " + plugin.getName());
    }

    public void unloadPlugin(String pluginId) {
        Plugin plugin = pluginRegistry.getPlugin(pluginId);
        if (plugin != null) {
            for (MenuFunctionality f : plugin.getFunctionalities()) {
                removeFunctionality(f.getId());
            }
            pluginRegistry.unregisterPlugin(pluginId);
            Printer.success("Plugin unloaded: " + plugin.getName());
        } else {
            Printer.error("Plugin not found: " + pluginId);
        }
    }

    public void stop() {
        this.running = false;
    }

    public void run() {
        running = true;
        while (running) {
            Printer.separator();
            Printer.header("MENU");
            List<MenuFunctionality> sorted = getSorted();
            for (int i = 0; i < sorted.size(); i++) {
                MenuFunctionality f = sorted.get(i);
                System.out.println((i + 1) + " - " + f.getLabel());
            }
            System.out.print("Choose: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid option.");
                continue;
            }

            if (choice >= 1 && choice <= sorted.size()) {
                MenuFunctionality selected = sorted.get(choice - 1);
                Printer.header(selected.getLabel());
                selected.execute();
            } else if (choice == 0) {
                stop();
            } else {
                System.out.println("Invalid option.");
            }
        }
        System.out.println("Shutting down. Goodbye!");
    }
}
