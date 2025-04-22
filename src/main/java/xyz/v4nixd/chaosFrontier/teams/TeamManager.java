package xyz.v4nixd.chaosFrontier.teams;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Scoreboard;

public class TeamManager {
    private static final Map<TeamColor, Set<UUID>> teams = new HashMap<>();
    private static final int MAX_PLAYERS_PER_TEAM = 4;

    static {
        for (TeamColor color : TeamColor.values()) {
            teams.put(color, new HashSet<>());
        }
    }

    public static boolean joinTeam(Player player, TeamColor color) {
        Set<UUID> team = teams.get(color);
        if (team.size() >= MAX_PLAYERS_PER_TEAM) return false;

        leaveTeam(player);
        team.add(player.getUniqueId());
        player.sendMessage(color.color + "Вы присоединились к команде " + color.name);
        return true;
    }

    public static void leaveTeam(Player player) {
        for (Set<UUID> team : teams.values()) {
            team.remove(player.getUniqueId());
        }
    }

    public static TeamColor getTeam(Player player) {
        for (Map.Entry<TeamColor, Set<UUID>> entry : teams.entrySet()) {
            if (entry.getValue().contains(player.getUniqueId())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Map<TeamColor, Set<UUID>> getTeams() {
        return teams;
    }
}
