package de.eistrach.rtcommands.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandInfo {

    private final String label;
    private final Command command;
    private final CommandSender sender;
    private final String[] args;

    public CommandInfo(final String label, final Command command, final CommandSender sender,
                       final String[] args) {
        this.label = label;
        this.command = command;
        this.sender = sender;
        this.args = args;
    }

    public String getLabel() {
        return label;
    }

    public Command getCommand() {
        return command;
    }

    public CommandSender getSender() {
        return sender;
    }

    public String[] getArgs() {
        return args;
    }
}
