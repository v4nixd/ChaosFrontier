package xyz.v4nixd.chaosFrontier.lobby;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;
import xyz.v4nixd.chaosFrontier.ChaosFrontier;

public class LobbyWorld extends ChunkGenerator {

    private final ChaosFrontier plugin;

    public LobbyWorld(ChaosFrontier plugin) {
        this.plugin = plugin;
    }

    public static World createLobbyWorld() {
        if (Bukkit.getWorld("lobby") != null) {
            return Bukkit.getWorld("lobby");
        }

        WorldCreator creator = new WorldCreator("lobby");
        creator.environment(World.Environment.NORMAL);
        creator.generateStructures(false);
        creator.generator(); // new VoidChunkGenerator()

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

        return lobbyWorld;
    }
}
