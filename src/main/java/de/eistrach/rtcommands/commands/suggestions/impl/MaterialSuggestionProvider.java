package de.eistrach.rtcommands.commands.suggestions.impl;

import de.eistrach.rtcommands.commands.CommandArgument;
import de.eistrach.rtcommands.commands.suggestions.ISuggestionProvider;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MaterialSuggestionProvider implements ISuggestionProvider {

    @Override
    public List<String> getSuggestions(final CommandArgument argument, final String consoleArgument) {
        return Arrays.stream(Material.values()).map(Enum::name).collect(Collectors.toList());
    }
}
