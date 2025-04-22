package xyz.v4nixd.chaosFrontier.lobbyManager;

import com.maximde.hologramlib.HologramLib;
import com.maximde.hologramlib.hologram.HologramManager;
import com.maximde.hologramlib.hologram.TextHologram;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class LobbyWorld extends ChunkGenerator implements Listener {

    public static JavaPlugin plugin;

    public static void setPlugin(JavaPlugin pluginInstance) {
        plugin = pluginInstance;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (player.getWorld().getName().equals("lobby")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        player.sendMessage("§dInventoryDrag fired by player");

        if (player.getWorld().getName().equals("lobby")) {
            player.sendMessage("§dInventoryDrag fired in lobby");
            event.setCancelled(true);
            player.sendMessage("§dInventoryDrag cancelled");
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getWorld().getName().equals("lobby")) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                ItemStack itemInHand = player.getInventory().getItemInMainHand();
                Material type = itemInHand.getType();

                if (type == Material.LIME_CONCRETE) {
                    LobbyStatus.setReady(player.getUniqueId(), true);
                    player.sendMessage("§aYou are now marked as ready");
                    event.setCancelled(true);
                } else if (type == Material.RED_BED) {
                    LobbyStatus.setReady(player.getUniqueId(), false);
                    player.sendMessage("§cYou are now marked unready");
                    event.setCancelled(true);
                }

                updateLobbyHologram();
            }
        }
    }

    public static void updateLobbyHologram() {
        HologramManager hologramManager = HologramLib.getManager().orElse(null);
        if (hologramManager != null) {
            hologramManager.getHologram("lobby_hologram").ifPresent(hologram -> {
                if (hologram instanceof TextHologram textHologram) {
                    int total = Bukkit.getOnlinePlayers().size();
                    int readyCount = LobbyStatus.countReady();
                    int notReadyCount = LobbyStatus.countNotReady();

                    textHologram.setMiniMessageText(
                            "<gold><bold>Lobby Status</bold></gold>\n" +
                                    "Players: <white>" + total + "</white>\n" +
                                    "Ready: <green>" + readyCount + "</green>\n" +
                                    "Not Ready: <red>" + notReadyCount + "</red>"
                    );
                    textHologram.update();
                }
            });
        }
    }

    public static World createLobbyWorld() {
        if (Bukkit.getWorld("lobby") != null) {
            return Bukkit.getWorld("lobby");
        }

        WorldCreator creator = new WorldCreator("lobby");
        creator.environment(World.Environment.NORMAL);
        creator.generateStructures(false);
        creator.generator(new VoidChunkGenerator());

        World lobbyWorld = Bukkit.createWorld(creator);

        if (lobbyWorld != null) {
            lobbyWorld.setSpawnLocation(0, 64, 0);
            lobbyWorld.setStorm(false);
            lobbyWorld.setThundering(false);
            lobbyWorld.setWeatherDuration(1000000);
            lobbyWorld.setTime(18000);
            lobbyWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            lobbyWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            lobbyWorld.setGameRule(GameRule.DO_INSOMNIA, false);
        }

        return lobbyWorld;
    }

    @SuppressWarnings("deprecation")
    public static void teleportToLobby(Player player) {
        World lobby = Bukkit.getWorld("lobby");

        if (lobby != null) {
            player.teleport(lobby.getSpawnLocation());
            player.setGameMode(GameMode.ADVENTURE);
            player.setAllowFlight(true);
            player.sendMessage("§aYou were teleported to lobby");

            boolean hasReadyItem = player.getInventory().contains(Material.LIME_CONCRETE);
            boolean hasUnreadyItem = player.getInventory().contains(Material.RED_BED);

            if (!hasReadyItem) {
                ItemStack readyItem = new ItemStack(Material.LIME_CONCRETE, 1);
                ItemMeta readyMeta = readyItem.getItemMeta();
                readyMeta.setDisplayName("§aReady");
                readyItem.setItemMeta(readyMeta);

                Inventory inv = player.getInventory();

                inv.setItem(0, readyItem);

                //player.getInventory().addItem(new ItemStack(Material.LIME_CONCRETE, 1));
            }

            if (!hasUnreadyItem) {
                ItemStack unreadyItem = new ItemStack(Material.RED_BED, 1);
                ItemMeta unreadyMeta = unreadyItem.getItemMeta();
                unreadyMeta.setDisplayName("§cUnready");
                unreadyItem.setItemMeta(unreadyMeta);

                Inventory inv = player.getInventory();

                inv.setItem(8, unreadyItem);

                //player.getInventory().addItem(new ItemStack(Material.RED_BED, 1));
            }

            player.sendMessage("§9Click RMB with Lime Concrete selected to ready up.");

            for (Player other : Bukkit.getOnlinePlayers()) {
                if (other != player) {
                    player.hidePlayer(plugin, other);
                    other.hidePlayer(plugin, player);
                }
            }
        } else {
            Bukkit.getLogger().warning("World 'lobby' not found! Couldnt teleport player");
        }
    }

}
