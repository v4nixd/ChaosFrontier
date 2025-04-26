package xyz.v4nixd.chaosFrontier;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import xyz.v4nixd.chaosFrontier.commands.GenerateWorldCommand;
import xyz.v4nixd.chaosFrontier.lobby.LobbyWorld;

public final class ChaosFrontier extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("ChaosFrontier loaded");

        setCommandExecutor("genworld", new GenerateWorldCommand());

        LobbyWorld lobbyWorld = new LobbyWorld(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("ChaosFrontier unloaded");
    }

    // Local Utils

    public void registerEvent(@NotNull Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public void setCommandExecutor(@NotNull String commandName, @NotNull CommandExecutor executor) {
        PluginCommand command = getCommand(commandName);

        if (command == null) {
            throw new NullPointerException("Command named '" + commandName + "' not found.");
        }

        command.setExecutor(executor);
    }
}