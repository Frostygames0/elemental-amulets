package frostygames0.elementalamulets.items;

import frostygames0.elementalamulets.config.ModConfig;
import frostygames0.elementalamulets.items.interfaces.IJumpItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class JumpAmulet extends AbstractAmuletItem implements IJumpItem {
    public JumpAmulet(Properties properties, int tier) {
        super(properties, tier);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("item.elementalamulets.jump_amulet.tooltip", TextFormatting.GRAY, TextFormatting.GREEN));
    }

    @Override
    public void curioBreak(ItemStack stack, LivingEntity livingEntity) {
        super.curioBreak(stack, livingEntity);
    }

    @Override
    public float getJump() {
        return 0.3f*getTier();
    }

    @Override
    public float getFallResist() {
        return getJump()*10-1f;
    }

    @Override
    public int getDamageOnUse() {
        return ModConfig.cached.JUMP_AMULET_USAGE_DMG*getTier();
    }

}
