package xyz.v4nixd.chaosFrontier.teams;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;
import xyz.v4nixd.chaosFrontier.gui.TeamSelectorGUI;

public class TeamCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            TeamSelectorGUI.openTeamGUI((Player) sender);
        }
        return true;
    }
}
