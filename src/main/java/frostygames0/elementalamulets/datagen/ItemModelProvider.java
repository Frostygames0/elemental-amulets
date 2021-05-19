package frostygames0.elementalamulets.datagen;

import frostygames0.elementalamulets.ElementalAmulets;
import frostygames0.elementalamulets.core.init.ModItems;
import frostygames0.elementalamulets.items.amulets.AmuletItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ElementalAmulets.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for(RegistryObject<Item> reg : ModItems.ITEMS.getEntries()) {
            Item item = reg.get();
            if(item instanceof AmuletItem) {
                amuletTexture((AmuletItem) item);
            } else {
                if(item instanceof BlockItem) continue; // BlockItems should be ignored since their models are generated by BlockStateProvider
                singleTexture(name(item), new ResourceLocation("item/generated"), "layer0", modLoc("item/"+name(item)));
            }
        }

    }

    private void amuletTexture(AmuletItem item) {
        if(item.getTier() > 0) {
            withExistingParent(name(item), "item/generated")
                    .texture("layer0", modLoc("item/" + removeTier(item)))
                    .texture("layer1", modLoc("item/amulet_tiers/level" + item.getTier()))
                    .override().predicate(mcLoc("damage"), 0.20f).model(withExistingParent("item/damaged_variations/tier" + item.getTier() + "/" + removeTier(item) + "_damage1", modLoc("item/" + name(item))).texture("layer0", modLoc("item/" + removeTier(item) + "_damage1"))).end()
                    .override().predicate(mcLoc("damage"), 0.40f).model(withExistingParent("item/damaged_variations/tier" + item.getTier() + "/" + removeTier(item) + "_damage2", modLoc("item/" + name(item))).texture("layer0", modLoc("item/" + removeTier(item) + "_damage2"))).end()
                    .override().predicate(mcLoc("damage"), 0.60f).model(withExistingParent("item/damaged_variations/tier" + item.getTier() + "/" + removeTier(item) + "_damage3", modLoc("item/" + name(item))).texture("layer0", modLoc("item/" + removeTier(item) + "_damage3"))).end()
                    .override().predicate(mcLoc("damage"), 0.80f).model(withExistingParent("item/damaged_variations/tier" + item.getTier() + "/" + removeTier(item) + "_damage4", modLoc("item/" + name(item))).texture("layer0", modLoc("item/" + removeTier(item) + "_damage4"))).end();
        }
    }

    private String name(Item item) {
        return item.getRegistryName().getPath();
    }

    private String removeTier(AmuletItem item) {
        String s = item.getRegistryName().getPath();
        int a = s.indexOf("tier");
        if(a > -1) return s.substring(0, a-1);
        return s;
    }
}
