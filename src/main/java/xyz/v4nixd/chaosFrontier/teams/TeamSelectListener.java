package xyz.v4nixd.chaosFrontier.teams;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class TeamSelectListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().contains("Выбор команды")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || clicked.getType() == Material.AIR) return;

            for (TeamColor color : TeamColor.values()) {
                if (clicked.getType() == color.icon) {
                    boolean success = TeamManager.joinTeam(player, color);
                    if (!success) {
                        player.sendMessage("§cЭта команда уже заполнена!");
                    }
                    player.closeInventory();
                    break;
                }
            }
        }
    }
}
