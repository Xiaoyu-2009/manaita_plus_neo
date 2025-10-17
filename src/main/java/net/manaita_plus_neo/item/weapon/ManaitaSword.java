package net.manaita_plus_neo.item.weapon;

import net.manaita_plus_neo.item.data.IManaitaPlusKey;
import net.manaita_plus_neo.util.ManaitaToolUtils;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;

import java.util.List;

public class ManaitaSword extends SwordItem implements IManaitaPlusKey {
    
    public ManaitaSword(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public int getDamage(ItemStack stack) {
        return 0;
    }
    
    @Override
    @SuppressWarnings("removal")
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player player) {
            int sweep = getSweep(stack);
            ManaitaToolUtils.performSweepAttack(player, sweep);
        }
        return false;
    }
    
    @Override
    public void appendHoverText(ItemStack p_41421_, Item.TooltipContext p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
        int sweep = getSweep(p_41421_);
        String sweepText = I18n.get("mode.manaita_sword");
        p_41423_.add(Component.literal(ManaitaToolUtils.ManaitaText.manaita_mode.formatting(sweepText + ": " + sweep)));
        p_41423_.add(Component.empty());
        p_41423_.add(Component.literal(ManaitaToolUtils.ManaitaText.manaita_infinity.formatting(I18n.get("info.attack"))));
    }
    
    public static int getSweep(ItemStack itemStack) {
        var customData = itemStack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            var tag = customData.getUnsafe();
            if (tag.contains("Sweep")) {
                return tag.getInt("Sweep");
            }
        }
        return 1;
    }
    
    public static void setSweep(ItemStack itemStack, int sweep) {
        var tag = new CompoundTag();
        tag.putInt("Sweep", sweep);
        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }
    
    @Override
    public void onManaitaKeyPress(ItemStack itemStack, Player player) {
        int sweep = getSweep(itemStack);
        sweep = ManaitaToolUtils.getNextSweepValue(sweep);
        setSweep(itemStack, sweep);
    }
    
    @Override
    public void onManaitaKeyPressOnClient(ItemStack itemStack, Player player) {
        int sweep = getSweep(itemStack);
        sweep = ManaitaToolUtils.getNextSweepValue(sweep);
        setSweep(itemStack, sweep);

        ManaitaToolUtils.handleSwordSweepChangeOnClient(
            itemStack, 
            player, 
            I18n.get("item.manaita_plus_neo.manaita_sword"), 
            sweep
        );
    }
    
    @Override
    public Component getName(ItemStack stack) {
        return Component.literal(ManaitaToolUtils.ManaitaText.manaita_infinity.formatting(I18n.get("item.manaita_plus_neo.manaita_sword")));
    }
}