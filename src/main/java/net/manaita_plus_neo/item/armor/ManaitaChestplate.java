package net.manaita_plus_neo.item.armor;

import net.manaita_plus_neo.util.ManaitaToolUtils;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ManaitaChestplate extends ArmorItem {
    public ManaitaChestplate(Properties properties) {
        super(ManaitaArmorMaterial.MANAITA, Type.CHESTPLATE, properties);
    }
    
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.literal(ManaitaToolUtils.ManaitaText.manaita_infinity.formatting(I18n.get("info.armor"))));
    }

/*     @Override
    public Component getName(ItemStack stack) {
        return Component.literal(ManaitaToolUtils.ManaitaText.manaita_infinity.formatting(I18n.get("item.manaita_plus_neo.manaita_chestplate")));
    } */
}