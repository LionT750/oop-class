package events;

import menu.MenuFunctionality;

public class CommandExecutedEvent extends Event {
    private MenuFunctionality command;

    public CommandExecutedEvent(MenuFunctionality command) {
        super("command-executed");
        this.command = command;
    }

    public MenuFunctionality getCommand() { return command; }
}
