package net.manaita_plus_neo.event;

import net.manaita_plus_neo.ManaitaPlusNeo;
import net.manaita_plus_neo.item.ManaitaAxe;
import net.manaita_plus_neo.item.ManaitaPickaxe;
import net.manaita_plus_neo.item.ManaitaShovel;
import net.manaita_plus_neo.item.ManaitaHoe;
import net.manaita_plus_neo.item.ManaitaShears;
import net.manaita_plus_neo.util.ManaitaToolUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = ManaitaPlusNeo.MOD_ID)
public class ManaitaToolEvents {
    
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack itemstack = player.getMainHandItem();

        if (itemstack.getItem() instanceof ManaitaAxe) {
            ManaitaAxe axe = (ManaitaAxe) itemstack.getItem();
            Level level = player.level();
            int range = ManaitaToolUtils.getRange(itemstack);

            ManaitaToolUtils.performRangeBreak(
                itemstack, 
                level, 
                event.getPos(), 
                player, 
                range, 
                (tool, blockState) -> axe.isCorrectToolForDrops(tool, blockState)
            );
        } else if (itemstack.getItem() instanceof ManaitaPickaxe) {
            ManaitaPickaxe pickaxe = (ManaitaPickaxe) itemstack.getItem();
            Level level = player.level();
            int range = ManaitaToolUtils.getRange(itemstack);

            ManaitaToolUtils.performRangeBreak(
                itemstack, 
                level, 
                event.getPos(), 
                player, 
                range, 
                (tool, blockState) -> pickaxe.isCorrectToolForDrops(tool, blockState)
            );
        } else if (itemstack.getItem() instanceof ManaitaShovel) {
            ManaitaShovel shovel = (ManaitaShovel) itemstack.getItem();
            Level level = player.level();
            int range = ManaitaToolUtils.getRange(itemstack);

            ManaitaToolUtils.performRangeBreak(
                itemstack, 
                level, 
                event.getPos(), 
                player, 
                range, 
                (tool, blockState) -> shovel.isCorrectToolForDrops(tool, blockState)
            );
        } else if (itemstack.getItem() instanceof ManaitaHoe) {
            ManaitaHoe hoe = (ManaitaHoe) itemstack.getItem();
            Level level = player.level();
            int range = ManaitaToolUtils.getRange(itemstack);

            ManaitaToolUtils.performRangeBreak(
                itemstack, 
                level, 
                event.getPos(), 
                player, 
                range, 
                (tool, blockState) -> hoe.isCorrectToolForDrops(tool, blockState)
            );
        } else if (itemstack.getItem() instanceof ManaitaShears) {
            ManaitaShears shears = (ManaitaShears) itemstack.getItem();
            Level level = player.level();
            int range = ManaitaToolUtils.getRange(itemstack);

            ManaitaToolUtils.performRangeBreak(
                itemstack, 
                level, 
                event.getPos(), 
                player, 
                range, 
                (tool, blockState) -> shears.isCorrectToolForDrops(tool, blockState)
            );
        }
    }
}