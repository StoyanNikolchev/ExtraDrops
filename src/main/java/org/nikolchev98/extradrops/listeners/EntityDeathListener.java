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

import static org.nikolchev98.extradrops.enums.LoggerMessages.*;
import static org.nikolchev98.extradrops.utils.Utils.*;

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

        // Ends here if the mob has no extra drops configured in the yml
        if (!config.contains(entityName)) {
            return;
        }

        ConfigurationSection dropsSection = config.getConfigurationSection(entityName + ".Added_Drops");

        // Checks if "Added_Drops" for this mob exists in the yml
        if (dropsSection == null) {
            logger.warning(String.format(ADDED_DROPS_DO_NOT_EXIST, entityName));
            return;
        }

        Map<String, Map<String, Integer>> addedDrops = new HashMap<>();

        // Fills up addedDrops map with data from the config
        for (String itemName : dropsSection.getKeys(false)) {
            ConfigurationSection itemConfig = dropsSection.getConfigurationSection(itemName);

            if (itemConfig == null) {
                logger.warning(String.format(SKIPPING_INVALID_ITEM_CONFIGURATION, itemName, entityName));
                continue;
            }

            // Retrieves percentage, min_amount, and max_amount from config
            int percentage = itemConfig.getInt("Chance", 0);
            int minAmount = itemConfig.getInt("Min_Amount", 0);
            int maxAmount = itemConfig.getInt("Max_Amount", 0);

            Map<String, Integer> itemData = new HashMap<>();
            itemData.put("Chance", percentage);
            itemData.put("Min_Amount", minAmount);
            itemData.put("Max_Amount", maxAmount);

            addedDrops.put(itemName, itemData);
        }

        for (Map.Entry<String, Map<String, Integer>> dropEntry : addedDrops.entrySet()) {
            String itemName = dropEntry.getKey();
            Map<String, Integer> itemData = dropEntry.getValue();

            int chance = itemData.get("Chance");
            int minAmount = itemData.get("Min_Amount");
            int maxAmount = itemData.get("Max_Amount");

            if (chance == 0 || chanceFails(chance)) {
                continue;
            }

            Material itemMaterial = getMaterial(itemName);

            if (itemMaterial == null) {
                logger.warning(String.format(INVALID_ITEM_NAME, itemName, entityName));
                continue;
            }

            int amount = generateRandomAmount(minAmount, maxAmount);
            ItemStack itemStack = new ItemStack(itemMaterial, amount);

            deathEvent.getDrops().add(itemStack);
            logger.info(String.format(EXTRA_ITEM_DROPPED, entityName, itemMaterial.name(), amount));
        }
    }
}