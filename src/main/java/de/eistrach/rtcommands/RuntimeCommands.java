package de.eistrach.rtcommands;

import de.eistrach.rtcommands.commands.CommandManager;
import de.eistrach.rtcommands.commands.converters.impl.StringConverter;
import de.eistrach.rtcommands.commands.suggestions.impl.PermissionSuggestionProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class RuntimeCommands extends JavaPlugin {

    @Override
    public void onLoad() {
        final CommandManager commandManager = new CommandManager(this);
        commandManager.createType("Permission", new StringConverter(), new PermissionSuggestionProvider());
        commandManager.registerAllRuntimeCommands();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
