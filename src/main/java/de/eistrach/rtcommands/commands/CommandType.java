package de.eistrach.rtcommands.commands;

import de.eistrach.rtcommands.commands.converters.ICustomTypeConverter;
import de.eistrach.rtcommands.commands.suggestions.ISuggestionProvider;

import java.util.Optional;

public class CommandType {
    private final String typeName;
    private final ICustomTypeConverter typeConverter;
    private final ISuggestionProvider suggestionProvider;

    public CommandType(final String typeName, final ICustomTypeConverter typeConverter,
                       final ISuggestionProvider suggestionProvider) {
        this.typeName = typeName;
        this.typeConverter = typeConverter;
        this.suggestionProvider = suggestionProvider;
    }

    public String getTypeName() {
        return typeName;
    }

    public ICustomTypeConverter getTypeConverter() {
        return typeConverter;
    }

    public Optional<ISuggestionProvider> getSuggestionProvider() {
        return Optional.ofNullable(suggestionProvider);
    }
}
