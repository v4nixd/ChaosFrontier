package xyz.v4nixd.chaosFrontier;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;
import xyz.v4nixd.chaosFrontier.lobbyManager.LobbyStatus;
import xyz.v4nixd.chaosFrontier.lobbyManager.LobbyWorld;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        LobbyStatus.removePlayer(player.getUniqueId());
        LobbyWorld.updateLobbyHologram();
    }
}
