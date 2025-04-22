package xyz.v4nixd.chaosFrontier.teams;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum TeamColor {
    RED("Красные", "§c", Material.RED_WOOL, "R"),
    BLUE("Синие", "§9", Material.BLUE_WOOL, "B"),
    GREEN("Зеленые", "§a", Material.GREEN_WOOL, "G"),
    YELLOW("Жёлтые", "§e", Material.YELLOW_WOOL, "Y");

    public final String name;
    public final String color;
    public final Material icon;
    private final String prefix;

    TeamColor(String name, String color, Material icon, String prefix) {
        this.name = name;
        this.color = color;
        this.icon = icon;
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getColor() {
        return color;
    }
}
