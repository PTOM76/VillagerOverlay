package net.mymai1208.villageroverlay.mixin.impl;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.MerchantEntity;
import net.mymai1208.villageroverlay.RenderUtil;
import net.mymai1208.villageroverlay.VillagerOverlay;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow @Final private EntityRenderDispatcher entityRenderDispatcher;

    @Inject(method = "renderEntity", at = @At("HEAD"))
    public void renderEntity(Entity entity, double d, double e, double f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, CallbackInfo ci) {
        if(entity instanceof MerchantEntity merchantEntity) {
            if(!merchantEntity.getOffers().isEmpty() && VillagerOverlay.getOpenedVillagers().contains(merchantEntity.getUuid())) {
                RenderUtil.renderVillagerOverlay(merchantEntity, d, e, f, g, matrixStack, vertexConsumerProvider, entityRenderDispatcher.getLight(merchantEntity, 1));
            }
        }
    }
}
