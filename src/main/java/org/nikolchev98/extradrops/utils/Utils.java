package org.nikolchev98.extradrops.utils;

import org.bukkit.Material;

import java.util.Random;

public class Utils {
    private static final Random random = new Random();

    public static boolean chanceFails(int chance) {
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

    public static int generateRandomAmount(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }
}