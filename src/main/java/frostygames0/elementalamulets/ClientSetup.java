package frostygames0.elementalamulets;

import frostygames0.elementalamulets.client.particles.ModParticles;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ElementalAmulets.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    @SubscribeEvent
    public static void textureStitch(final TextureStitchEvent.Pre event) {
        event.addSprite(new ResourceLocation(ElementalAmulets.MOD_ID, "item/necklace_slot"));
    }

    @SubscribeEvent
    public static void particleFactoryRegister(final ParticleFactoryRegisterEvent event) {
        ModParticles.register();
    }
}
