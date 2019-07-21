package de.eistrach.rtcommands;

import de.eistrach.rtcommands.commands.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class RuntimeCommands extends JavaPlugin {

    @Override
    public void onLoad() {
        final CommandManager commandManager = new CommandManager(this);
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
