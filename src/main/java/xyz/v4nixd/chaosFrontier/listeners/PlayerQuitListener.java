package xyz.v4nixd.chaosFrontier.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        MiniMessage mm = MiniMessage.miniMessage();

        Component playerQuitMessage = mm.deserialize("<red><bold>[-]</bold></red> " + player.getName());

        event.quitMessage(playerQuitMessage);
    }
}
