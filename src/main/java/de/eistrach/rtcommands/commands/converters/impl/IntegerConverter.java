package de.eistrach.rtcommands.commands.converters.impl;

import de.eistrach.rtcommands.commands.converters.ICustomTypeConverter;

import java.util.Optional;

public class IntegerConverter implements ICustomTypeConverter<Integer> {
    @Override
    public Optional<Integer> convert(final String value) {
            return Optional.of(Integer.parseInt(value));
    }
}
