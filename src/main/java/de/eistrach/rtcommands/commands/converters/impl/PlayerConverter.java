package de.eistrach.rtcommands.commands.converters.impl;

import de.eistrach.rtcommands.commands.converters.ICustomTypeConverter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class PlayerConverter implements ICustomTypeConverter<Player> {

    private final Plugin plugin;

    public PlayerConverter(final Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Optional<Player> convert(final String value) {
        return Optional.ofNullable(plugin.getServer().getPlayerExact(value));
    }
}
