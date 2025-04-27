package xyz.v4nixd.chaosFrontier;

import com.maximde.hologramlib.HologramLib;
import com.maximde.hologramlib.hologram.HologramManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import xyz.v4nixd.chaosFrontier.commands.GenerateWorldCommand;
import xyz.v4nixd.chaosFrontier.listeners.PlayerJoinListener;
import xyz.v4nixd.chaosFrontier.listeners.PlayerMoveListener;
import xyz.v4nixd.chaosFrontier.listeners.PlayerQuitListener;
import xyz.v4nixd.chaosFrontier.lobby.LobbyWorldManager;

public final class ChaosFrontier extends JavaPlugin {

    public HologramManager hologramManager;
    public LobbyWorldManager lobbyWorldManager;

    public static ChaosFrontier getPlugin() {
        return JavaPlugin.getPlugin(ChaosFrontier.class);
    }

    public void onLoad() {
        HologramLib.onLoad(this);
    }

    @Override
    public void onEnable() {
        getLogger().info("ChaosFrontier loaded");

        initHologramLib();

        registerEvent(new PlayerJoinListener());
        registerEvent(new PlayerQuitListener());
        registerEvent(new PlayerMoveListener());
        setCommandExecutor("genworld", new GenerateWorldCommand());

        lobbyWorldManager = new LobbyWorldManager(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("ChaosFrontier unloaded");
    }

    // Local Utils

    private void initHologramLib() {
        hologramManager = HologramLib.getManager().orElse(null);
        if (hologramManager == null) {
            getLogger().severe("Failed to initialize HologramLib manager");
        }
    }

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