package de.eistrach.rtcommands.commands.converters.impl;

import de.eistrach.rtcommands.commands.converters.ICustomTypeConverter;

import java.util.Optional;

public class DoubleConverter implements ICustomTypeConverter<Double> {

    @Override
    public Optional<Double> convert(final String value) {
        return Optional.of(Double.parseDouble(value));
    }
}
