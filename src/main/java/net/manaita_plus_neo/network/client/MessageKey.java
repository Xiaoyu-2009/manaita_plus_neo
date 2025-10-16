package net.manaita_plus_neo.network.client;

import net.manaita_plus_neo.ManaitaPlusNeo;
import net.manaita_plus_neo.item.data.IManaitaPlusKey;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MessageKey(byte key) implements CustomPacketPayload {
    public static final Type<MessageKey> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ManaitaPlusNeo.MOD_ID, "key"));
    public static final StreamCodec<FriendlyByteBuf, MessageKey> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.BYTE,
        MessageKey::key,
        MessageKey::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer sender = (ServerPlayer) context.player();
            switch (key) {
                case 0:
                    ItemStack mainHandItem = sender.getMainHandItem();
                    if (!mainHandItem.isEmpty() && mainHandItem.getItem() instanceof IManaitaPlusKey keyHandler) {
                        keyHandler.onManaitaKeyPress(mainHandItem, sender);
                    }
                    break;
                case 1:
                    for (ItemStack itemStack : sender.getInventory().armor) {
                        if (!itemStack.isEmpty() && itemStack.getItem() instanceof IManaitaPlusKey keyHandler) {
                            keyHandler.onManaitaKeyPress(itemStack, sender);
                        }
                    }
                    break;
            }
        });
    }
}