package de.eistrach.rtcommands.commands.converters.impl;

import de.eistrach.rtcommands.commands.converters.ICustomTypeConverter;

import java.util.Optional;

public class StringConverter implements ICustomTypeConverter<String> {

    @Override
    public Optional<String> convert(final String value) {
        return Optional.of(value);
    }
}
