package net.manaita_plus_neo.item.tools;

import net.manaita_plus_neo.item.data.IManaitaPlusKey;
import net.manaita_plus_neo.util.ManaitaToolUtils;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public class ManaitaPaxel extends DiggerItem implements IManaitaPlusKey {

    public ManaitaPaxel(Tier tier, Item.Properties properties) {
        super(tier, BlockTags.MINEABLE_WITH_PICKAXE, properties);
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
    public boolean canPerformAction(ItemStack stack, ItemAbility toolAction) {
        return true;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        int range = ManaitaToolUtils.getRange(stack);

        if (range > 1 && miningEntity instanceof Player player) {
            ManaitaToolUtils.performRangeBreak(stack, level, pos, player, range, 
            (tool, blockState) -> isCorrectToolForDrops(tool, blockState));
        }
        
        return true;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        int range = ManaitaToolUtils.getRange(stack);
        String rangeText = I18n.get("mode.manaita_tool");
        String sizeText = I18n.get("mode.range.name");
        tooltip.add(Component.literal(ManaitaToolUtils.ManaitaText.manaita_mode.formatting("[" + sizeText + "] " + rangeText + ": " + range + "x" + range + "x" + range)));
        boolean doubling = ManaitaToolUtils.isDoublingEnabled(stack);
        String doublingText = I18n.get("mode.doubling");
        String statusText = doubling ? I18n.get("info.on") : I18n.get("info.off");
        tooltip.add(Component.literal(ManaitaToolUtils.ManaitaText.manaita_mode.formatting("[" + doublingText + "] " + statusText)));
        tooltip.add(Component.empty());
        tooltip.add(Component.literal(ManaitaToolUtils.ManaitaText.manaita_infinity.formatting(I18n.get("info.attack"))));
    }

    @Override
    public void onManaitaKeyPress(ItemStack itemStack, Player player) {
        ManaitaToolUtils.handleManaitaKeyPress(itemStack, player, I18n.get("item.manaita_plus_neo.manaita_paxel"));
    }

    @Override
    public void onManaitaKeyPressOnClient(ItemStack itemStack, Player player) {
        ManaitaToolUtils.handleManaitaKeyPressOnClient(itemStack, player, I18n.get("item.manaita_plus_neo.manaita_paxel"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        if (!level.isClientSide) {
            if (player.isShiftKeyDown()) {
                ManaitaToolUtils.toggleRange(itemInHand, player, 21, I18n.get("item.manaita_plus_neo.manaita_paxel"));
            } else {
                ManaitaToolUtils.toggleEnchantment(itemInHand, player, I18n.get("item.manaita_plus_neo.manaita_paxel"));
            }
        }
        return InteractionResultHolder.pass(itemInHand);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        stack.setPopTime(0);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();

        if (player != null && player.isShiftKeyDown()) {
            return ManaitaToolUtils.performHoeRightClick(context);
        }

        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        
        // 去皮/刮削/除蜡
        if (blockstate.getToolModifiedState(context, ItemAbilities.AXE_STRIP, false) != null ||
            blockstate.getToolModifiedState(context, ItemAbilities.AXE_SCRAPE, false) != null ||
            blockstate.getToolModifiedState(context, ItemAbilities.AXE_WAX_OFF, false) != null) {
            return ManaitaToolUtils.performAxeRightClick(context);
        }
        // 铲地/熄灭篝火
        else if (
            blockstate.getToolModifiedState(context, ItemAbilities.SHOVEL_FLATTEN, false) != null ||
            blockstate.getBlock() instanceof CampfireBlock
        ) {
            return ManaitaToolUtils.performShovelRightClick(context);
        }
        // 耕地
        else if (blockstate.getToolModifiedState(context, ItemAbilities.HOE_TILL, false) != null) {
            return ManaitaToolUtils.performHoeRightClick(context);
        }
        else {
            return InteractionResult.PASS;
        }
    }
    
    @Override
    public Component getName(ItemStack stack) {
        return Component.literal(ManaitaToolUtils.ManaitaText.manaita_infinity.formatting(I18n.get("item.manaita_plus_neo.manaita_paxel")));
    }
}