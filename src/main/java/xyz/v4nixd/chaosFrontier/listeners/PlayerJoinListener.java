package xyz.v4nixd.chaosFrontier.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.v4nixd.chaosFrontier.ChaosFrontier;
import xyz.v4nixd.chaosFrontier.lobby.LobbyWorldManager;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        MiniMessage mm = MiniMessage.miniMessage();
        LobbyWorldManager lobbyWorldManager = ChaosFrontier.getPlugin().lobbyWorldManager;

        Component playerJoinMessage = mm.deserialize("<green><bold>[+]</bold></green> " + player.getName());

        event.joinMessage(playerJoinMessage);
        World lobbyWorld = LobbyWorldManager.createLobbyWorld();
        lobbyWorldManager.teleportToLobby(lobbyWorld, player);
    }
}
