package de.eistrach.rtcommands.commands.converters.impl;

import de.eistrach.rtcommands.commands.converters.ICustomTypeConverter;

import java.util.Optional;

public class FloatConverter implements ICustomTypeConverter<Float> {

    @Override
    public Optional<Float> convert(final String value) {
        return Optional.of(Float.parseFloat(value));
    }
}
