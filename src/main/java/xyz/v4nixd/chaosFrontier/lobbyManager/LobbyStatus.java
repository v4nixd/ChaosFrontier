package xyz.v4nixd.chaosFrontier.lobbyManager;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class LobbyStatus {
    private static final HashMap<UUID, Boolean> readyMap = new HashMap<>();

    public static void setReady(UUID uuid, boolean isReady) {
        readyMap.put(uuid, isReady);
    }

    public static int countReady() {
        return (int) readyMap.values().stream().filter(b -> b).count();
    }

    public static int countNotReady() {
        return (int) Bukkit.getOnlinePlayers().stream()
                .filter(p -> !readyMap.getOrDefault(p.getUniqueId(), false)).count();
    }

    public static boolean isReady(UUID uuid) {
        return readyMap.getOrDefault(uuid, false);
    }

    public static void removePlayer(UUID uuid) {
        readyMap.remove(uuid);
    }
}
