package net.manaita_plus_neo;

import net.manaita_plus_neo.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(ManaitaPlusNeo.MOD_ID)
public class ManaitaPlusNeo
{
    public static final String MOD_ID = "manaita_plus_neo";

    public ManaitaPlusNeo(IEventBus modEventBus) {
        ModItems.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
    }
}