package xyz.v4nixd.chaosFrontier;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import xyz.v4nixd.chaosFrontier.lobbyManager.LobbyWorld;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (Bukkit.getWorld("lobby") != null) {
            LobbyWorld.teleportToLobby(player);
            LobbyWorld.updateLobbyHologram();
        } else {
            player.sendMessage("§cLobby world isnt loaded yet, try rejoining later");
        }
    }
}
