package xyz.v4nixd.chaosFrontier;

import org.bukkit.plugin.java.JavaPlugin;

public final class ChaosFrontier extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("ChaosFrontier loaded");
    }

    @Override
    public void onDisable() {
        getLogger().info("ChaosFrontier unloaded");
    }
}