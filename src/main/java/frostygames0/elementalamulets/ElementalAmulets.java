package frostygames0.elementalamulets;

import frostygames0.elementalamulets.config.ModConfig;
import frostygames0.elementalamulets.core.init.*;
import frostygames0.elementalamulets.items.AbstractAmuletItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.DynamicBucketModel;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.SlotTypeMessage;

@Mod(ElementalAmulets.MOD_ID)
public class ElementalAmulets {
    public static final String MOD_ID = "elementalamulets";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ItemGroup GROUP = new ElementalAmuletsGroup();
    public ElementalAmulets() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModTiles.TILES.register(bus);
        ModContainers.CONTAINERS.register(bus);
        ModRecipes.SERIALIZERS.register(bus);

        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.SERVER, ModConfig.SERVER_SPEC);
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.CLIENT, ModConfig.CLIENT_SPEC);

        bus.addListener(this::enqueueIMC);
        bus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("necklace").size(3).build());
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() ->{
            /*for(RegistryObject<Item> entry : ModItems.ITEMS.getEntries()) {
                Item item = entry.get();
                if(item instanceof AbstractAmuletItem) {
                    ItemModelsProperties.registerProperty(item, new ResourceLocation(ElementalAmulets.MOD_ID, "amulet_durability"), (stack, world, living) -> {
                            if (stack.getDamage() <= 200) return 0.0f;
                            else if (stack.getDamage() <= 400) return 0.4f;
                            else if (stack.getDamage() <= 600) return 0.6f;
                            else if (stack.getDamage() <= 800) return 0.8f;
                            else return 1.0f;
                    });
                }
            }*/
            ModItems.ITEMS.getEntries().stream().map(RegistryObject::get).filter(item -> item instanceof AbstractAmuletItem).forEach(item -> {
                ItemModelsProperties.registerProperty(item, new ResourceLocation(ElementalAmulets.MOD_ID, "amulet_durability"), (stack, world, living) -> {
                    if (stack.getDamage() <= 200) return 0.0f;
                    else if (stack.getDamage() <= 400) return 0.4f;
                    else if (stack.getDamage() <= 600) return 0.6f;
                    else if (stack.getDamage() <= 800) return 0.8f;
                    else return 1.0f;
                    //return stack.getDamage()*0.001f;
                });
            });
        });
    }
}
