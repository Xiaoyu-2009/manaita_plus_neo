package net.manaita_plus_neo.item;

import net.manaita_plus_neo.ManaitaPlusNeo;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ShearsItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ManaitaPlusNeo.MOD_ID);

    // 砧板斧
    public static final DeferredItem<Item> MANAITA_AXE = ITEMS.registerItem("manaita_axe", properties -> 
        new ManaitaAxe(
            Tiers.NETHERITE, 
            properties/*.attributes(
                AxeItem.createAttributes(
                    Tiers.NETHERITE, 
                    Float.MAX_VALUE, 
                    Float.MAX_VALUE
                ))*/
        )
    );

    // 砧板镐
    public static final DeferredItem<Item> MANAITA_PICKAXE = ITEMS.registerItem("manaita_pickaxe", properties -> 
        new ManaitaPickaxe(
            Tiers.NETHERITE, 
            properties
        )
    );

    // 砧板铲
    public static final DeferredItem<Item> MANAITA_SHOVEL = ITEMS.registerItem("manaita_shovel", properties -> 
        new ManaitaShovel(
            Tiers.NETHERITE, 
            properties
        )
    );

    // 砧板锄
    public static final DeferredItem<Item> MANAITA_HOE = ITEMS.registerItem("manaita_hoe", properties -> 
        new ManaitaHoe(
            Tiers.NETHERITE, 
            properties
        )
    );

    // 砧板剪刀
    public static final DeferredItem<Item> MANAITA_SHEARS = ITEMS.registerItem("manaita_shears", properties -> 
        new ManaitaShears(
            properties
        )
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}