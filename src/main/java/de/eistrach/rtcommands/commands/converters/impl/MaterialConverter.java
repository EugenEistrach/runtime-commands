package de.eistrach.rtcommands.commands.converters.impl;

import de.eistrach.rtcommands.commands.converters.ICustomTypeConverter;
import org.bukkit.Material;

import java.util.Optional;

public class MaterialConverter implements ICustomTypeConverter<Material> {

    @Override
    public Optional<Material> convert(final String value) {
        return Optional.ofNullable(Material.getMaterial(value.toUpperCase()));
    }
}
