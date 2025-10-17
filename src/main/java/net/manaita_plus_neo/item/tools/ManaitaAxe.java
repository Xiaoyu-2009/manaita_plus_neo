package net.manaita_plus_neo.item.tools;

import net.manaita_plus_neo.item.data.IManaitaPlusKey;
import net.manaita_plus_neo.util.ManaitaToolUtils;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;

public class ManaitaAxe extends AxeItem implements IManaitaPlusKey {
    
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
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        int range = ManaitaToolUtils.getRange(stack);

        if (miningEntity instanceof Player player) {
            ManaitaToolUtils.performRangeBreak(stack, level, pos, player, range, 
            (tool, blockState) -> isCorrectToolForDrops(tool, blockState));
        }
        
        return true;
    }

    @Override
    public boolean canAttackBlock(BlockState p_41441_, Level p_41442_, BlockPos p_41443_, Player p_41444_) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, Item.TooltipContext p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
        int range = ManaitaToolUtils.getRange(p_41421_);
        String rangeText = I18n.get("mode.manaita_tool");
        String sizeText = I18n.get("mode.range.name");
        p_41423_.add(Component.literal(ManaitaToolUtils.ManaitaText.manaita_mode.formatting("[" + sizeText + "] " + rangeText + ": " + range + "x" + range + "x" + range)));
        boolean doubling = ManaitaToolUtils.isDoublingEnabled(p_41421_);
        String doublingText = I18n.get("mode.doubling");
        String statusText = doubling ? I18n.get("info.on") : I18n.get("info.off");
        p_41423_.add(Component.literal(ManaitaToolUtils.ManaitaText.manaita_mode.formatting("[" + doublingText + "] " + statusText)));
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
        return ItemAbilities.DEFAULT_AXE_ACTIONS.contains(toolAction);
    }

    public static void handleDropsAndExp(BlockEvent.BreakEvent event, ItemStack toolStack) {
        ManaitaToolUtils.handleDropsAndExp(event, toolStack);
    }
    
    @Override
    public void onManaitaKeyPress(ItemStack itemStack, Player player) {
        ManaitaToolUtils.handleManaitaKeyPress(itemStack, player, I18n.get("item.manaita_plus_neo.manaita_axe"));
    }

    @Override
    public void onManaitaKeyPressOnClient(ItemStack itemStack, Player player) {
        ManaitaToolUtils.handleManaitaKeyPressOnClient(itemStack, player, I18n.get("item.manaita_plus_neo.manaita_axe"));
    }
    
/*     @Override
    public Component getName(ItemStack stack) {
        return Component.literal(ManaitaToolUtils.ManaitaText.manaita_infinity.formatting(I18n.get("item.manaita_plus_neo.manaita_axe")));
    } */
}