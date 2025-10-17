package net.manaita_plus_neo.item.armor;

import net.manaita_plus_neo.item.data.IManaitaPlusKey;
import net.manaita_plus_neo.util.ManaitaToolUtils;
import net.manaita_plus_neo.util.ManaitaArmorUtils;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ManaitaHelmet extends ArmorItem implements IManaitaPlusKey {
    public ManaitaHelmet(Properties properties) {
        super(ManaitaArmorMaterial.MANAITA, Type.HELMET, properties);
    }
    
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.literal(ManaitaToolUtils.ManaitaText.manaita_infinity.formatting(I18n.get("info.armor"))));
        boolean nightVision = ManaitaArmorUtils.getNightVision(stack);
        String statusText = nightVision ? I18n.get("info.on") : I18n.get("info.off");
        tooltip.add(Component.literal(ManaitaToolUtils.ManaitaText.manaita_mode.formatting(
            I18n.get("mode.nightvision") + ": " + statusText)
        ));
    }
    
    @Override
    public void onManaitaKeyPress(ItemStack itemStack, Player player) {
        if (!player.isShiftKeyDown()) {
            boolean nightVision = !ManaitaArmorUtils.getNightVision(itemStack);
            ManaitaArmorUtils.setNightVision(itemStack, nightVision);
        }
    }

    @Override
    public void onManaitaKeyPressOnClient(ItemStack itemStack, Player player) {
        if (!player.isShiftKeyDown()) {
            ManaitaArmorUtils.handleNightVisionKeyPressOnClient(itemStack, player);
        }
    }

/*     @Override
    public Component getName(ItemStack stack) {
        return Component.literal(ManaitaToolUtils.ManaitaText.manaita_infinity.formatting(I18n.get("item.manaita_plus_neo.manaita_helmet")));
    } */
}