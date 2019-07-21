package de.eistrach.rtcommands.commands.suggestions.impl;

import de.eistrach.rtcommands.commands.CommandArgument;
import de.eistrach.rtcommands.commands.suggestions.ISuggestionProvider;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;

import java.util.List;
import java.util.stream.Collectors;

public class PermissionSuggestionProvider implements ISuggestionProvider {
    @Override
    public List<String> getSuggestions(final CommandArgument argument, final String consoleArgument) {
        return Bukkit.getPluginManager().getPermissions().stream()
                .map(Permission::getName)
                .collect(Collectors.toList());
    }
}
