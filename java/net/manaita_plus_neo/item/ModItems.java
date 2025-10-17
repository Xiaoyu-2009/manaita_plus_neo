package net.manaita_plus_neo.item;

import net.manaita_plus_neo.ManaitaPlusNeo;
import net.manaita_plus_neo.item.tools.*;
import net.manaita_plus_neo.item.weapon.ManaitaBow;
import net.manaita_plus_neo.item.weapon.ManaitaSword;
import net.manaita_plus_neo.item.armor.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ManaitaPlusNeo.MOD_ID);

    // 砧板斧
    public static final DeferredItem<Item> MANAITA_AXE = ITEMS.registerItem("manaita_axe", properties -> 
        new ManaitaAxe(
            Tiers.NETHERITE, 
            properties.attributes(
                AxeItem.createAttributes(
                    Tiers.NETHERITE, 
                    10000.0F, 
                    10000.0F
                ))
        )
    );

    // 砧板镐
    public static final DeferredItem<Item> MANAITA_PICKAXE = ITEMS.registerItem("manaita_pickaxe", properties -> 
        new ManaitaPickaxe(
            Tiers.NETHERITE, 
            properties.attributes(
                PickaxeItem.createAttributes(
                    Tiers.NETHERITE, 
                    10000.0F, 
                    10000.0F
                ))
        )
    );

    // 砧板铲
    public static final DeferredItem<Item> MANAITA_SHOVEL = ITEMS.registerItem("manaita_shovel", properties -> 
        new ManaitaShovel(
            Tiers.NETHERITE, 
            properties.attributes(
                ShovelItem.createAttributes(
                    Tiers.NETHERITE, 
                    10000.0F, 
                    10000.0F
                ))
        )
    );

    // 砧板锄
    public static final DeferredItem<Item> MANAITA_HOE = ITEMS.registerItem("manaita_hoe", properties -> 
        new ManaitaHoe(
            Tiers.NETHERITE, 
            properties.attributes(
                HoeItem.createAttributes(
                    Tiers.NETHERITE, 
                    10000.0F, 
                    10000.0F
                ))
        )
    );

    // 砧板剪刀
    public static final DeferredItem<Item> MANAITA_SHEARS = ITEMS.registerItem("manaita_shears", properties -> 
        new ManaitaShears(
            properties
        )
    );

    // 砧板弓
    public static final DeferredItem<Item> MANAITA_BOW = ITEMS.registerItem("manaita_bow", properties -> 
        new ManaitaBow(
            properties
        )
    );

    // 砧板剑
    public static final DeferredItem<Item> MANAITA_SWORD = ITEMS.registerItem("manaita_sword", properties -> 
        new ManaitaSword(
            Tiers.NETHERITE, 
            properties.attributes(
                SwordItem.createAttributes(
                    Tiers.NETHERITE, 
                    10000.0F, 
                    10000.0F
                ))
        )
    );
    
    // 砧板头盔
    public static final DeferredItem<Item> MANAITA_HELMET = ITEMS.registerItem("manaita_helmet", properties -> 
        new ManaitaHelmet(
            properties.stacksTo(1)
        )
    );
    
    // 砧板胸甲
    public static final DeferredItem<Item> MANAITA_CHESTPLATE = ITEMS.registerItem("manaita_chestplate", properties -> 
        new ManaitaChestplate(
            properties.stacksTo(1)
        )
    );
    
    // 砧板护腿
    public static final DeferredItem<Item> MANAITA_LEGGINGS = ITEMS.registerItem("manaita_leggings", properties -> 
        new ManaitaLeggings(
            properties.stacksTo(1)
        )
    );
    
    // 砧板靴子
    public static final DeferredItem<Item> MANAITA_BOOTS = ITEMS.registerItem("manaita_boots", properties -> 
        new ManaitaBoots(
            properties.stacksTo(1)
        )
    );

    // 砧板多功能工具
    public static final DeferredItem<Item> MANAITA_PAXEL = ITEMS.registerItem("manaita_paxel", properties -> 
        new ManaitaPaxel(
            Tiers.NETHERITE, 
            properties.attributes(
                DiggerItem.createAttributes(
                    Tiers.NETHERITE, 
                    10000.0F, 
                    10000.0F
                ))
        )
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}