package frostygames0.elementalamulets.network;

import frostygames0.elementalamulets.blocks.tiles.ElementalCombinatorTile;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;


import java.util.Objects;
import java.util.function.Supplier;

public class SCombineElementalPacket {
    private final BlockPos pos;

    public SCombineElementalPacket(BlockPos pos) {
        this.pos = pos;
    }

    public SCombineElementalPacket(PacketBuffer buffer) {
        this.pos = buffer.readBlockPos();
    }

    public static void toBytes(SCombineElementalPacket pkt, PacketBuffer buffer) {
        buffer.writeBlockPos(pkt.pos);
    }

    public static void handle(SCombineElementalPacket msg, Supplier<NetworkEvent.Context> sup) {
        NetworkEvent.Context ctx = sup.get();
        World world = Objects.requireNonNull(ctx.getSender()).world;
        ctx.enqueueWork(() -> {
            if(world != null) {
                if(world.isBlockPresent(msg.pos)){
                    TileEntity te = world.getTileEntity(msg.pos);
                    if (te instanceof ElementalCombinatorTile) {
                        ElementalCombinatorTile tile = (ElementalCombinatorTile) te;
                        //tile.combineElemental(ctx.getSender());
                        tile.startCombination();
                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
