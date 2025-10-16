package net.manaita_plus_neo.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.ItemAbility;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.manaita_plus_neo.util.ManaitaToolUtils;

public class ManaitaAxe extends AxeItem {
    
    public ManaitaAxe(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public int getDamage(ItemStack stack) {
        return 0;
    }
    
    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return Float.MAX_VALUE;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        return true;
    }

    @Override
    public boolean canAttackBlock(BlockState p_41441_, Level p_41442_, BlockPos p_41443_, Player p_41444_) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, Item.TooltipContext p_41422_, java.util.List<net.minecraft.network.chat.Component> p_41423_, net.minecraft.world.item.TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
        int range = ManaitaToolUtils.getRange(p_41421_);
        String rangeText = I18n.get("mode.manaita_tool");
        String sizeText = I18n.get("mode.range.name");
        p_41423_.add(Component.literal(ManaitaToolUtils.ManaitaText.manaita_mode.formatting("[" + sizeText + "] " + rangeText + ": " + range + "x" + range + "x" + range)));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        ItemStack itemInHand = p_41433_.getItemInHand(p_41434_);
        if (!p_41432_.isClientSide) {
            if (p_41433_.isShiftKeyDown()) {
                ManaitaToolUtils.toggleRange(itemInHand, p_41433_, 19, I18n.get("item.manaita_plus_neo.manaita_axe"));
            } else {
                ManaitaToolUtils.toggleEnchantment(itemInHand, p_41433_, I18n.get("item.manaita_plus_neo.manaita_axe"));
            }
        }
        return InteractionResultHolder.pass(itemInHand);
    }

    public int getRange(ItemStack itemStack) {
        return ManaitaToolUtils.getRange(itemStack);
    }

    public void setRange(ItemStack itemStack, int range) {
        ManaitaToolUtils.setRange(itemStack, range);
    }

    public InteractionResult useOn(UseOnContext p_40529_) {
        return ManaitaToolUtils.performAxeRightClick(p_40529_);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility toolAction) {
        return net.neoforged.neoforge.common.ItemAbilities.DEFAULT_AXE_ACTIONS.contains(toolAction);
    }
}