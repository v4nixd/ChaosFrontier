package xyz.v4nixd.chaosFrontier;

import com.maximde.hologramlib.HologramLib;
import com.maximde.hologramlib.hologram.HologramManager;
import com.maximde.hologramlib.hologram.TextHologram;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.v4nixd.chaosFrontier.lobbyManager.LobbyPlaceholderExpansion;
import xyz.v4nixd.chaosFrontier.lobbyManager.LobbyWorld;
import xyz.v4nixd.chaosFrontier.lobbyManager.NoMovementListener;
import xyz.v4nixd.chaosFrontier.teams.TeamCommand;
import xyz.v4nixd.chaosFrontier.teams.TeamSelectListener;

public final class ChaosFrontier extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("ChaosFrontier loaded");

        HologramLib.init();
        HologramManager holoMgr = HologramLib.getManager()
                .orElseThrow(() -> new IllegalStateException("Failed to initialize HologramLib manager"));

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new LobbyPlaceholderExpansion(this).register();
        } else {
            getLogger().warning("Placeholder API not found. Placeholders will not be available");
        }

        LobbyWorld.setPlugin(this);
        World lobbyWorld = LobbyWorld.createLobbyWorld();
        if (lobbyWorld != null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                LobbyWorld.teleportToLobby(player);
            }
        }

        String hologramText = "<gold><bold>Lobby Status</bold></gold>\n" +
                "Players: <white>%lobby_players%</white>\n" +
                "Ready: <green>%lobby_ready%</green>\n" +
                "Not Ready: <red>%lobby_not_ready%</red>";

        for (Player player : Bukkit.getOnlinePlayers()) {
            hologramText = PlaceholderAPI.setPlaceholders(player, hologramText);
        }

        TextHologram hologram = new TextHologram("lobby_hologram")
                .setMiniMessageText(hologramText);

        Location holoLoc = new Location(lobbyWorld, 0, 66, 2);
        holoMgr.spawn(hologram, holoLoc);

        this.getCommand("genworld").setExecutor(new GenerateWorldCommand());
        this.getCommand("teams").setExecutor(new TeamCommand());
        getServer().getPluginManager().registerEvents(new NoMovementListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new TeamSelectListener(), this);
        getServer().getPluginManager().registerEvents(new LobbyWorld(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("ChaosFrontier unloaded");
    }
}