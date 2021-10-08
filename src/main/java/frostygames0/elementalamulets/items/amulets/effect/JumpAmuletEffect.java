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

package frostygames0.elementalamulets.items.amulets.effect;

import frostygames0.elementalamulets.core.init.ModItems;
import frostygames0.elementalamulets.items.amulets.JumpAmulet;
import frostygames0.elementalamulets.network.CUpdatePlayerVelocityPacket;
import frostygames0.elementalamulets.network.ModNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class JumpAmuletEffect {

    static void onLivingHurt(LivingHurtEvent event) {
        if(event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (!player.level.isClientSide()) {
                if (event.getSource() == DamageSource.FALL) {
                    CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.JUMP_AMULET.get(), player).ifPresent((triple) -> {
                        float fallResist = ((JumpAmulet) triple.getRight().getItem()).getFallResist(triple.getRight());
                        if (!event.isCanceled() && fallResist > 0) {
                            float finalDamage = Math.max(0, (event.getAmount() - fallResist));
                            if (finalDamage == 0) {
                                event.setCanceled(true);
                            } else {
                                event.setAmount(finalDamage);
                            }
                        }
                    });
                }
            }
        }
    }
    static void onLivingAttack(LivingAttackEvent event) {
        if(event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (!player.level.isClientSide()) {
                if (event.getSource() == DamageSource.FALL) {
                    CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.JUMP_AMULET.get(), player).ifPresent((triple) -> {
                        float fallResist = ((JumpAmulet) triple.getRight().getItem()).getFallResist(triple.getRight());
                        if (!event.isCanceled() && fallResist > 0) {
                            float finalDamage = Math.max(0, (event.getAmount() - fallResist));
                            if (finalDamage == 0) {
                                event.setCanceled(true);
                            }
                        }
                    });
                }
            }
        }
    }

    static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        if(event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            World world = player.getCommandSenderWorld();
            if (!world.isClientSide) {
                if (world.getFluidState(player.blockPosition()).isEmpty()) {
                    CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.JUMP_AMULET.get(), player).ifPresent(triple -> {
                        ItemStack stack = triple.getRight();
                        JumpAmulet item = (JumpAmulet) stack.getItem();

                        Vector3d vector = player.getDeltaMovement().add(0, item.getJump(stack), 0);
                        ModNetworking.sendToClient(new CUpdatePlayerVelocityPacket(vector.x, vector.y, vector.z), (ServerPlayerEntity) player);
                    });
                }
            }
        }
    }

}
