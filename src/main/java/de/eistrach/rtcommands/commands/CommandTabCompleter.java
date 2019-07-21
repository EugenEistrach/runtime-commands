package de.eistrach.rtcommands.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class CommandTabCompleter implements TabCompleter {

    private final CustomCommand customCommand;

    public CommandTabCompleter(final CustomCommand customCommand) {
        this.customCommand = customCommand;
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias,
                                      final String[] args) {

        return customCommand.getTabSuggestions(args);
    }
}
