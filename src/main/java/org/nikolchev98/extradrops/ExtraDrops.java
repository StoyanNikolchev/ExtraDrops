package org.nikolchev98.extradrops;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.nikolchev98.extradrops.listeners.EntityDeathListener;

public final class ExtraDrops extends JavaPlugin {
    private FileConfiguration config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        Bukkit.getPluginManager().registerEvents(new EntityDeathListener(config), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}