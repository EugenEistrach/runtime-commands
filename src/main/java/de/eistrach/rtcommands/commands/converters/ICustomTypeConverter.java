package de.eistrach.rtcommands.commands.converters;

import java.util.Optional;

public interface ICustomTypeConverter<T> {
    Optional<T> convert(String value);
}
