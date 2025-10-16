package net.manaita_plus_neo.client.event;

import net.manaita_plus_neo.ManaitaKeyBindings;
import net.manaita_plus_neo.ManaitaPlusNeo;
import net.manaita_plus_neo.item.data.IManaitaPlusKey;
import net.manaita_plus_neo.network.client.MessageKey;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = ManaitaPlusNeo.MOD_ID)
public class ManaitaClientEvents {
    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (mc.player == null) return;
        
        // 翻倍
        if (ManaitaKeyBindings.manaitaKey.consumeClick()) {
            ItemStack mainHandItem = mc.player.getMainHandItem();
            if (!mainHandItem.isEmpty() && mainHandItem.getItem() instanceof IManaitaPlusKey iManaitaPlusKey) {
                iManaitaPlusKey.onManaitaKeyPressOnClient(mainHandItem, mc.player);
                PacketDistributor.sendToServer(new MessageKey((byte)0));
            }
        }
        
        // 盔甲
        if (ManaitaKeyBindings.manaitaArmorKey.consumeClick()) {
            for (ItemStack itemStack : mc.player.getInventory().armor) {
                if (!itemStack.isEmpty() && itemStack.getItem() instanceof IManaitaPlusKey key) {
                    key.onManaitaKeyPressOnClient(itemStack, mc.player);
                }
            }
            PacketDistributor.sendToServer(new MessageKey((byte)1));
        }
    }
}