package xyz.v4nixd.chaosFrontier.lobbyManager;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.v4nixd.chaosFrontier.ChaosFrontier;

@SuppressWarnings("deprecation")
public class LobbyPlaceholderExpansion extends PlaceholderExpansion {
    private final ChaosFrontier plugin;

    public LobbyPlaceholderExpansion(ChaosFrontier plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "lobby";
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        switch (identifier) {
            case "players":
                return String.valueOf(Bukkit.getOnlinePlayers().size());
            case "ready":
                return String.valueOf(LobbyStatus.countReady());
            case "not_ready":
                return String.valueOf(LobbyStatus.countNotReady());
            default:
                return "";
        }
    }
}
