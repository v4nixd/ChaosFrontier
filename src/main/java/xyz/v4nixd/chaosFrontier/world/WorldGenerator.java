package xyz.v4nixd.chaosFrontier.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.jetbrains.annotations.NotNull;

public class WorldGenerator {
    public World generateWorld(@NotNull String worldName, Boolean genStructuresBool, int borderSize) {
        if (genStructuresBool == null) {
            genStructuresBool = false;
        }

        if (borderSize < 0) {
            borderSize = Math.abs(borderSize);
        }

        worldName = "gameworld_" + worldName;

        WorldCreator creator = new WorldCreator(worldName);
        creator.generateStructures(genStructuresBool);
        World gameWorld = Bukkit.createWorld(creator);

        if (gameWorld == null) {
            throw new IllegalStateException("Failed to create world: " + worldName);
        }

        WorldBorder border = gameWorld.getWorldBorder();
        border.setCenter(gameWorld.getSpawnLocation());
        border.setSize(borderSize);

        return gameWorld;
    }
}
