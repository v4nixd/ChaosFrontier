package xyz.v4nixd.chaosFrontier.lobbyManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class NoMovementListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getWorld().getName().equals("lobby")) {
            event.setCancelled(true);

            player.setVelocity(new Vector(0, 0, 0));

            if (player.getLocation().getY() < 1) {
                player.teleport(player.getWorld().getSpawnLocation());
            }
        }
    }
}
