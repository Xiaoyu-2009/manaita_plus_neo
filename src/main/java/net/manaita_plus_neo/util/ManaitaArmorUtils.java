package net.manaita_plus_neo.util;

import net.manaita_plus_neo.item.armor.ManaitaBoots;
import net.manaita_plus_neo.item.armor.ManaitaChestplate;
import net.manaita_plus_neo.item.armor.ManaitaHelmet;
import net.manaita_plus_neo.item.armor.ManaitaLeggings;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;

public class ManaitaArmorUtils {

    public static boolean isWearingManaitaArmor(Player player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        return isManaitaArmor(helmet) || isManaitaArmor(chestplate) ||
                isManaitaArmor(leggings) || isManaitaArmor(boots);
    }

    public static boolean isManaitaArmor(ItemStack stack) {
        if (stack.isEmpty()) return false;

        return stack.getItem() instanceof ManaitaHelmet || stack.getItem() instanceof ManaitaChestplate ||
                stack.getItem() instanceof ManaitaLeggings || stack.getItem() instanceof ManaitaBoots;
    }
    
    // 头盔/夜视
    public static boolean getNightVision(ItemStack itemStack) {
        var customData = itemStack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            var tag = customData.getUnsafe();
            if (tag.contains("NightVision")) {
                return tag.getBoolean("NightVision");
            }
        }
        return false;
    }
    
    public static void setNightVision(ItemStack itemStack, boolean enabled) {
        var tag = new CompoundTag();
        tag.putBoolean("NightVision", enabled);
        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }
    
    // 护腿/隐身
    public static boolean getInvisibility(ItemStack itemStack) {
        var customData = itemStack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            var tag = customData.getUnsafe();
            if (tag.contains("Invisibility")) {
                return tag.getBoolean("Invisibility");
            }
        }
        return false;
    }
    
    public static void setInvisibility(ItemStack itemStack, boolean enabled) {
        var tag = new CompoundTag();
        tag.putBoolean("Invisibility", enabled);
        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }
    
    // 靴子/速度
    public static int getSpeed(ItemStack itemStack) {
        var customData = itemStack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            var tag = customData.getUnsafe();
            if (tag.contains("Speed")) {
                return tag.getInt("Speed");
            }
        }
        return 0;
    }
    
    public static void setSpeed(ItemStack itemStack, int speed) {
        var tag = new CompoundTag();
        tag.putInt("Speed", speed);
        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    public static void handleNightVisionKeyPressOnClient(ItemStack itemStack, Player player) {
        boolean nightVision = !getNightVision(itemStack);
        setNightVision(itemStack, nightVision);
        String nightVisionText = I18n.get("mode.nightvision");
        String statusText = nightVision ? I18n.get("info.on") : I18n.get("info.off");
        player.displayClientMessage(Component.literal(
            ManaitaToolUtils.ManaitaText.manaita_mode.formatting(
                "[" + I18n.get("item.manaita_plus_neo.manaita_helmet") + "] " + nightVisionText + ": " + statusText
            )
        ), true);
    }
    
    public static void handleInvisibilityKeyPressOnClient(ItemStack itemStack, Player player) {
        boolean invisibility = !getInvisibility(itemStack);
        setInvisibility(itemStack, invisibility);
        String invisibilityText = I18n.get("mode.invisibility");
        String statusText = invisibility ? I18n.get("info.on") : I18n.get("info.off");
        player.displayClientMessage(Component.literal(
            ManaitaToolUtils.ManaitaText.manaita_mode.formatting(
                "[" + I18n.get("item.manaita_plus_neo.manaita_leggings") + "] " + invisibilityText + ": " + statusText
            )
        ), true);
    }
    
    public static void handleSpeedKeyPressOnClient(ItemStack itemStack, Player player) {
        int speed = getSpeed(itemStack);
        speed = (speed + 1) % 10;
        setSpeed(itemStack, speed);
        String speedText = I18n.get("mode.speed");
        player.displayClientMessage(Component.literal(
            ManaitaToolUtils.ManaitaText.manaita_mode.formatting(
                "[" + I18n.get("item.manaita_plus_neo.manaita_boots") + "] " + speedText + ": " + speed
            )
        ), true);
    }
}