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

package frostygames0.elementalamulets.datagen;

import frostygames0.elementalamulets.ElementalAmulets;
import frostygames0.elementalamulets.core.init.ModItems;
import frostygames0.elementalamulets.core.init.ModTags;
import frostygames0.elementalamulets.items.amulets.AmuletItem;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;


import javax.annotation.Nullable;

public class TagProvider extends ItemTagsProvider {
    public TagProvider(DataGenerator dataGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, new BlockTagsProvider(dataGenerator, ElementalAmulets.MOD_ID, existingFileHelper), ElementalAmulets.MOD_ID, existingFileHelper);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void addTags() {
        //Amulets
        AmuletItem[] amulets = ModItems.getAmulets().toArray(new AmuletItem[0]);
        this.tag(ModTags.NECKLACES).add(amulets);

        // Curios tag
        this.tag(ItemTags.bind("curios:necklace")).addTag(ModTags.NECKLACES);
        this.tag(ItemTags.bind("curios:belt")).add(ModItems.AMULET_BELT.get());

        // Elements
        this.tag(ModTags.ELEMENTS).add(ModItems.AIR_ELEMENT.get(), ModItems.EARTH_ELEMENT.get(), ModItems.FIRE_ELEMENT.get(), ModItems.WATER_ELEMENT.get(), ModItems.AETHER_ELEMENT.get(), ModItems.ELEMENTAL_SHARDS.get());
        // Fire
        this.tag(ModTags.FIRE_ELEMENT_CONVERTIBLE).add(Items.BLAZE_POWDER, Items.NETHERITE_SCRAP, Items.LAVA_BUCKET, Items.FIRE_CHARGE);
        // Water
        this.tag(ModTags.WATER_ELEMENT_CONVERTIBLE).add(Items.WATER_BUCKET, Items.WET_SPONGE,
                Items.PRISMARINE, Items.PRISMARINE_BRICK_SLAB, Items.PRISMARINE_BRICK_STAIRS,
                Items.PRISMARINE_BRICKS, Items.PRISMARINE_SLAB, Items.PRISMARINE_STAIRS,
                Items.PRISMARINE_CRYSTALS, Items.PRISMARINE_SHARD, Items.PRISMARINE_WALL,
                Items.DARK_PRISMARINE, Items.DARK_PRISMARINE_SLAB, Items.DARK_PRISMARINE_STAIRS );
        // Air
        this.tag(ModTags.AIR_ELEMENT_CONVERTIBLE).add(Items.FEATHER).addTags(ItemTags.SAND, ItemTags.WOOL);
        // Earth
        this.tag(ModTags.EARTH_ELEMENT_CONVERTIBLE).add(Items.DIRT, Items.COARSE_DIRT, Items.GRASS_BLOCK).addTags(ItemTags.FLOWERS, ItemTags.LEAVES, ItemTags.LOGS).addTags(Tags.Items.SEEDS, Tags.Items.CROPS);
    }
}
