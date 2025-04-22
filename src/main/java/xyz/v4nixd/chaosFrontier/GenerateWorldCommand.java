package xyz.v4nixd.chaosFrontier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GenerateWorldCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only user can use this command");
            return true;
        }

        String worldName;
        if (args.length > 0) {
            worldName = args[0];
        } else {
            worldName = "game_world_" + System.currentTimeMillis();
        }

        WorldCreator creator = new WorldCreator(worldName);
        creator.generateStructures(false);
        World gameWorld = Bukkit.createWorld(creator);

        player.teleport(gameWorld.getSpawnLocation());
        player.sendMessage("§aWorld " + worldName + " created");

        WorldBorder border = gameWorld.getWorldBorder();
        border.setCenter(gameWorld.getSpawnLocation());
        border.setSize(100);

        player.sendMessage("§bBorder set to 100x100 blocks");

        return true;
    }
}