# ExtraDrops

ExtraDrops is a Minecraft (1.20) Spigot plugin that allows server administrators to customize and add additional drops to entities upon their death.

## Features

- **Customizable Drops**: Define additional drops for specific Minecraft entities.
- **Percentage-Based Drops**: Specify the chance (percentage) for each drop to occur.
- **Customizable Amount**: Set a minimum and maximum amount for each drop.

# Example Configuration for ExtraDrops
```yaml
ENDER_DRAGON:
  NETHER_STAR:
    Chance: 100
    Min_Amount: 1
    Max_Amount: 300
