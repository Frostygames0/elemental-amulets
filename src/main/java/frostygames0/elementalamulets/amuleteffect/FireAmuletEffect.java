package frostygames0.elementalamulets.amuleteffect;

import frostygames0.elementalamulets.items.amulets.FireAmulet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class FireAmuletEffect {

    static void onLivingAttack(LivingAttackEvent event) {
        LivingEntity entity = event.getEntityLiving();
        DamageSource source = event.getSource();
        if(entity.world.isRemote()) {
            if (source.isFireDamage()) {
                CuriosApi.getCuriosHelper().findEquippedCurio(item -> item.getItem() instanceof FireAmulet, entity).ifPresent((triple) -> {
                    FireAmulet amulet = (FireAmulet) triple.getRight().getItem();
                    float fire = amulet.getFireResist(triple.getRight());
                    float lava = amulet.getLavaResist(triple.getRight());
                    if (source == DamageSource.IN_FIRE || source == DamageSource.ON_FIRE) {
                        if (fire < 0.001f) event.setCanceled(true);
                    } else {
                        if (lava < 0.001f) event.setCanceled(true);
                    }
                });
            }
        }
    }

    static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        DamageSource source = event.getSource();
        if(entity.world.isRemote()) {
            if (source.isFireDamage()) {
                CuriosApi.getCuriosHelper().findEquippedCurio(item -> item.getItem() instanceof FireAmulet, entity).ifPresent((triple) -> {
                    FireAmulet amulet = (FireAmulet) triple.getRight().getItem();
                    float fire = amulet.getFireResist(triple.getRight());
                    float lava = amulet.getLavaResist(triple.getRight());
                    if (source == DamageSource.IN_FIRE || source == DamageSource.ON_FIRE) {
                        if (fire < 0.999f) {
                            if (fire < 0.001f) {
                                event.setCanceled(true);
                            }
                            event.setAmount(event.getAmount() * fire);
                        }
                    } else {
                        if (lava < 0.999f) {
                            if (lava < 0.001f) {
                                event.setCanceled(true);
                            }
                            event.setAmount(event.getAmount() * lava);
                        }
                    }
                });
            }
        }
    }

}
