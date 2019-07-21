package de.eistrach.rtcommands.commands.suggestions.impl;

import de.eistrach.rtcommands.commands.CommandArgument;
import de.eistrach.rtcommands.commands.suggestions.ISuggestionProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerSuggestionProvider implements ISuggestionProvider {
    @Override
    public List<String> getSuggestions(final CommandArgument argument, final String consoleArgument) {
        final Collection<Player> onlinePlayers = (Collection<Player>) Bukkit.getOnlinePlayers();
        return onlinePlayers.stream()
                .map(HumanEntity::getName)
                .collect(Collectors.toList());
    }
}
