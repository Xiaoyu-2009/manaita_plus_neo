package net.manaita_plus_neo;

import net.manaita_plus_neo.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ManaitaPlusNeo.MOD_ID);

    // 更好的砧板
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MANAITA_PLUS_TAB = CREATIVE_TABS.register("manaita_plus_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.ManaitaPlusTab"))
            .icon(() -> new ItemStack(ModItems.MANAITA_AXE.get()))
            .displayItems((parameters, output) -> {
                // 砧板斧
                output.accept(ModItems.MANAITA_AXE.get());
                // 砧板稿
                output.accept(ModItems.MANAITA_PICKAXE.get());
                // 砧板铲
                output.accept(ModItems.MANAITA_SHOVEL.get());
                // 砧板锄
                output.accept(ModItems.MANAITA_HOE.get());
                // 砧板剪刀
                output.accept(ModItems.MANAITA_SHEARS.get());
            })
            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
    }
}