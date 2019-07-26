package nl.tabuu.tabuucore.nms.v1_12_R1;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.material.SafeMaterial;
import nl.tabuu.tabuucore.nms.wrapper.ISafeMaterialExtension;
import nl.tabuu.tabuucore.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;

public class SafeMaterialExtension implements ISafeMaterialExtension {

    @Override
    public ItemStack toItemStack(SafeMaterial material) {

        if(!material.name().endsWith("_DYE") && getColourData(material) > -1){
            byte colourData = getColourData(material);
            String colourPrefix = getColour(colourData) + "_";
            String materialName = material.name().replace(colourPrefix, "");

            try{
                return new ItemStack(Material.valueOf(materialName), 1, colourData);
            }
            catch (IllegalArgumentException ignore){}
        }
        else if(material.name().endsWith("_SHOVEL")){
            String materialName = material.name().replace("SHOVEL", "SPADE");
            try{
                return new ItemStack(Material.valueOf(materialName));
            }
            catch (IllegalArgumentException ignore){}
        }
        else if(material.name().endsWith("_HORSE_ARMOR")){
            String materialName = material.name().replace("HORSE_ARMOR", "BARDING");
            try{
                return new ItemStack(Material.valueOf(materialName));
            }
            catch (IllegalArgumentException ignore){}
        }
        else if(material.name().startsWith("GOLDEN_") &&
               !(material.name().endsWith("_APPLE") || (material.name().endsWith("_CARROT")))){
            String materialName = material.name().replace("GOLDEN", "GOLD");
            try{
                return new ItemStack(Material.valueOf(materialName));
            }
            catch (IllegalArgumentException ignore){}
        }
        else if(material.name().endsWith("_SPAWN_EGG")){
            String eggTypeName = material.name().replace("_SPAWN_EGG", "");
            try{
                EntityType eggType = EntityType.valueOf(eggTypeName);
                ItemStack spawnEgg = new ItemStack(Material.MONSTER_EGG);
                SpawnEggMeta meta = (SpawnEggMeta) spawnEgg.getItemMeta();

                meta.setSpawnedType(eggType);
                spawnEgg.setItemMeta(meta);

                return spawnEgg;
            }
            catch (IllegalArgumentException ignore){}
        }

        switch (material){
            //region Wood Type Related.
            //region Doors
            case OAK_DOOR:
                return new ItemStack(Material.WOOD_DOOR);
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

            //region Stairs
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
            //endregion

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

            //region Logs
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

            case OAK_FENCE:
                return new ItemStack(Material.FENCE);
            case OAK_FENCE_GATE:
                return new ItemStack(Material.FENCE_GATE);
            //endregion

            //region Colour Related.
            case TERRACOTTA:
                return new ItemStack(Material.HARD_CLAY);
            case LIGHT_GRAY_GLAZED_TERRACOTTA:
                return new ItemStack(Material.SILVER_GLAZED_TERRACOTTA, 1, (byte) 0);
            case LIGHT_GRAY_SHULKER_BOX:
                return new ItemStack(Material.SILVER_SHULKER_BOX, 1, (byte) 0);

            //region Dyes
            case INK_SAC:
                return new ItemStack(Material.INK_SACK, 1, (byte) 0);
            case LAPIS_LAZULI:
                return new ItemStack(Material.INK_SACK, 1, (byte) 4);
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
            case BONE_MEAL:
                return new ItemStack(Material.INK_SACK, 1, (byte) 15);
            case DANDELION_YELLOW:
            case YELLOW_DYE:
                return new ItemStack(Material.INK_SACK, 1, (byte) 11);
            //endregion
            //endregion.

            //region Plant Related.
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
            //endregion

            //region Record Related.
            case MUSIC_DISC_13:
                return new ItemStack(Material.GOLD_RECORD);
            case MUSIC_DISC_11:
                return new ItemStack(Material.RECORD_11);
            case MUSIC_DISC_BLOCKS:
                return new ItemStack(Material.RECORD_3);
            case MUSIC_DISC_CAT:
                return new ItemStack(Material.GREEN_RECORD);
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
            case MUSIC_DISC_WAIT:
                return new ItemStack(Material.RECORD_12);
            case MUSIC_DISC_WARD:
                return new ItemStack(Material.RECORD_10);
            //endregion

            //region Infested Block Related.
            case INFESTED_CHISELED_STONE_BRICKS:
                return new ItemStack(Material.MONSTER_EGGS, 1, (byte) 5);
            case INFESTED_COBBLESTONE:
                return new ItemStack(Material.MONSTER_EGGS, 1, (byte) 1);
            case INFESTED_STONE:
                return new ItemStack(Material.MONSTER_EGGS, 1, (byte) 0);
            case INFESTED_CRACKED_STONE_BRICKS:
                return new ItemStack(Material.MONSTER_EGGS, 1, (byte) 4);
            case INFESTED_MOSSY_STONE_BRICKS:
                return new ItemStack(Material.MONSTER_EGGS, 1, (byte) 3);
            case INFESTED_STONE_BRICKS:
                return new ItemStack(Material.MONSTER_EGGS, 1, (byte) 2);
            //endregion

            //region Head Related
            case CREEPER_HEAD:
                return new ItemStack(Material.SKULL_ITEM, 1, (byte) 4);
            case PLAYER_HEAD:
                return new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
            case ZOMBIE_HEAD:
                return new ItemStack(Material.SKULL_ITEM, 1, (byte) 2);
            case SKELETON_SKULL:
                return new ItemStack(Material.SKULL_ITEM, 1, (byte) 0);
            case WITHER_SKELETON_SKULL:
                return new ItemStack(Material.SKULL_ITEM, 1, (byte) 1);
            case DRAGON_HEAD:
                return new ItemStack(Material.SKULL_ITEM, 1, (byte) 5);
            //endregion

            //region Stone Brick Related
            case STONE_BRICKS:
                return new ItemStack(Material.SMOOTH_BRICK);
            case STONE_BRICK_STAIRS:
                return new ItemStack(Material.SMOOTH_STAIRS);
            //endregion

            //region Slab Related
            //region Wooden slabs
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

            //region Stone slabs
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
            //endregion

            //region Stone slabs2
            case RED_SANDSTONE_SLAB:
                return new ItemStack(Material.STONE_SLAB2, 1, (byte) 0);
            //endregion
            //endregion

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

            case END_STONE_BRICKS:
                return new ItemStack(Material.END_BRICKS);
            case END_STONE:
                return new ItemStack(Material.ENDER_STONE);
            case END_PORTAL:
                return new ItemStack(Material.ENDER_PORTAL);
            case END_PORTAL_FRAME:
                return new ItemStack(Material.ENDER_PORTAL_FRAME);
            case ENDER_EYE:
                return new ItemStack(Material.EYE_OF_ENDER);

            case EXPERIENCE_BOTTLE:
                return new ItemStack(Material.EXP_BOTTLE);

            case TNT_MINECART:
                return new ItemStack(Material.EXPLOSIVE_MINECART);

            case FIRE_CHARGE:
                return new ItemStack(Material.FIREBALL);

            case FIREWORK_ROCKET:
                return new ItemStack(Material.FIREWORK);
            case FIREWORK_STAR:
                return new ItemStack(Material.FIREWORK_CHARGE);

            case FURNACE_MINECART:
                return new ItemStack(Material.BURNING_FURNACE);

            case MAP:
                return new ItemStack(Material.EMPTY_MAP);
            case FILLED_MAP:
                return new ItemStack(Material.MAP);

            case RED_MUSHROOM_BLOCK:
                return new ItemStack(Material.HUGE_MUSHROOM_1);
            case BROWN_MUSHROOM_BLOCK:
                return new ItemStack(Material.HUGE_MUSHROOM_2);

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
            case BRICK:
                return new ItemStack(Material.CLAY_BRICK);
            case BRICKS:
                return new ItemStack(Material.BRICK);

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
            case COMMAND_BLOCK_MINECART:
                return new ItemStack(Material.COMMAND_MINECART);
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
            case MAGMA_BLOCK:
                return new ItemStack(Material.MAGMA);
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
            case POPPY:
                return new ItemStack(Material.RED_ROSE);
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
            default:
                try{
                    return new ItemStack(Material.valueOf(material.name()));
                }
                catch (IllegalArgumentException exception){
                    TabuuCore.getInstance().getLogger().severe("Could not find material " + material.name() + " for your Minecraft version!");
                    return new ItemBuilder(new ItemStack(Material.COOKED_FISH, 1, (byte) 5)).setDisplayName("Missing No.").build();
                }
        }
    }

    private byte getColourData(SafeMaterial material){
        String name = material.name();

        if(name.startsWith("WHITE_"))
            return 0;
        else if(name.startsWith("ORANGE_"))
            return 1;
        else if(name.startsWith("MAGENTA_"))
            return 2;
        else if(name.startsWith("LIGHT_BLUE_"))
            return 3;
        else if(name.startsWith("YELLOW_"))
            return 4;
        else if(name.startsWith("LIME_"))
            return 5;
        else if(name.startsWith("PINK_"))
            return 6;
        else if(name.startsWith("GRAY_"))
            return 7;
        else if(name.startsWith("LIGHT_GRAY_"))
            return 8;
        else if(name.startsWith("CYAN_"))
            return 9;
        else if(name.startsWith("PURPLE_"))
            return 10;
        else if(name.startsWith("BLUE_"))
            return 11;
        else if(name.startsWith("BROWN_"))
            return 12;
        else if(name.startsWith("GREEN_"))
            return 13;
        else if(name.startsWith("RED_"))
            return 14;
        else if(name.startsWith("BLACK_"))
            return 15;
        else
            return -1;
    }

    private String getColour(byte data){
        switch (data){
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
