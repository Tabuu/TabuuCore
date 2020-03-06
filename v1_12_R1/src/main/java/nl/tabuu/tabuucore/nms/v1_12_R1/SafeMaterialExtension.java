package nl.tabuu.tabuucore.nms.v1_12_R1;

import nl.tabuu.tabuucore.material.SafeMaterial;
import nl.tabuu.tabuucore.nms.wrapper.ISafeMaterialExtension;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;

public class SafeMaterialExtension implements ISafeMaterialExtension {

    @Override
    public ItemStack toItemStack(SafeMaterial material) {
        // Try translating material with custom dictionary.
        switch (material) {
            //region Building Blocks

            //region Wood
            case OAK_PLANKS:
                return new ItemStack(Material.WOOD);
            case SPRUCE_PLANKS:
                return new ItemStack(Material.WOOD, 1, (byte) 1);
            case BIRCH_PLANKS:
                return new ItemStack(Material.WOOD, 1, (byte) 2);
            case JUNGLE_PLANKS:
                return new ItemStack(Material.WOOD, 1, (byte) 3);
            case ACACIA_PLANKS:
                return new ItemStack(Material.WOOD, 1, (byte) 4);
            case DARK_OAK_PLANKS:
                return new ItemStack(Material.WOOD, 1, (byte) 5);

            case OAK_WOOD:
                return new ItemStack(Material.LOG, 1, (byte) 0);
            case SPRUCE_WOOD:
                return new ItemStack(Material.LOG, 1, (byte) 1);
            case BIRCH_WOOD:
                return new ItemStack(Material.LOG, 1, (byte) 2);
            case JUNGLE_WOOD:
                return new ItemStack(Material.LOG, 1, (byte) 3);
            case ACACIA_WOOD:
                return new ItemStack(Material.LOG_2, 1, (byte) 0);
            case DARK_OAK_WOOD:
                return new ItemStack(Material.LOG_2, 1, (byte) 1);

            case OAK_LOG:
                return new ItemStack(Material.LOG, 1, (byte) 0);
            case SPRUCE_LOG:
                return new ItemStack(Material.LOG, 1, (byte) 1);
            case BIRCH_LOG:
                return new ItemStack(Material.LOG, 1, (byte) 2);
            case JUNGLE_LOG:
                return new ItemStack(Material.LOG, 1, (byte) 3);
            case ACACIA_LOG:
                return new ItemStack(Material.LOG_2, 1, (byte) 0);
            case DARK_OAK_LOG:
                return new ItemStack(Material.LOG_2, 1, (byte) 1);

            case OAK_STAIRS:
                return new ItemStack(Material.WOOD_STAIRS);
            case SPRUCE_STAIRS:
                return new ItemStack(Material.SPRUCE_WOOD_STAIRS);
            case BIRCH_STAIRS:
                return new ItemStack(Material.BIRCH_WOOD_STAIRS);
            case JUNGLE_STAIRS:
                return new ItemStack(Material.JUNGLE_WOOD_STAIRS);
            case ACACIA_STAIRS:
                return new ItemStack(Material.ACACIA_STAIRS);
            case DARK_OAK_STAIRS:
                return new ItemStack(Material.DARK_OAK_STAIRS);

            case OAK_SLAB:
                return new ItemStack(Material.WOOD_STEP, 1, (byte) 0);
            case SPRUCE_SLAB:
                return new ItemStack(Material.WOOD_STEP, 1, (byte) 1);
            case BIRCH_SLAB:
                return new ItemStack(Material.WOOD_STEP, 1, (byte) 2);
            case JUNGLE_SLAB:
                return new ItemStack(Material.WOOD_STEP, 1, (byte) 3);
            case ACACIA_SLAB:
                return new ItemStack(Material.WOOD_STEP, 1, (byte) 4);
            case DARK_OAK_SLAB:
                return new ItemStack(Material.WOOD_STEP, 1, (byte) 5);
            //endregion

            //region Stone
            case GRANITE:
                return new ItemStack(Material.STONE, 1, (byte) 1);
            case POLISHED_GRANITE:
                return new ItemStack(Material.STONE, 1, (byte) 2);
            case DIORITE:
                return new ItemStack(Material.STONE, 1, (byte) 3);
            case POLISHED_DIORITE:
                return new ItemStack(Material.STONE, 1, (byte) 4);
            case ANDESITE:
                return new ItemStack(Material.STONE, 1, (byte) 5);
            case POLISHED_ANDESITE:
                return new ItemStack(Material.STONE, 1, (byte) 6);

            case CHISELED_SANDSTONE:
                return new ItemStack(Material.SANDSTONE, 1, (byte) 1);
            case CUT_SANDSTONE:
                return new ItemStack(Material.SANDSTONE, 1, (byte) 2);
            case CHISELED_RED_SANDSTONE:
                return new ItemStack(Material.RED_SANDSTONE, 1, (byte) 1);
            case CUT_RED_SANDSTONE:
                return new ItemStack(Material.RED_SANDSTONE, 1, (byte) 2);

            case STONE_BRICKS:
                return new ItemStack(Material.SMOOTH_BRICK);
            case MOSSY_STONE_BRICKS:
                return new ItemStack(Material.SMOOTH_BRICK, 1, (byte) 1);
            case CRACKED_STONE_BRICKS:
                return new ItemStack(Material.SMOOTH_BRICK, 1, (byte) 2);
            case CHISELED_STONE_BRICKS:
                return new ItemStack(Material.SMOOTH_BRICK, 1, (byte) 3);
            case STONE_BRICK_STAIRS:
                return new ItemStack(Material.SMOOTH_STAIRS);

            case STONE_SLAB:
                return new ItemStack(Material.STEP, 1, (byte) 0);
            case SANDSTONE_SLAB:
                return new ItemStack(Material.STEP, 1, (byte) 1);
            case PETRIFIED_OAK_SLAB:
                return new ItemStack(Material.STEP, 1, (byte) 2);
            case COBBLESTONE_SLAB:
                return new ItemStack(Material.STEP, 1, (byte) 3);
            case BRICK_SLAB:
                return new ItemStack(Material.STEP, 1, (byte) 4);
            case STONE_BRICK_SLAB:
                return new ItemStack(Material.STEP, 1, (byte) 5);
            case QUARTZ_SLAB:
                return new ItemStack(Material.STEP, 1, (byte) 6);
            case NETHER_BRICK_SLAB:
                return new ItemStack(Material.STEP, 1, (byte) 7);
            case RED_SANDSTONE_SLAB:
                return new ItemStack(Material.STONE_SLAB2, 1, (byte) 0);
            //endregion

            //region Dirt
            case GRASS_BLOCK:
                return new ItemStack(Material.GRASS);
            case COARSE_DIRT:
                return new ItemStack(Material.DIRT, 1, (byte) 1);
            case PODZOL:
                return new ItemStack(Material.DIRT, 1, (byte) 2);
            //endregion

            case TERRACOTTA:
                return new ItemStack(Material.HARD_CLAY);
            case LIGHT_GRAY_GLAZED_TERRACOTTA:
                return new ItemStack(Material.SILVER_GLAZED_TERRACOTTA, 1, (byte) 0);
            case LIGHT_GRAY_SHULKER_BOX:
                return new ItemStack(Material.SILVER_SHULKER_BOX, 1, (byte) 0);

            //endregion

            //region Decoration

            //region Leaves
            case OAK_LEAVES:
                return new ItemStack(Material.LEAVES, 1, (byte) 0);
            case SPRUCE_LEAVES:
                return new ItemStack(Material.LEAVES, 1, (byte) 1);
            case BIRCH_LEAVES:
                return new ItemStack(Material.LEAVES, 1, (byte) 2);
            case JUNGLE_LEAVES:
                return new ItemStack(Material.LEAVES, 1, (byte) 3);
            case ACACIA_LEAVES:
                return new ItemStack(Material.LEAVES_2, 1, (byte) 0);
            case DARK_OAK_LEAVES:
                return new ItemStack(Material.LEAVES_2, 1, (byte) 1);
            //endregion

            //region Saplings
            case OAK_SAPLING:
                return new ItemStack(Material.SAPLING, 1, (byte) 0);
            case SPRUCE_SAPLING:
                return new ItemStack(Material.SAPLING, 1, (byte) 1);
            case BIRCH_SAPLING:
                return new ItemStack(Material.SAPLING, 1, (byte) 2);
            case JUNGLE_SAPLING:
                return new ItemStack(Material.SAPLING, 1, (byte) 3);
            case ACACIA_SAPLING:
                return new ItemStack(Material.SAPLING, 1, (byte) 4);
            case DARK_OAK_SAPLING:
                return new ItemStack(Material.SAPLING, 1, (byte) 5);
            //endregion

            //region Infested Block
            case INFESTED_STONE:
                return new ItemStack(Material.MONSTER_EGGS);
            case INFESTED_COBBLESTONE:
                return new ItemStack(Material.MONSTER_EGGS, 1, (byte) 1);
            case INFESTED_STONE_BRICKS:
                return new ItemStack(Material.MONSTER_EGGS, 1, (byte) 2);
            case INFESTED_MOSSY_STONE_BRICKS:
                return new ItemStack(Material.MONSTER_EGGS, 1, (byte) 3);
            case INFESTED_CRACKED_STONE_BRICKS:
                return new ItemStack(Material.MONSTER_EGGS, 1, (byte) 4);
            case INFESTED_CHISELED_STONE_BRICKS:
                return new ItemStack(Material.MONSTER_EGGS, 1, (byte) 5);
            //endregion

            //region Plant
            case SUNFLOWER:
                return new ItemStack(Material.DOUBLE_PLANT, 1, (byte) 0);
            case LILAC:
                return new ItemStack(Material.DOUBLE_PLANT, 1, (byte) 1);
            case TALL_GRASS:
                return new ItemStack(Material.DOUBLE_PLANT, 1, (byte) 2);
            case LARGE_FERN:
                return new ItemStack(Material.DOUBLE_PLANT, 1, (byte) 3);
            case ROSE_BUSH:
                return new ItemStack(Material.DOUBLE_PLANT, 1, (byte) 4);
            case PEONY:
                return new ItemStack(Material.DOUBLE_PLANT, 1, (byte) 5);

            case GRASS:
                return new ItemStack(Material.LONG_GRASS);
            case FERN:
                return new ItemStack(Material.LONG_GRASS, 1, (byte) 2);
            case LILY_PAD:
                return new ItemStack(Material.WATER_LILY);
            //endregion

            //region Flowers
            case DANDELION:
                return new ItemStack(Material.YELLOW_FLOWER);
            case POPPY:
                return new ItemStack(Material.RED_ROSE);
            case BLUE_ORCHID:
                return new ItemStack(Material.RED_ROSE, 1, (byte) 1);
            case ALLIUM:
                return new ItemStack(Material.RED_ROSE, 1, (byte) 2);
            case AZURE_BLUET:
                return new ItemStack(Material.RED_ROSE, 1, (byte) 3);
            case RED_TULIP:
                return new ItemStack(Material.RED_ROSE, 1, (byte) 4);
            case ORANGE_TULIP:
                return new ItemStack(Material.RED_ROSE, 1, (byte) 5);
            case WHITE_TULIP:
                return new ItemStack(Material.RED_ROSE, 1, (byte) 6);
            case PINK_TULIP:
                return new ItemStack(Material.RED_ROSE, 1, (byte) 7);
            case OXEYE_DAISY:
                return new ItemStack(Material.RED_ROSE, 1, (byte) 8);
            //endregion

            //region Head
            case SKELETON_SKULL:
                return new ItemStack(Material.SKULL_ITEM);
            case WITHER_SKELETON_SKULL:
                return new ItemStack(Material.SKULL_ITEM, 1, (byte) 1);
            case ZOMBIE_HEAD:
                return new ItemStack(Material.SKULL_ITEM, 1, (byte) 2);
            case PLAYER_HEAD:
                return new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
            case CREEPER_HEAD:
                return new ItemStack(Material.SKULL_ITEM, 1, (byte) 4);
            case DRAGON_HEAD:
                return new ItemStack(Material.SKULL_ITEM, 1, (byte) 5);
            //endregion

            case OAK_FENCE:
                return new ItemStack(Material.FENCE);
            case OAK_FENCE_GATE:
                return new ItemStack(Material.FENCE_GATE);

            case SHULKER_BOX:
                return new ItemStack(Material.PURPLE_SHULKER_BOX);

            case RED_MUSHROOM_BLOCK:
                return new ItemStack(Material.HUGE_MUSHROOM_1);
            case BROWN_MUSHROOM_BLOCK:
                return new ItemStack(Material.HUGE_MUSHROOM_2);
            //endregion

            //region Redstone

            //region Doors
            case OAK_DOOR:
                return new ItemStack(Material.WOODEN_DOOR);
            case SPRUCE_DOOR:
                return new ItemStack(Material.SPRUCE_DOOR_ITEM);
            case BIRCH_DOOR:
                return new ItemStack(Material.BIRCH_DOOR_ITEM);
            case JUNGLE_DOOR:
                return new ItemStack(Material.JUNGLE_DOOR_ITEM);
            case ACACIA_DOOR:
                return new ItemStack(Material.ACACIA_DOOR_ITEM);
            case DARK_OAK_DOOR:
                return new ItemStack(Material.DARK_OAK_DOOR_ITEM);
            //endregion

            //region Trapdoors
            case OAK_TRAPDOOR:
                return new ItemStack(Material.TRAP_DOOR);
            //endregion

            case COMPARATOR:
                return new ItemStack(Material.REDSTONE_COMPARATOR);

            //endregion

            //region Transportation

            //region Boats
            case OAK_BOAT:
                return new ItemStack(Material.BOAT);
            case SPRUCE_BOAT:
                return new ItemStack(Material.BOAT_SPRUCE);
            case BIRCH_BOAT:
                return new ItemStack(Material.BOAT_BIRCH);
            case JUNGLE_BOAT:
                return new ItemStack(Material.BOAT_JUNGLE);
            case ACACIA_BOAT:
                return new ItemStack(Material.BOAT_ACACIA);
            case DARK_OAK_BOAT:
                return new ItemStack(Material.BOAT_DARK_OAK);
            //endregion

            //region Minecarts
            case TNT_MINECART:
                return new ItemStack(Material.EXPLOSIVE_MINECART);
            case FURNACE_MINECART:
                return new ItemStack(Material.POWERED_MINECART);
            case COMMAND_BLOCK_MINECART:
                return new ItemStack(Material.COMMAND_MINECART);
            case CHEST_MINECART:
                return new ItemStack(Material.STORAGE_MINECART);
            //endregion

            //endregion

            //region Miscellaneous

            //region Dye
            case BLACK_DYE:
            case INK_SAC:
                return new ItemStack(Material.INK_SACK, 1, (byte) 0);
            case BLUE_DYE:
            case LAPIS_LAZULI:
                return new ItemStack(Material.INK_SACK, 1, (byte) 4);
            case BROWN_DYE:
            case COCOA_BEANS:
                return new ItemStack(Material.INK_SACK, 1, (byte) 3);
            case CYAN_DYE:
                return new ItemStack(Material.INK_SACK, 1, (byte) 6);
            case GRAY_DYE:
                return new ItemStack(Material.INK_SACK, 1, (byte) 8);
            case CACTUS_GREEN:
            case GREEN_DYE:
                return new ItemStack(Material.INK_SACK, 1, (byte) 2);
            case LIGHT_BLUE_DYE:
                return new ItemStack(Material.INK_SACK, 1, (byte) 12);
            case LIGHT_GRAY_DYE:
                return new ItemStack(Material.INK_SACK, 1, (byte) 7);
            case LIME_DYE:
                return new ItemStack(Material.INK_SACK, 1, (byte) 10);
            case MAGENTA_DYE:
                return new ItemStack(Material.INK_SACK, 1, (byte) 13);
            case ORANGE_DYE:
                return new ItemStack(Material.INK_SACK, 1, (byte) 14);
            case PINK_DYE:
                return new ItemStack(Material.INK_SACK, 1, (byte) 9);
            case PURPLE_DYE:
                return new ItemStack(Material.INK_SACK, 1, (byte) 5);
            case ROSE_RED:
            case RED_DYE:
                return new ItemStack(Material.INK_SACK, 1, (byte) 1);
            case WHITE_DYE:
            case BONE_MEAL:
                return new ItemStack(Material.INK_SACK, 1, (byte) 15);
            case DANDELION_YELLOW:
            case YELLOW_DYE:
                return new ItemStack(Material.INK_SACK, 1, (byte) 11);
            //endregion

            //region Record
            case MUSIC_DISC_13:
                return new ItemStack(Material.GOLD_RECORD);
            case MUSIC_DISC_CAT:
                return new ItemStack(Material.GREEN_RECORD);
            case MUSIC_DISC_BLOCKS:
                return new ItemStack(Material.RECORD_3);
            case MUSIC_DISC_CHIRP:
                return new ItemStack(Material.RECORD_4);
            case MUSIC_DISC_FAR:
                return new ItemStack(Material.RECORD_5);
            case MUSIC_DISC_MALL:
                return new ItemStack(Material.RECORD_6);
            case MUSIC_DISC_MELLOHI:
                return new ItemStack(Material.RECORD_7);
            case MUSIC_DISC_STAL:
                return new ItemStack(Material.RECORD_8);
            case MUSIC_DISC_STRAD:
                return new ItemStack(Material.RECORD_9);
            case MUSIC_DISC_WARD:
                return new ItemStack(Material.RECORD_10);
            case MUSIC_DISC_11:
                return new ItemStack(Material.RECORD_11);
            case MUSIC_DISC_WAIT:
                return new ItemStack(Material.RECORD_12);
            //endregion

            //endregion

            //region Foodstuffs

            //region Fish Related.
            case COD:
                return new ItemStack(Material.RAW_FISH, 1, (byte) 0);
            case SALMON:
                return new ItemStack(Material.RAW_FISH, 1, (byte) 1);
            case TROPICAL_FISH:
                return new ItemStack(Material.RAW_FISH, 1, (byte) 2);
            case PUFFERFISH:
                return new ItemStack(Material.RAW_FISH, 1, (byte) 3);

            case COOKED_COD:
                return new ItemStack(Material.COOKED_FISH, 1, (byte) 0);
            case COOKED_SALMON:
                return new ItemStack(Material.COOKED_FISH, 1, (byte) 1);
            //endregion

            //endregion

            //region Tools

            case WOODEN_AXE:
                return new ItemStack(Material.WOOD_AXE);
            case WOODEN_HOE:
                return new ItemStack(Material.WOOD_HOE);
            case WOODEN_PICKAXE:
                return new ItemStack(Material.WOOD_PICKAXE);
            case WOODEN_SHOVEL:
                return new ItemStack(Material.WOOD_SPADE);

            //endregion

            //region Combat

            case WOODEN_SWORD:
                return new ItemStack(Material.WOOD_SWORD);

            //endregion

            //region Brewing

            //endregion

            //region Unsorted //TODO: Sort
            case MAP:
                return new ItemStack(Material.EMPTY_MAP);

            case FILLED_MAP:
                return new ItemStack(Material.MAP);

            case END_STONE_BRICKS:
                return new ItemStack(Material.END_BRICKS);

            case END_STONE:
                return new ItemStack(Material.ENDER_STONE);

            case BRICK:
                return new ItemStack(Material.CLAY_BRICK);

            case BRICKS:
                return new ItemStack(Material.BRICK);

            case MAGMA_BLOCK:
                return new ItemStack(Material.MAGMA);

            case COBWEB:
                return new ItemStack(Material.WEB);

            case CRAFTING_TABLE:
                return new ItemStack(Material.WORKBENCH);

            case SPAWNER:
                return new ItemStack(Material.MOB_SPAWNER);

            case PRISMARINE_BRICKS:
                return new ItemStack(Material.PRISMARINE, 1, (byte) 1);

            case DARK_PRISMARINE:
                return new ItemStack(Material.PRISMARINE, 1, (byte) 2);

            case CHIPPED_ANVIL:
                return new ItemStack(Material.ANVIL, 1, (byte) 1);

            case DAMAGED_ANVIL:
                return new ItemStack(Material.ANVIL, 1, (byte) 2);

            case CHISELED_QUARTZ_BLOCK:
                return new ItemStack(Material.QUARTZ_BLOCK, 1, (byte) 1);

            case QUARTZ_PILLAR:
                return new ItemStack(Material.QUARTZ_BLOCK, 1, (byte) 2);

            case PISTON:
                return new ItemStack(Material.PISTON_BASE);
            case STICKY_PISTON:
                return new ItemStack(Material.PISTON_STICKY_BASE);

            case END_PORTAL:
                return new ItemStack(Material.ENDER_PORTAL);

            case END_PORTAL_FRAME:
                return new ItemStack(Material.ENDER_PORTAL_FRAME);

            case ENDER_EYE:
                return new ItemStack(Material.EYE_OF_ENDER);

            case EXPERIENCE_BOTTLE:
                return new ItemStack(Material.EXP_BOTTLE);

            case FIRE_CHARGE:
                return new ItemStack(Material.FIREBALL);

            case FIREWORK_ROCKET:
                return new ItemStack(Material.FIREWORK);

            case FIREWORK_STAR:
                return new ItemStack(Material.FIREWORK_CHARGE);

            case CAULDRON:
                return new ItemStack(Material.CAULDRON_ITEM);

            case POTATO:
                return new ItemStack(Material.POTATO_ITEM);

            case POTATOES:
                return new ItemStack(Material.POTATO);

            case BREWING_STAND:
                return new ItemStack(Material.BREWING_STAND_ITEM);

            case FLOWER_POT:
                return new ItemStack(Material.FLOWER_POT_ITEM);

            case CARROT:
                return new ItemStack(Material.CARROT_ITEM);

            case CARROTS:
                return new ItemStack(Material.CARROT);

            case BEETROOT:
                return new ItemStack(Material.BEETROOT);

            case BEETROOTS:
                return new ItemStack(Material.BEETROOT_BLOCK);

            case POPPED_CHORUS_FRUIT:
                return new ItemStack(Material.CHORUS_FRUIT_POPPED);

            case ENCHANTED_GOLDEN_APPLE:
                return new ItemStack(Material.GOLDEN_APPLE, 1, (byte) 1);

            case COOKED_PORKCHOP:
                return new ItemStack(Material.GRILLED_PORK);

            case PORKCHOP:
                return new ItemStack(Material.PORK);

            case BEEF:
                return new ItemStack(Material.RAW_BEEF);

            case CHICKEN:
                return new ItemStack(Material.RAW_CHICKEN);

            case CARVED_PUMPKIN:
                return new ItemStack(Material.PUMPKIN);

            case COMMAND_BLOCK:
                return new ItemStack(Material.COMMAND);

            case CHAIN_COMMAND_BLOCK:
                return new ItemStack(Material.COMMAND_CHAIN);

            case REPEATING_COMMAND_BLOCK:
                return new ItemStack(Material.COMMAND_REPEATING);

            case REPEATER:
                return new ItemStack(Material.DIODE);

            case LIGHT_WEIGHTED_PRESSURE_PLATE:
                return new ItemStack(Material.GOLD_PLATE);

            case HEAVY_WEIGHTED_PRESSURE_PLATE:
                return new ItemStack(Material.IRON_PLATE);

            case LEAD:
                return new ItemStack(Material.LEASH);

            case MELON:
                return new ItemStack(Material.MELON_BLOCK);

            case MELON_SLICE:
                return new ItemStack(Material.MELON);

            case GLISTERING_MELON_SLICE:
                return new ItemStack(Material.SPECKLED_MELON);

            case MYCELIUM:
                return new ItemStack(Material.MYCEL);

            case GUNPOWDER:
                return new ItemStack(Material.SULPHUR);

            case NETHER_WART:
                return new ItemStack(Material.NETHER_WARTS);

            case NETHER_PORTAL:
                return new ItemStack(Material.PORTAL);

            case RAIL:
                return new ItemStack(Material.RAILS);

            case REDSTONE_TORCH:
                return new ItemStack(Material.REDSTONE_TORCH_ON);

            case FARMLAND:
                return new ItemStack(Material.SOIL);

            case REDSTONE_LAMP:
                return new ItemStack(Material.REDSTONE_LAMP_OFF);

            case WHEAT_SEEDS:
                return new ItemStack(Material.SEEDS);

            case IRON_BARS:
                return new ItemStack(Material.IRON_FENCE);

            case WRITABLE_BOOK:
                return new ItemStack(Material.BOOK_AND_QUILL);

            case ATTACHED_MELON_STEM:
                return new ItemStack(Material.MELON_STEM);

            case ATTACHED_PUMPKIN_STEM:
                return new ItemStack(Material.PUMPKIN_STEM);

            case NETHER_BRICK_FENCE:
                return new ItemStack(Material.NETHER_FENCE);

            case NETHER_BRICKS:
                return new ItemStack(Material.NETHER_BRICK);

            case NETHER_QUARTZ_ORE:
                return new ItemStack(Material.QUARTZ_ORE);

            case CARROT_ON_A_STICK:
                return new ItemStack(Material.CARROT_STICK);

            case CHARCOAL:
                return new ItemStack(Material.COAL, 1, (byte) 1);

            case CLOCK:
                return new ItemStack(Material.WATCH);

            case DRAGON_BREATH:
                return new ItemStack(Material.DRAGONS_BREATH);

            case ENCHANTING_TABLE:
                return new ItemStack(Material.ENCHANTMENT_TABLE);

            case GLASS_PANE:
                return new ItemStack(Material.THIN_GLASS);

            case TOTEM_OF_UNDYING:
                return new ItemStack(Material.TOTEM);

            case WET_SPONGE:
                return new ItemStack(Material.SPONGE, 1, (byte) 1);

            case SNOWBALL:
                return new ItemStack(Material.SNOW_BALL);

            //endregion
        }

        // Try translating material with Spigot Material.
        try {
            return new ItemStack(Material.valueOf(material.name()));
        } catch (IllegalArgumentException ignore) { }

        // Replace common material name differences.
        String formattedName = null;
        String name = material.name();
        byte data = 0;

        // Handle colored materials.
        if (getColorData(material) > -1) {
            if (name.endsWith("_TERRACOTTA") && getColorData(material) > -1) {
                data = getColorData(material);
                formattedName = "STAINED_CLAY";
            } else if (!name.endsWith("_DYE")) {
                data = getColorData(material);
                String colourPrefix = getColor(data) + "_";
                formattedName = name.replace(colourPrefix, "");
            }
        }

        if (name.endsWith("_SHOVEL"))
            formattedName = name.replace("SHOVEL", "SPADE");

        if (name.endsWith("_HORSE_ARMOR"))
            formattedName = name.replace("HORSE_ARMOR", "BARDING");

        if (name.startsWith("GOLDEN_") && !(name.endsWith("_APPLE") || (name.endsWith("_CARROT"))))
            formattedName = name.replace("GOLDEN", "GOLD");

        if (name.endsWith("_PRESSURE_PLATE"))
            formattedName = name.replace("PRESSURE_PLATE", "PLATE");

        // Try translating replaced material with Spigot Material.
        if (formattedName != null) {
            try {
                return new ItemStack(Material.valueOf(formattedName), 1, data);
            } catch (IllegalArgumentException ignore) { }
        }

        // Handle old type differences.
        if (name.endsWith("_SPAWN_EGG")) {
            String eggTypeName = name.replace("_SPAWN_EGG", "");
            ItemStack spawnEgg = new ItemStack(Material.MONSTER_EGG);

            try {
                EntityType type = EntityType.valueOf(eggTypeName);
                SpawnEggMeta meta = (SpawnEggMeta) spawnEgg.getItemMeta();

                meta.setSpawnedType(type);
                spawnEgg.setItemMeta(meta);
            } catch (IllegalArgumentException ignore) { }

            return spawnEgg;
        } else if (name.endsWith("_BUTTON") && !name.startsWith("STONE_"))
            return new ItemStack(Material.WOOD_BUTTON);

        else if (name.endsWith("_SIGN"))
            return new ItemStack(Material.SIGN);

        else if (name.endsWith("_TRAPDOOR") && !material.name().startsWith("IRON_"))
            return new ItemStack(Material.TRAP_DOOR);

        else if (name.endsWith("_SLAB") && !material.name().startsWith("IRON_"))
            return new ItemStack(Material.TRAP_DOOR);

        else if (name.endsWith("_STAIRS"))
            return new ItemStack(Material.COBBLESTONE_STAIRS);

        else if (name.endsWith("_WALL"))
            return new ItemStack(Material.COBBLE_WALL);

        throw new IllegalArgumentException("Could not find material " + material.name() + " for your Minecraft version!");
    }

    @Override
    public SafeMaterial fromItemStack(ItemStack item) {
        if (item.hasItemMeta() && !(item.getItemMeta() instanceof SpawnEggMeta))
            item = new ItemStack(item.getType(), 1, (byte) item.getDurability());;

        for (SafeMaterial material : SafeMaterial.values()) {
            try {
                if (item.isSimilar(material.toItemStack()))
                    return material;
            } catch (UnsupportedOperationException ignore) { }
        }

        throw new IllegalArgumentException("Could not find material.");
    }

    private byte getColorData(SafeMaterial material) {
        String name = material.name();

        if (name.startsWith("WHITE_"))
            return 0;
        else if (name.startsWith("ORANGE_"))
            return 1;
        else if (name.startsWith("MAGENTA_"))
            return 2;
        else if (name.startsWith("LIGHT_BLUE_"))
            return 3;
        else if (name.startsWith("YELLOW_"))
            return 4;
        else if (name.startsWith("LIME_"))
            return 5;
        else if (name.startsWith("PINK_"))
            return 6;
        else if (name.startsWith("GRAY_"))
            return 7;
        else if (name.startsWith("LIGHT_GRAY_"))
            return 8;
        else if (name.startsWith("CYAN_"))
            return 9;
        else if (name.startsWith("PURPLE_"))
            return 10;
        else if (name.startsWith("BLUE_"))
            return 11;
        else if (name.startsWith("BROWN_"))
            return 12;
        else if (name.startsWith("GREEN_"))
            return 13;
        else if (name.startsWith("RED_"))
            return 14;
        else if (name.startsWith("BLACK_"))
            return 15;
        else
            return -1;
    }

    private String getColor(byte data) {
        switch (data) {
            case 0:
                return "WHITE";
            case 1:
                return "ORANGE";
            case 2:
                return "MAGENTA";
            case 3:
                return "LIGHT_BLUE";
            case 4:
                return "YELLOW";
            case 5:
                return "LIME";
            case 6:
                return "PINK";
            case 7:
                return "GRAY";
            case 8:
                return "LIGHT_GRAY";
            case 9:
                return "CYAN";
            case 10:
                return "PURPLE";
            case 11:
                return "BLUE";
            case 12:
                return "BROWN";
            case 13:
                return "GREEN";
            case 14:
                return "RED";
            case 15:
                return "BLACK";
            default:
                return "";
        }
    }
}
