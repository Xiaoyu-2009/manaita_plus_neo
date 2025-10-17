package net.manaita_plus_neo.item.weapon;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.network.chat.Component;
import net.minecraft.client.resources.language.I18n;
import net.manaita_plus_neo.util.ManaitaToolUtils;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ManaitaBow extends Item {
    public ManaitaBow(Properties properties) {
        super(properties);
    }

    @Override
    public int getDamage(ItemStack stack) {
        return 0;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.empty());
        tooltip.add(Component.literal(ManaitaToolUtils.ManaitaText.manaita_infinity.formatting(I18n.get("info.attack"))));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            Arrow arrow = EntityType.ARROW.create(level);
            if (arrow != null) {
                arrow.setPos(player.getX(), player.getEyeY() - 0.1F, player.getZ());
                arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 100.0F, 0.0F);
                arrow.setCritArrow(true);
                arrow.setBaseDamage(10000.0F);

                level.addFreshEntity(arrow);
            }
        }
        return InteractionResultHolder.success(itemstack);
    }
    
    @Override
    public Component getName(ItemStack stack) {
        return Component.literal(ManaitaToolUtils.ManaitaText.manaita_infinity.formatting(I18n.get("item.manaita_plus_neo.manaita_bow")));
    }
}