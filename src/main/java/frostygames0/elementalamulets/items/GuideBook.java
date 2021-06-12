package frostygames0.elementalamulets.items;

import frostygames0.elementalamulets.items.triggers.ModCriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.patchouli.api.PatchouliAPI;


import javax.annotation.Nullable;
import java.util.List;

import static frostygames0.elementalamulets.ElementalAmulets.modPrefix;

public class GuideBook extends Item {
    public static final ResourceLocation BOOK_ID = modPrefix("guide_book");

    public GuideBook(Properties properties) {
        super(properties);
    }

    public boolean isOpen() {
        return ModList.get().isLoaded("patchouli") && ForgeRegistries.ITEMS.getKey(this).equals(PatchouliAPI.get().getOpenBookGui());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("item.elementalamulets.guide_book.subtitle").mergeStyle(TextFormatting.GRAY));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(playerIn instanceof ServerPlayerEntity) {
            if(ModList.get().isLoaded("patchouli")) {
                PatchouliAPI.get().openBookGUI((ServerPlayerEntity) playerIn, BOOK_ID);
                ModCriteriaTriggers.SUCCESS_USE.trigger((ServerPlayerEntity)playerIn, playerIn.getHeldItem(handIn), (ServerWorld) worldIn, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ());
                return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
            } else {
                playerIn.sendStatusMessage(new TranslationTextComponent("patchouli.elementalamulets.not_present").mergeStyle(TextFormatting.RED), true);
                return ActionResult.resultFail(playerIn.getHeldItem(handIn));
            }
        }
        return ActionResult.resultConsume(playerIn.getHeldItem(handIn));
    }
}
