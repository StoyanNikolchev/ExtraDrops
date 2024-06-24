# ExtraDrops

ExtraDrops is a Minecraft (1.20) Spigot plugin that allows server administrators to customize and add additional drops to entities upon their death.

## Features

- **Customizable Drops**: Define additional drops for specific Minecraft entities.
- **Percentage-Based Drops**: Specify the chance (percentage) for each drop to occur.

# Example Configuration for ExtraDrops
```yaml
ELDER_GUARDIAN:
  Added_Drops:
    - DIAMOND: 50
    - EMERALD_BLOCK: 30
ENDER_DRAGON:
  Added_Drops:
    - NETHER_STAR: 10
