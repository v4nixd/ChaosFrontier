package xyz.v4nixd.chaosFrontier.gui;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.v4nixd.chaosFrontier.teams.TeamColor;

import java.util.Collections;

public class TeamSelectorGUI {

    @SuppressWarnings("deprecated")
    public static void openTeamGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, "§lВыбор команды");

        for (TeamColor color : TeamColor.values()) {
            ItemStack item = new ItemStack(color.icon);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(color.color + color.name);
            meta.setLore(Collections.singletonList("§7Нажмите, чтобы присоединиться"));
            item.setItemMeta(meta);

            inv.addItem(item);
        }

        player.openInventory(inv);
    }
}
