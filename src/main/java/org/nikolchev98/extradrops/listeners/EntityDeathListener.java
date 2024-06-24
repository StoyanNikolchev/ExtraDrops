package org.nikolchev98.extradrops.listeners;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.nikolchev98.extradrops.utils.Utils.chanceFails;
import static org.nikolchev98.extradrops.utils.Utils.getMaterial;

public class EntityDeathListener implements Listener {
    private final FileConfiguration config;
    private final Logger logger;

    public EntityDeathListener(FileConfiguration config, Logger logger) {
        this.config = config;
        this.logger = logger;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent deathEvent) {
        LivingEntity entity = deathEvent.getEntity();
        String entityName = entity.getType().name();

        //Ends here if the mob has no extra drops configured in the yml
        if (!config.contains(entityName)) {
            return;
        }

        ConfigurationSection dropsSection = config.getConfigurationSection(entityName + ".Added_Drops");

        //Checks if "Added_Drops" for this mob exists in the yml
        if (dropsSection == null) {
            logger.warning(String.format("Extra drops for %s configured incorrectly. Please check config.yml%n", entityName));
            return;
        }

        Map<String, Integer> addedDrops = new HashMap<>();

        for (String itemName : dropsSection.getKeys(false)) {

            //Percentage defaults to 0 if the value set in the config is invalid or empty
            int percentage = dropsSection.getInt(itemName);
            addedDrops.put(itemName, percentage);
        }

        for (Map.Entry<String, Integer> dropEntry : addedDrops.entrySet()) {
            int chance = dropEntry.getValue();

            if (chance == 0 || chanceFails(chance)) {
                return;
            }

            String itemName = dropEntry.getKey();
            Material itemMaterial = getMaterial(itemName);

            if (itemMaterial == null) {
                logger.warning(String.format("Incorrect item name: '%s' for entity %s. Please check config.yml!", itemName, entityName));
                return;
            }

            ItemStack itemStack = new ItemStack(itemMaterial);

            deathEvent.getDrops().add(itemStack);
            logger.info(String.format("%s dropped an extra item: %s%n", entityName, itemMaterial.name()));
        }
    }
}