package frostygames0.elementalamulets.core.init;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import frostygames0.elementalamulets.ElementalAmulets;
import frostygames0.elementalamulets.items.amulets.AmuletItem;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.ProcessorLists;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = ElementalAmulets.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModVillagers{
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, ElementalAmulets.MOD_ID);
    public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, ElementalAmulets.MOD_ID);

    public static final RegistryObject<PointOfInterestType> JEWELLER_POI = POI_TYPES.register("jeweller",
            () -> new PointOfInterestType("jeweller", PointOfInterestType.getBlockStates(ModBlocks.ELEMENTAL_COMBINATOR.get()), 1, 1));
    public static final RegistryObject<VillagerProfession> JEWELLER = PROFESSIONS.register("jeweller",
            () -> new VillagerProfession("jeweller", JEWELLER_POI.get(), ImmutableSet.of(ModItems.ELEMENTAL_ORE.get(), ModItems.FIRE_ELEMENT.get(), ModItems.EARTH_ELEMENT.get(), ModItems.WATER_ELEMENT.get(), ModItems.AIR_ELEMENT.get()), ImmutableSet.of(), SoundEvents.ENCHANTMENT_TABLE_USE));

    public static void register(IEventBus bus) {
        POI_TYPES.register(bus);
        PROFESSIONS.register(bus);
    }

    @SubscribeEvent
    public static void registerTrades(final VillagerTradesEvent event) {
        if(event.getType() == JEWELLER.get()) {
            // Level 1 trades
            List<VillagerTrades.ITrade> trades1 = event.getTrades().get(1);
            trades1.add(new BasicTrade(new ItemStack(ModItems.ELEMENTAL_SHARDS.get(), 5), new ItemStack(Items.EMERALD), 10, 1, 1f));

            // Level 2 trades
            List<VillagerTrades.ITrade> trades2 = event.getTrades().get(2);
            trades2.add(new BasicTrade(5, new ItemStack(ModItems.GUIDE_BOOK.get()), 1, 2, 1.3F));
            trades2.add(new BasicTrade(15, new ItemStack(ModBlocks.ELEMENTAL_COMBINATOR.get().asItem()), 2, 5, 1.5f));

            List<VillagerTrades.ITrade> trades3 = event.getTrades().get(3);
            trades3.add(new BasicTrade(3, new ItemStack(ModItems.FIRE_ELEMENT.get()), 10, 1, 1.2f));
            trades3.add(new BasicTrade(3, new ItemStack(ModItems.WATER_ELEMENT.get()), 10, 1, 1.2f));
            trades3.add(new BasicTrade(3, new ItemStack(ModItems.AIR_ELEMENT.get()), 10, 1, 1.2f));
            trades3.add(new BasicTrade(3, new ItemStack(ModItems.EARTH_ELEMENT.get()), 10, 1, 1.2f));

        }
    }

    @SubscribeEvent
    public static void registerWandererTrades(final WandererTradesEvent event) {
        Random rand = new Random(8080);
        event.getRareTrades().add(new BasicTrade(45, AmuletItem.getStackWithTier(new ItemStack(ModItems.getAmulets().get(rand.nextInt(ModItems.getAmulets().size()))), 3), 1, 25, 1.5f));
    }

    public static class Structures {
        public static void init() {
                PlainsVillagePools.bootstrap();
                SavannaVillagePools.bootstrap();
                TaigaVillagePools.bootstrap();
                DesertVillagePools.bootstrap();
                SnowyVillagePools.bootstrap();
                for (String biome : new String[]{"plains"}) { // This is because it should be all village biomes but for now there is only plains
                    addHouseToPool(new ResourceLocation("village/" + biome + "/houses"),
                            ElementalAmulets.MOD_ID + ":villages/jeweller_house_" + biome, 12);
                }
                ElementalAmulets.LOGGER.debug("Jeweller's house was successfully added to all existing vanilla plains villages");
        }

        private static void addHouseToPool(ResourceLocation pool, String houseToAdd, int weight) {
            JigsawPattern old = WorldGenRegistries.TEMPLATE_POOL.get(pool);
            if (old == null) {
                ElementalAmulets.LOGGER.warn("Jigsaw pool " + pool + " is not found! Skipping Jeweller's house generation");
                return;
            }
            List<JigsawPiece> pieces = old.getShuffledTemplates(ThreadLocalRandom.current());
            List<Pair<JigsawPiece, Integer>> newPieces = pieces.stream().map(p -> Pair.of(p, 1)).collect(Collectors.toList());
            JigsawPiece newPiece = JigsawPiece.legacy(houseToAdd, ProcessorLists.MOSSIFY_10_PERCENT).apply(JigsawPattern.PlacementBehaviour.RIGID);
            newPieces.add(Pair.of(newPiece, weight));
            // I'm getting old pool and then add my house and just register it with same name, so it replaces the old one. Hacky but works good
            Registry.register(WorldGenRegistries.TEMPLATE_POOL, pool, new JigsawPattern(pool, old.getName(), newPieces));
        }
    }
}
