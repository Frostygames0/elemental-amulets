package frostygames0.elementalamulets.datagen.recipes;

import frostygames0.elementalamulets.core.init.ModBlocks;
import frostygames0.elementalamulets.core.init.ModItems;
import frostygames0.elementalamulets.core.init.ModTags;
import frostygames0.elementalamulets.items.amulets.AmuletItem;
import frostygames0.elementalamulets.recipes.ElementalCombination;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Block;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.Tags;


import java.util.function.Consumer;

import static frostygames0.elementalamulets.ElementalAmulets.modPrefix;


public class RecipeProvider extends net.minecraft.data.RecipeProvider {
    public RecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }


    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        this.registerVanillaRecipes(consumer);
        this.registerModRecipes(consumer);
    }
    // Vanilla recipes(crafting, smelting, etc.)
    private void registerVanillaRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ModItems.ELEMENTAL_COMBINATOR_BLOCK.get())
                .patternLine("_=_")
                .patternLine("#4#")
                .patternLine("000")
                .key('_', Items.RED_CARPET)
                .key('=', Items.IRON_INGOT)
                .key('4', ModItems.ELEMENTAL_SHARDS.get())
                .key('0', Tags.Items.COBBLESTONE)
                .key('#', ItemTags.PLANKS)
                .addCriterion("has_item", InventoryChangeTrigger.Instance.forItems(ModItems.ELEMENTAL_SHARDS.get()))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.GUIDE_BOOK.get())
                .addIngredient(Items.BOOK)
                .addIngredient(ModTags.ELEMENTS)
                .addCriterion("has_item", InventoryChangeTrigger.Instance.forItems(ModItems.ELEMENTAL_SHARDS.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.EMPTY_AMULET.get())
                .patternLine(" ##")
                .patternLine("xx#")
                .patternLine("xx ")
                .key('#', Tags.Items.STRING)
                .key('x', ModItems.ELEMENTAL_SHARDS.get())
                .addCriterion("has_item", InventoryChangeTrigger.Instance.forItems(ModItems.ELEMENTAL_SHARDS.get()))
                .build(consumer);
        oreSmelting(ModBlocks.ELEMENTAL_STONE.get(), ModItems.ELEMENTAL_SHARDS.get(), 0.7f, 200, consumer);
    }
    // Elemental Combination recipes
    private void registerModRecipes(Consumer<IFinishedRecipe> consumer) {
        classicElementRecipe(ModItems.AIR_ELEMENT.get(), ModTags.AIR_ELEMENT_CONVERTIBLE, consumer);
        classicElementRecipe(ModItems.FIRE_ELEMENT.get(), ModTags.FIRE_ELEMENT_CONVERTIBLE, consumer);
        classicElementRecipe(ModItems.WATER_ELEMENT.get(), ModTags.WATER_ELEMENT_CONVERTIBLE, consumer);
        classicElementRecipe(ModItems.EARTH_ELEMENT.get(), ModTags.EARTH_ELEMENT_CONVERTIBLE, consumer);
        ElementalCombinationBuilder.create(ModItems.AETHER_ELEMENT.get())
                .addElemental(ModItems.ELEMENTAL_SHARDS.get())
                .addIngredient(ModItems.FIRE_ELEMENT.get())
                .addIngredient(Items.EMERALD)
                .addIngredient(ModItems.AIR_ELEMENT.get())
                .addIngredient(Items.EMERALD)
                .addIngredient(ModItems.WATER_ELEMENT.get())
                .addIngredient(Items.EMERALD)
                .addIngredient(ModItems.EARTH_ELEMENT.get())
                .addIngredient(Items.EMERALD)
                .setCombinationTime(600)
                .build(consumer);

        amuletRecipeTier1(ModItems.FIRE_AMULET.get().getDefaultInstance(), ModItems.FIRE_ELEMENT.get(), consumer);
        amuletRecipeTier1(ModItems.AIR_AMULET.get().getDefaultInstance(), ModItems.AIR_ELEMENT.get(), consumer);
        amuletRecipeTier1(ModItems.WATER_AMULET.get().getDefaultInstance(), ModItems.WATER_ELEMENT.get(), consumer);

        ElementalCombinationBuilder.create(ModItems.SPEED_AMULET.get().getDefaultInstance())
                .addElemental(ModItems.WATER_AMULET.get().getDefaultInstance())
                .addIngredient(2, Items.ICE)
                .addIngredient(Items.SUGAR)
                .addIngredient(3, Items.ICE)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.ICE)
                .isTagTransferred()
                .setCombinationTime(350)
                .build(consumer, modPrefix("amulets/"+ModItems.SPEED_AMULET.getId().getPath()+"_1"));
        ElementalCombinationBuilder.create(ModItems.JUMP_AMULET.get().getDefaultInstance())
                .addElemental(ModItems.AIR_AMULET.get().getDefaultInstance())
                .addIngredient(2, Items.SLIME_BALL)
                .addIngredient(Items.RABBIT_FOOT)
                .addIngredient(3, Items.SLIME_BALL)
                .addIngredient(Items.RABBIT_FOOT)
                .addIngredient(Items.SLIME_BALL)
                .isTagTransferred()
                .setCombinationTime(350)
                .build(consumer, modPrefix("amulets/"+ModItems.JUMP_AMULET.getId().getPath()+"_1"));
        ElementalCombinationBuilder.create(ModItems.INVISIBILITY_AMULET.get())
                .addElemental(ModItems.EMPTY_AMULET.get())
                .addIngredient(2, ModItems.WATER_ELEMENT.get())
                .addIngredient(Items.LAPIS_LAZULI)
                .addIngredient(3, ModItems.FIRE_ELEMENT.get())
                .addIngredient(Items.LAPIS_LAZULI)
                .addIngredient(ModItems.WATER_ELEMENT.get())
                .setCombinationTime(200)
                .isTagTransferred()
                .build(consumer);
        amuletRecipeSpecial(ModItems.TERRA_PROTECTION_AMULET.get().getDefaultInstance(), Items.SHIELD, ModItems.EARTH_ELEMENT.get(), consumer);

        amuletRecipeTier2(ModItems.FIRE_AMULET.get().getDefaultInstance(), AmuletItem.getStackWithTier(new ItemStack(ModItems.FIRE_AMULET.get()), 2), Items.IRON_INGOT, ModItems.FIRE_ELEMENT.get(), consumer);
        amuletRecipeTier2sPECIAL(ModItems.SPEED_AMULET.get().getDefaultInstance(), AmuletItem.getStackWithTier(new ItemStack(ModItems.SPEED_AMULET.get()), 2), Items.IRON_INGOT, Items.SUGAR, ModItems.WATER_ELEMENT.get(), consumer);
        amuletRecipeTier2(ModItems.TERRA_PROTECTION_AMULET.get().getDefaultInstance(), AmuletItem.getStackWithTier(new ItemStack(ModItems.TERRA_PROTECTION_AMULET.get()), 2), Items.IRON_INGOT, ModItems.EARTH_ELEMENT.get(), consumer);
        amuletRecipeTier2sPECIAL(ModItems.JUMP_AMULET.get().getDefaultInstance(), AmuletItem.getStackWithTier(new ItemStack(ModItems.JUMP_AMULET.get()), 2), Items.IRON_INGOT, Items.SLIME_BALL, ModItems.AIR_ELEMENT.get(), consumer);

        amuletRecipeTier3(AmuletItem.getStackWithTier(new ItemStack(ModItems.FIRE_AMULET.get()), 2), AmuletItem.getStackWithTier(new ItemStack(ModItems.FIRE_AMULET.get()), 3), Items.GHAST_TEAR, Items.GOLD_INGOT, ModItems.FIRE_ELEMENT.get(), consumer);
        amuletRecipeTier3(AmuletItem.getStackWithTier(new ItemStack(ModItems.SPEED_AMULET.get()), 2), AmuletItem.getStackWithTier(new ItemStack(ModItems.SPEED_AMULET.get()), 3), Items.PACKED_ICE, Items.PACKED_ICE, ModItems.WATER_ELEMENT.get(), consumer);
        amuletRecipeTier3(AmuletItem.getStackWithTier(new ItemStack(ModItems.TERRA_PROTECTION_AMULET.get()), 2), AmuletItem.getStackWithTier(new ItemStack(ModItems.TERRA_PROTECTION_AMULET.get()), 3), Items.OBSIDIAN, Items.DIAMOND, ModItems.EARTH_ELEMENT.get(), consumer);
        amuletRecipeTier3(AmuletItem.getStackWithTier(new ItemStack(ModItems.JUMP_AMULET.get()), 2), AmuletItem.getStackWithTier(new ItemStack(ModItems.JUMP_AMULET.get()), 3), Items.SLIME_BLOCK, Items.RABBIT_HIDE, ModItems.AIR_ELEMENT.get(), consumer);

        amuletRecipeTier4(AmuletItem.getStackWithTier(new ItemStack(ModItems.FIRE_AMULET.get()), 3), AmuletItem.getStackWithTier(new ItemStack(ModItems.FIRE_AMULET.get()), 4), ModItems.FIRE_ELEMENT.get(), consumer);
        amuletRecipeTier4(AmuletItem.getStackWithTier(new ItemStack(ModItems.SPEED_AMULET.get()), 3), AmuletItem.getStackWithTier(new ItemStack(ModItems.SPEED_AMULET.get()), 4), ModItems.FIRE_ELEMENT.get(), consumer);
        amuletRecipeTier4(AmuletItem.getStackWithTier(new ItemStack(ModItems.TERRA_PROTECTION_AMULET.get()), 3), AmuletItem.getStackWithTier(new ItemStack(ModItems.TERRA_PROTECTION_AMULET.get()), 4), ModItems.EARTH_ELEMENT.get(), consumer);
        amuletRecipeTier4(AmuletItem.getStackWithTier(new ItemStack(ModItems.JUMP_AMULET.get()), 3), AmuletItem.getStackWithTier(new ItemStack(ModItems.JUMP_AMULET.get()), 4), ModItems.AIR_ELEMENT.get(), consumer);
    }

    /* Helper Methods! */

    private static void amuletRecipeSpecial(ItemStack amulet, IItemProvider specialStack, IItemProvider element, Consumer <IFinishedRecipe> consumer) {
        ElementalCombinationBuilder.create(amulet)
                .addElemental(ModItems.EMPTY_AMULET.get())
                .addIngredient(3, element)
                .addIngredient(Items.LAPIS_LAZULI)
                .addIngredient(element)
                .addIngredient(specialStack)
                .addIngredient(element)
                .addIngredient(Items.LAPIS_LAZULI)
                .setCombinationTime(350)
                .isTagTransferred()
                .build(consumer, modPrefix("amulets/"+amulet.getItem().getRegistryName().getPath()+"_1"));
    }

    private static void amuletRecipeTier1(ItemStack amulet, IItemProvider element, Consumer<IFinishedRecipe> consumer) {
        ElementalCombinationBuilder.create(amulet)
                .addElemental(ModItems.EMPTY_AMULET.get())
                .addIngredient(3, element)
                .addIngredient(Items.LAPIS_LAZULI)
                .addIngredient(3, element)
                .addIngredient(Items.LAPIS_LAZULI)
                .setCombinationTime(200)
                .isTagTransferred()
                .build(consumer, modPrefix("amulets/"+amulet.getItem().getRegistryName().getPath()+"_1"));
    }

    private static void amuletRecipeTier2(ItemStack amulet, ItemStack result, IItemProvider upgrader, IItemProvider element, Consumer<IFinishedRecipe> consumer) {
        ElementalCombinationBuilder.create(result)
                .addElemental(amulet)
                .addIngredient(element)
                .addIngredient(2, upgrader)
                .addIngredient(element)
                .addIngredient(upgrader)
                .addIngredient(element)
                .addIngredient(2, upgrader)
                .setCombinationTime(400)
                .isTagTransferred()
                .build(consumer, modPrefix("amulets/"+result.getItem().getRegistryName().getPath()+"_2"));
    }

    private static void amuletRecipeTier2sPECIAL(ItemStack amulet, ItemStack result, IItemProvider upgrader, IItemProvider upgrader2, IItemProvider element, Consumer<IFinishedRecipe> consumer) {
        ElementalCombinationBuilder.create(result)
                .addElemental(amulet)
                .addIngredient(upgrader)
                .addIngredient(element)
                .addIngredient(upgrader2)
                .addIngredient(element)
                .addIngredient(upgrader)
                .addIngredient(element)
                .addIngredient(upgrader2)
                .addIngredient(element)
                .setCombinationTime(400)
                .isTagTransferred()
                .build(consumer, modPrefix("amulets/"+result.getItem().getRegistryName().getPath()+"_2"));
    }

    private static void amuletRecipeTier3(ItemStack amulet, ItemStack result, IItemProvider upgrader, IItemProvider upgrader2, IItemProvider element, Consumer<IFinishedRecipe> consumer) {
        ElementalCombinationBuilder.create(result)
                .addElemental(amulet)
                .addIngredient(upgrader)
                .addIngredient(element)
                .addIngredient(upgrader2)
                .addIngredient(element)
                .addIngredient(upgrader)
                .addIngredient(element)
                .addIngredient(upgrader2)
                .addIngredient(element)
                .setCombinationTime(400)
                .isTagTransferred()
                .build(consumer, modPrefix("amulets/"+result.getItem().getRegistryName().getPath()+"_3"));
    }

    private static void amuletRecipeTier4(ItemStack amulet, ItemStack result, IItemProvider upgrader, Consumer<IFinishedRecipe> consumer) {
        ElementalCombinationBuilder.create(result)
                .addElemental(amulet)
                .addIngredient(upgrader)
                .addIngredient(ModItems.AETHER_ELEMENT.get())
                .addIngredient(upgrader)
                .addIngredient(ModItems.AETHER_ELEMENT.get())
                .addIngredient(upgrader)
                .addIngredient(ModItems.AETHER_ELEMENT.get())
                .addIngredient(upgrader)
                .addIngredient(ModItems.AETHER_ELEMENT.get())
                .setCombinationTime(800)
                .isTagTransferred()
                .build(consumer, modPrefix("amulets/"+result.getItem().getRegistryName().getPath()+"_4"));
    }

    private static void classicElementRecipe(IItemProvider elementIn, ITag<Item> convertibles, Consumer<IFinishedRecipe> consumerIn) {
        ElementalCombinationBuilder.create(new ItemStack(elementIn, 2))
                .addElemental(ModItems.ELEMENTAL_SHARDS.get())
                .addIngredient(convertibles, ElementalCombination.MAX_INGREDIENTS)
                .build(consumerIn, modPrefix("elements/"+elementIn.asItem().getRegistryName().getPath()));
    }

    private static void oreSmelting(Block oreBlock, Item resultOre, float exp, int cookTime, Consumer<IFinishedRecipe> consumerIn) {
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(oreBlock),
                resultOre, exp, cookTime)
                .addCriterion("has_item", InventoryChangeTrigger.Instance.forItems(oreBlock))
                .build(consumerIn, resultOre.getRegistryName());
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(oreBlock),
                resultOre, exp, cookTime/2)
                .addCriterion("has_item", InventoryChangeTrigger.Instance.forItems(oreBlock))
                .build(consumerIn, resultOre.getRegistryName()+"_from_blasting");
    }
}
