package xyz.v4nixd.chaosFrontier.lobby;

import com.maximde.hologramlib.HologramLib;
import com.maximde.hologramlib.hologram.Hologram;
import com.maximde.hologramlib.hologram.HologramManager;
import com.maximde.hologramlib.hologram.TextHologram;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import xyz.v4nixd.chaosFrontier.ChaosFrontier;

import java.util.Optional;

public class LobbyWorldManager extends ChunkGenerator {

    private final ChaosFrontier plugin;
    private static final String HOLOGRAM_NAME = "lobby_hologram";

    public LobbyWorldManager(ChaosFrontier plugin) {
        this.plugin = plugin;
    }

    public static World createLobbyWorld() {
        if (Bukkit.getWorld("lobby") != null) {
            return Bukkit.getWorld("lobby");
        }

        WorldCreator creator = new WorldCreator("lobby");
        creator.environment(World.Environment.NORMAL);
        creator.generateStructures(false);
        creator.generator(new VoidChunkGenerator());

        World lobbyWorld = Bukkit.createWorld(creator);

        if (lobbyWorld == null) {
            throw new IllegalStateException("Failed to generate lobbyWorld");
        }

        lobbyWorld.setSpawnLocation(0, 64, 0);
        lobbyWorld.setStorm(false);
        lobbyWorld.setThundering(false);
        lobbyWorld.setWeatherDuration(1000000);
        lobbyWorld.setTime(18000);
        lobbyWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        lobbyWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        lobbyWorld.setGameRule(GameRule.DO_INSOMNIA, false);

        spawnHologram(lobbyWorld);

        return lobbyWorld;
    }

    private static void spawnHologram(World lobbyWorld) {
        HologramManager hologramManager = HologramLib.getManager().orElseThrow();

        TextHologram hologram = createHologram();
        Location hologramLocation = new Location(lobbyWorld, 0, 66 ,2);

        hologramManager.spawn(hologram, hologramLocation);
    }

    private static TextHologram createHologram() {
        HologramManager hologramManager = HologramLib.getManager().orElseThrow();

        Optional<Hologram<?>> hologram = hologramManager.getHologram(HOLOGRAM_NAME);

        if (hologram.isPresent()) {
            return (TextHologram) hologram.get();
        }

        return new TextHologram(HOLOGRAM_NAME)
                .setMiniMessageText("Welcome to <gold><bold>ChaosFrontier</bold></gold> lobby");
    }

    public void teleportToLobby(World lobby, Player player) {
        MiniMessage mm = MiniMessage.miniMessage();

        Component teleportedMessage = mm.deserialize("<green>You have been teleported to <bold>Lobby</bold>");

        if (lobby == null) {
            throw new IllegalStateException("Lobby world is null");
        }

        player.teleport(lobby.getSpawnLocation());
        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(true);
        player.sendMessage(teleportedMessage);

        hidePlayers(player);
    }

    private void hidePlayers(Player player) {
        for (Player other : Bukkit.getOnlinePlayers()) {
            if (other != player) {
                player.hidePlayer(plugin, other);
                other.hidePlayer(plugin, player);
            }
        }
    }
}
