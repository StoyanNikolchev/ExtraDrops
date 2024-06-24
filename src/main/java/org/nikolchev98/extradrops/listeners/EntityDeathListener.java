package org.nikolchev98.extradrops.listeners;

import org.bukkit.Bukkit;
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
import java.util.Random;

public class EntityDeathListener implements Listener {
    private final FileConfiguration config;

    public EntityDeathListener(FileConfiguration config) {
        this.config = config;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent deathEvent) {
        LivingEntity entity = deathEvent.getEntity();
        String entityName = entity.getType().name();

        if (!config.contains(entityName)) {
            return;
        }

        ConfigurationSection dropsSection = config.getConfigurationSection(entityName + ".Added_Drops");
        Map<String, Integer> addedDrops = new HashMap<>();

        for (String itemName : dropsSection.getKeys(false)) {
            int percentage = dropsSection.getInt(itemName);
            addedDrops.put(itemName, percentage);
        }

        for (Map.Entry<String, Integer> dropEntry : addedDrops.entrySet()) {
            int chance = dropEntry.getValue();
            if (chance == 0 || chanceFails(chance)) {
                return;
            }

            Material itemMaterial = getMaterial(dropEntry.getKey());
            if (itemMaterial != null) {
                ItemStack itemStack = new ItemStack(itemMaterial);

                deathEvent.getDrops().add(itemStack);
                System.out.printf("%s dropped an additional %s%n", entityName, itemMaterial.name());
            }
        }
    }

    private boolean chanceFails(int chance) {
        Random random = new Random();
        int randomNumber = random.nextInt(101);

        return randomNumber >= chance;
    }

    private Material getMaterial(String name) {
        try {
            return Material.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
