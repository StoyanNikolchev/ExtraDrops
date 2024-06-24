package org.nikolchev98.extradrops;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.nikolchev98.extradrops.listeners.EntityDeathListener;

import java.util.logging.Logger;

import static org.nikolchev98.extradrops.enums.LoggerMessages.PLUGIN_DISABLED;
import static org.nikolchev98.extradrops.enums.LoggerMessages.PLUGIN_ENABLED;

public final class ExtraDrops extends JavaPlugin {
    private FileConfiguration config;
    private Logger logger;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        logger = getLogger();
        Bukkit.getPluginManager().registerEvents(new EntityDeathListener(config, logger), this);
        logger.info(PLUGIN_ENABLED);
    }

    @Override
    public void onDisable() {
        logger.info(PLUGIN_DISABLED);
    }
}
