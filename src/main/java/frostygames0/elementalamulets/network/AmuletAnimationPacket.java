package frostygames0.elementalamulets.network;

import frostygames0.elementalamulets.ElementalAmulets;
import frostygames0.elementalamulets.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * This packet is meant to be used for displaying amulet totem like animation
 * but since all ICurio items have curioBreak method that client side only
 * I don't need this :/
 */
public class AmuletAnimationPacket {
    private final ItemStack stack;

    public AmuletAnimationPacket(PacketBuffer buffer) {
        stack = buffer.readItemStack();
    }

    public AmuletAnimationPacket(ItemStack stack) {
        this.stack = stack;
    }

    public static void toBytes(AmuletAnimationPacket msg, PacketBuffer buffer) {
        buffer.writeItemStack(msg.stack);
    }

    public static void handle(AmuletAnimationPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                if(ModConfig.cached.DISPLAY_TOTEM_LIKE_ANIM_ONBREAK) {
                    Minecraft.getInstance().gameRenderer.displayItemActivation(msg.stack);
                }
            } else {
                ElementalAmulets.LOGGER.error("AmuletAnimationPacket is meant to be sent to client!");
            }
        });
        ctx.get().setPacketHandled(true);
    }
}