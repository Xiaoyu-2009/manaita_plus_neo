package net.manaita_plus_neo.client;

import net.manaita_plus_neo.ManaitaPlusNeo;
import net.manaita_plus_neo.block.entity.ModBlockEntities;
import net.manaita_plus_neo.client.render.block.RenderCraftingManaitaBlockEntity;
import net.manaita_plus_neo.client.render.block.RenderFurnaceManaitaBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ManaitaPlusNeo.MOD_ID)
public class ClientEvents {
    
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // 砧板工作台
        event.registerBlockEntityRenderer(ModBlockEntities.CRAFTING_BLOCK_ENTITY.get(), RenderCraftingManaitaBlockEntity::new);
        // 砧板熔炉
        event.registerBlockEntityRenderer(ModBlockEntities.FURNACE_BLOCK_ENTITY.get(), RenderFurnaceManaitaBlockEntity::new);
    }
}