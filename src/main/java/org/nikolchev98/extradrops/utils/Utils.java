package org.nikolchev98.extradrops.utils;

import org.bukkit.Material;

import java.util.Random;

public class Utils {

    public static boolean chanceFails(int chance) {
        Random random = new Random();
        int randomNumber = random.nextInt(101);

        return randomNumber >= chance;
    }

    public static Material getMaterial(String name) {
        try {
            return Material.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}