package frostygames0.elementalamulets.items.amulets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import frostygames0.elementalamulets.ElementalAmulets;
import frostygames0.elementalamulets.advancements.triggers.ModCriteriaTriggers;
import frostygames0.elementalamulets.client.models.AmuletModel;
import frostygames0.elementalamulets.core.init.ModStats;
import frostygames0.elementalamulets.core.util.NBTUtil;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class AmuletItem extends Item implements ICurioItem {
    public static final String TIER_TAG = ElementalAmulets.MOD_ID+":tier";
    public static final int MAX_TIER = 4;

    private final boolean hasTier;
    
    public AmuletItem(Properties properties, boolean hasTier) {
        super(properties);
        this.hasTier = hasTier;
    }

    public AmuletItem(Properties properties) {
        this(properties, true);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if(this.getTier(stack) > 0) tooltip.add(new TranslationTextComponent("item.elementalamulets.common_amulet.tooltip.tier", new StringTextComponent(String.valueOf(this.getTier(stack))).withStyle(TextFormatting.YELLOW)).withStyle(TextFormatting.GOLD));
        tooltip.add(new TranslationTextComponent(getOrCreateDescriptionId()+".tooltip").withStyle(TextFormatting.GRAY));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 1000 * this.getTier(stack);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(prevStack.getItem() != stack.getItem()) {
            LivingEntity entity = slotContext.getWearer();
            if(!entity.level.isClientSide() && entity instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = (ServerPlayerEntity) entity;

                ModCriteriaTriggers.SUCCESS_USE.trigger(player, stack);
                player.awardStat(ModStats.AMULET_WORN_STAT);
            }
        }
    }

    @Override
    public ItemStack getDefaultInstance() {
        return getStackWithTier(new ItemStack(this), 1);
    }

    public static ItemStack getStackWithTier(ItemStack stack, int tier) {
        if(stack.getItem() instanceof AmuletItem) {
            if(((AmuletItem) stack.getItem()).hasTier) {
                NBTUtil.putInteger(stack, TIER_TAG, MathHelper.clamp(tier, 1, MAX_TIER));
            }
        }
        return stack;
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if(!this.hasTier) super.fillItemCategory(group, items);
        else {
            if (allowdedIn(group)) {
                for (int i = 1; i <= MAX_TIER; ++i) {
                    ItemStack stack = new ItemStack(this);
                    items.add(getStackWithTier(stack, i));
                }
            }
        }
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        return true;
    }

    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        matrixStack.pushPose();
        ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
        ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);

        matrixStack.scale(0.7f, 0.7f, 0.7f); // Makes amulet smaller gugugaga

        AmuletModel amuletModel = new AmuletModel();
        IVertexBuilder vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, amuletModel.renderType(AmuletModel.getTexture(this, stack)), false, stack.hasFoil());
        amuletModel.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1f,1f);
    }

    public int getTier(ItemStack stack) {
        if(this.hasTier()) {
            return NBTUtil.getInteger(stack, TIER_TAG);
        }
        return 0;
    }

    public boolean hasTier() {
        return this.hasTier;
    }

    /**
     * Defines if amulet implements it's action outside of {@link ICurioItem} methods such as {@link frostygames0.elementalamulets.items.amulets.effect.JumpAmuletEffect}
     * @return true - if it uses something different for effect(e.g forge events), false - if not
     */
    public boolean hasSpecialEffect() {
        return false;
    }


}
