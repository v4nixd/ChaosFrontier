package xyz.v4nixd.chaosFrontier.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import xyz.v4nixd.chaosFrontier.world.WorldGenerator;

public class GenerateWorldCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only user can use this command");
            return false;
        }

        String worldName = getWorldName(args);
        boolean genStructureBool = getGenerateStructuresBool(args);
        int borderSize = 100;

        World world = WorldGenerator.generateWorld(worldName, genStructureBool, borderSize);
        String generatedMessage = "§aWorld §r" + world.getName() + " §a created";
        player.sendMessage(generatedMessage);

        teleportPlayerToWorld(world, player);

        return true;
    }

    private String getWorldName(String[] args) {
        String response;

        if (args.length > 0) {
            response = args[0];
        } else {
            response = String.valueOf(System.currentTimeMillis());
        }

        return response;
    }

    private void teleportPlayerToWorld(World world, Player player) {
        Location spawnLocation = world.getSpawnLocation();
        String teleportedMessage = "§aTeleported §r§l§n" + player.getName() + " §r§ato " + world.getName();

        player.teleport(spawnLocation);
        player.sendMessage(teleportedMessage);
    }

    private boolean getGenerateStructuresBool(String[] args) {
        return args.length > 1 && Boolean.parseBoolean(args[1].trim().toLowerCase());
    }
}
