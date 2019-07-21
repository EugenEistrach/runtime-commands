package de.eistrach.rtcommands.commands.suggestions;

import de.eistrach.rtcommands.commands.CommandArgument;

import java.util.List;

public interface ISuggestionProvider {
    List<String> getSuggestions(CommandArgument argument, String consoleArgument);
}
