package net.manaita_plus_neo.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.block.Block;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.neoforge.common.ItemAbilities;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

public class ManaitaToolUtils {

    public enum ManaitaText {
        manaita_infinity(new ChatFormatting[] { 
            ChatFormatting.RED, ChatFormatting.GOLD, ChatFormatting.YELLOW,
            ChatFormatting.GREEN, ChatFormatting.AQUA, ChatFormatting.BLUE, 
            ChatFormatting.LIGHT_PURPLE },80.0D
        ),
        manaita_mode(new ChatFormatting[] { ChatFormatting.YELLOW, ChatFormatting.YELLOW, ChatFormatting.YELLOW,
            ChatFormatting.YELLOW, ChatFormatting.YELLOW, ChatFormatting.YELLOW, ChatFormatting.GOLD,
            ChatFormatting.RED, ChatFormatting.YELLOW, ChatFormatting.YELLOW, ChatFormatting.YELLOW,
            ChatFormatting.YELLOW, ChatFormatting.YELLOW, ChatFormatting.YELLOW, ChatFormatting.GOLD,
            ChatFormatting.RED }, 120.0D
        ),
        manaita_enchantment(new ChatFormatting[] { ChatFormatting.LIGHT_PURPLE, ChatFormatting.LIGHT_PURPLE,
            ChatFormatting.LIGHT_PURPLE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.BLUE,
            ChatFormatting.DARK_PURPLE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.LIGHT_PURPLE,
            ChatFormatting.LIGHT_PURPLE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.AQUA,
            ChatFormatting.DARK_PURPLE }, 120.0D
        );

        private final String[] chatFormattings;
        private final double delay;

        ManaitaText(ChatFormatting[] chatFormattings, double delay) {
            this.chatFormattings = Arrays.stream(chatFormattings).map(ChatFormatting::toString).toArray(String[]::new);
            if (delay <= 0.0D) delay = 0.001D;
            this.delay = delay;
        }

        public String formatting(String input) {
            String[] colours = this.chatFormattings;
            StringBuilder sb = new StringBuilder(input.length() * 3);
            int offset = (int) Math.floor((System.currentTimeMillis() & 0x3FFFL) / delay) % colours.length;
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                int col = (i + colours.length - offset) % colours.length;
                sb.append(colours[col]);
                sb.append(c);
            }
            return sb.toString();
        }
    }

    public static boolean isDoublingEnabled(ItemStack itemStack) {
        var customData = itemStack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            var tag = customData.getUnsafe();
            if (tag.contains("Doubling")) {
                return tag.getBoolean("Doubling");
            }
        }
        return false;
    }

    public static void setDoublingEnabled(ItemStack itemStack, boolean enabled) {
        var tag = new CompoundTag();
        tag.putBoolean("Doubling", enabled);
        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    public static void toggleRange(ItemStack itemStack, Player player, int maxRange, String itemName) {
        int currentRange = getRange(itemStack);
        int newRange = currentRange + 2;
        if (newRange > maxRange) {
            newRange = 1;
        }
        setRange(itemStack, newRange);

        String rangeText = I18n.get("mode.range.name");
        player.sendSystemMessage(Component.literal(
            ManaitaText.manaita_mode.formatting(
                "[" + itemName + "] " + rangeText + ": " + newRange + "x" + newRange + "x" + newRange
            )
        ));
    }

    public static void toggleEnchantment(ItemStack itemStack, Player player, String itemName) {
        var currentEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(itemStack);
        var mutableEnchantments = new ItemEnchantments.Mutable(currentEnchantments);

        Holder<Enchantment> silkTouchHolder = player.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.SILK_TOUCH);
        Holder<Enchantment> fortuneHolder = player.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.FORTUNE);

        if (!hasSilkTouch(currentEnchantments)) {
            mutableEnchantments.removeIf(holder -> holder.equals(fortuneHolder));
            mutableEnchantments.set(silkTouchHolder, 1);
            EnchantmentHelper.setEnchantments(itemStack, mutableEnchantments.toImmutable());
            String s = I18n.get("enchantments.silktouch");
            String enchantmentText = I18n.get("info.enchantment");
            player.sendSystemMessage(Component.literal(
                ManaitaText.manaita_enchantment.formatting(itemName + enchantmentText + ": " + s)
            ));
        } else {
            mutableEnchantments.removeIf(holder -> holder.equals(silkTouchHolder));
            mutableEnchantments.set(fortuneHolder, 10);
            EnchantmentHelper.setEnchantments(itemStack, mutableEnchantments.toImmutable());
            String s = I18n.get("enchantments.fortune");
            String enchantmentText = I18n.get("info.enchantment");
            player.sendSystemMessage(Component.literal(
                ManaitaText.manaita_enchantment.formatting(itemName + enchantmentText + ": " + s)
            ));
        }
    }

    public static boolean hasSilkTouch(ItemEnchantments enchantments) {
        return enchantments.keySet().stream().anyMatch(holder -> holder.is(Enchantments.SILK_TOUCH));
    }

    public static int getRange(ItemStack itemStack) {
        var customData = itemStack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            var tag = customData.getUnsafe();
            if (tag.contains("Range")) {
                return tag.getInt("Range");
            }
        }
        return 1;
    }

    public static void setRange(ItemStack itemStack, int range) {
        if (range <= 0) range = 1;
        var tag = new CompoundTag();
        tag.putInt("Range", range);
        itemStack.set(DataComponents.CUSTOM_DATA,CustomData.of(tag));
    }

    public static void performRangeBreak(ItemStack toolItem, Level level, BlockPos pos, Player player, int range, BiPredicate<ItemStack, BlockState> canMineBlock) {
        if (range <= 1) {
            return;
        }

        int radius = range / 2;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x == 0 && y == 0 && z == 0) {
                        continue;
                    }

                    mutablePos.set(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                    BlockState blockstate = level.getBlockState(mutablePos);

                    if (canMineBlock.test(toolItem, blockstate)) {
                        if (player.getAbilities().instabuild) {
                            level.removeBlock(mutablePos, false);
                        } else {
                            destroyBlockWithEnchantments(toolItem, level, player, mutablePos, blockstate);
                        }
                    }
                }
            }
        }
    }

    private static void destroyBlockWithEnchantments(ItemStack toolItem, Level level, Player player, BlockPos pos,BlockState blockstate) {
        if (!(level instanceof ServerLevel)) {
            level.removeBlock(pos, false);
            return;
        }
        
        Block.dropResources(blockstate, level, pos, null, player, toolItem);
        level.removeBlock(pos, false);
    }

    public static InteractionResult performAxeRightClick(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Player player = context.getPlayer();
        int i = getRange(context.getItemInHand()) >> 1;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int xM = blockpos.getX() + i;
        int yM = blockpos.getY() + i;
        int zM = blockpos.getZ() + i;
        boolean flag = false;
        for (int x = blockpos.getX() - i; x <= xM; x++) {
            for (int y = blockpos.getY() - i; y <= yM; y++) {
                for (int z = blockpos.getZ() - i; z <= zM; z++) {
                    mutableBlockPos.set(x, y, z);
                    BlockState blockstate = level.getBlockState(mutableBlockPos);

                    Optional<BlockState> optional = Optional.ofNullable(
                        blockstate.getToolModifiedState(context, ItemAbilities.AXE_STRIP, false)
                    );

                    Optional<BlockState> optional1 = optional.isPresent() ? Optional.empty() : 
                    Optional.ofNullable(
                        blockstate.getToolModifiedState(context, ItemAbilities.AXE_SCRAPE, false)
                    );

                    Optional<BlockState> optional2 = optional.isPresent() || optional1.isPresent() ? Optional.empty() : 
                    Optional.ofNullable(
                        blockstate.getToolModifiedState(context, ItemAbilities.AXE_WAX_OFF, false)
                    );

                    ItemStack itemstack = context.getItemInHand();
                    Optional<BlockState> optional3 = Optional.empty();

                    if (optional.isPresent()) {
                        level.playSound(player, mutableBlockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                        optional3 = optional;
                    } else if (optional1.isPresent()) {
                        level.playSound(player, mutableBlockPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        level.levelEvent(player, 3005, mutableBlockPos, 0);
                        optional3 = optional1;
                    } else if (optional2.isPresent()) {
                        level.playSound(player, mutableBlockPos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                        level.levelEvent(player, 3004, mutableBlockPos, 0);
                        optional3 = optional2;
                    }

                    if (optional3.isPresent()) {
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, mutableBlockPos,itemstack);
                        }

                        level.setBlock(mutableBlockPos, optional3.get(), 11);
                        level.gameEvent(GameEvent.BLOCK_CHANGE, mutableBlockPos, GameEvent.Context.of(player, optional3.get()));
                        if (player != null) {
                            itemstack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
                        }
                        flag = true;
                    }
                }
            }
        }
        return flag ? InteractionResult.sidedSuccess(level.isClientSide) : InteractionResult.PASS;
    }

    public static InteractionResult performHoeRightClick(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Player player = context.getPlayer();
        int i = getRange(context.getItemInHand()) >> 1;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int xM = blockpos.getX() + i;
        int yM = blockpos.getY() + i;
        int zM = blockpos.getZ() + i;
        boolean flag = false;
        for (int x = blockpos.getX() - i; x <= xM; x++) {
            for (int y = blockpos.getY() - i; y <= yM; y++) {
                for (int z = blockpos.getZ() - i; z <= zM; z++) {
                    mutableBlockPos.set(x, y, z);
                    BlockState toolModifiedState = level.getBlockState(mutableBlockPos).getToolModifiedState(context, ItemAbilities.HOE_TILL, false);
                    if (toolModifiedState != null) {
                        level.playSound(player, mutableBlockPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                        if (!level.isClientSide) {
                            level.setBlock(mutableBlockPos, toolModifiedState, 11);
                            level.gameEvent(GameEvent.BLOCK_CHANGE, mutableBlockPos, GameEvent.Context.of(player, toolModifiedState));
                            if (player != null) {
                                context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
                            }
                        }
                        flag = true;
                    }
                }
            }
        }
        return flag ? InteractionResult.sidedSuccess(level.isClientSide) : InteractionResult.PASS;
    }

    public static void handleDropsAndExp(BlockEvent.BreakEvent event, ItemStack toolStack) {
        if (isDoublingEnabled(toolStack) && !event.getPlayer().getAbilities().instabuild) {
            Level level = event.getPlayer().level();
            BlockPos pos = event.getPos();

            BlockState state = event.getState();
            BlockEntity blockEntity = level.getBlockEntity(pos);
            List<ItemStack> drops = Block.getDrops(state, (ServerLevel) level, pos, blockEntity, event.getPlayer(), toolStack);

            for (ItemStack drop : drops) {
                if (!drop.isEmpty()) {
                    ItemStack extraDrop = drop.copy();
                    extraDrop.setCount(extraDrop.getCount() * 3);
                    ItemEntity itemEntity = new ItemEntity(
                        level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, extraDrop
                    );
                    level.addFreshEntity(itemEntity);
                }
            }
            
            int exp = state.getExpDrop((ServerLevel) level, pos, blockEntity, event.getPlayer(), toolStack);
            if (exp > 0) {
                ExperienceOrb.award((ServerLevel) level, 
                    Vec3.atCenterOf(pos), exp * 3);
            }
        }
    }

    public static void handleManaitaKeyPress(ItemStack itemStack, Player player, String itemName) {
        boolean doubling = !isDoublingEnabled(itemStack);
        setDoublingEnabled(itemStack, doubling);
    }

    public static void handleManaitaKeyPressOnClient(ItemStack itemStack, Player player, String itemName) {
        boolean doubling = !isDoublingEnabled(itemStack);
        setDoublingEnabled(itemStack, doubling);
        String doublingText = I18n.get("mode.doubling");
        String statusText = doubling ? I18n.get("info.on") : I18n.get("info.off");
        player.sendSystemMessage(Component.literal(
            ManaitaText.manaita_mode.formatting(
                "[" + itemName + "] " + doublingText + ": " + statusText
            )
        ));
    }
}