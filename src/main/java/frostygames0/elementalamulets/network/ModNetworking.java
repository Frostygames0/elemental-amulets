/*
 *    This file is part of Elemental Amulets.
 *
 *     Elemental Amulets is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Elemental Amulets is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Elemental Amulets.  If not, see <https://www.gnu.org/licenses/>.
 */

package frostygames0.elementalamulets.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;


import static frostygames0.elementalamulets.ElementalAmulets.modPrefix;

/**
 * @author Frostygames0
 * @date 21.09.2021 22:01
 */
public class ModNetworking {
    private static final String PROTOCOL_NETWORK = "1.0";
    private static int ID = 0;

    public static SimpleChannel INSTANCE;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(modPrefix("main"),
                () -> PROTOCOL_NETWORK,
                PROTOCOL_NETWORK::equals,
                PROTOCOL_NETWORK::equals);

        INSTANCE.messageBuilder(SOpenAmuletBeltGUIPacket.class, nextID())
                .encoder(((cOpenAmuletBeltGUIPacket, packetBuffer) -> {}))
                .decoder(buf -> new SOpenAmuletBeltGUIPacket())
                .consumer(SOpenAmuletBeltGUIPacket::handle)
                .add();
        INSTANCE.messageBuilder(CUpdatePlayerVelocityPacket.class, nextID())
                .encoder(CUpdatePlayerVelocityPacket::toBytes)
                .decoder(CUpdatePlayerVelocityPacket::new)
                .consumer(CUpdatePlayerVelocityPacket::handle)
                .add();
        INSTANCE.messageBuilder(SCombinePacket.class, nextID())
                .encoder(SCombinePacket::toBytes)
                .decoder(SCombinePacket::new)
                .consumer(SCombinePacket::handle)
                .add();
    }

    public static void sendToClient(Object packet, ServerPlayerEntity player) {
        INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}
