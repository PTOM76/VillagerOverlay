package net.mymai1208.villageroverlay

import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.model.json.ModelTransformationMode
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.passive.MerchantEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.RotationAxis

object RenderUtil {
    @JvmStatic
    fun renderVillagerOverlay(merchantEntity: MerchantEntity, x: Double, y: Double, z: Double, tickDelta: Double, matrixStack: MatrixStack, vertexConsumerProvider: VertexConsumerProvider, lightLevel: Int) {
        val offers = merchantEntity.offers

        val index = (offers.size * (System.currentTimeMillis() % 4000) / 4000)

        val offer = offers.getOrNull(index.toInt()) ?: offers[0]
        val sellItem = offer.sellItem
        val buyItem = if(!offer.originalFirstBuyItem.isEmpty) offer.originalFirstBuyItem else offer.secondBuyItem

        val camX = MathHelper.lerp(tickDelta, merchantEntity.lastRenderX, merchantEntity.x) - x
        val camY = MathHelper.lerp(tickDelta, merchantEntity.lastRenderY, merchantEntity.y) - y
        val camZ = MathHelper.lerp(tickDelta, merchantEntity.lastRenderZ, merchantEntity.z) - z

        var offsetY = 1 + (if (merchantEntity.isSleeping) 0.0 else 1.5)

        //取引アイテムの描画
        matrixStack.push()

        matrixStack.translate(camX, camY + offsetY, camZ)
        matrixStack.push()

        matrixStack.translate(-0.3, 0.0, 0.0)

        matrixStack.scale(0.5f, 0.5f, 0.5f)

        renderItem(buyItem, matrixStack, vertexConsumerProvider, lightLevel, tickDelta)

        matrixStack.pop()

        matrixStack.push()

        matrixStack.translate(0.3, 0.0, 0.0)

        matrixStack.scale(0.5f, 0.5f, 0.5f)
        renderItem(sellItem, matrixStack, vertexConsumerProvider, lightLevel, tickDelta)

        matrixStack.pop()

        matrixStack.pop()

        offsetY += 1
    }

    private fun renderItem(itemStack: ItemStack, matrixStack: MatrixStack, vertexConsumerProvider: VertexConsumerProvider, lightLevel: Int, tickDelta: Double, isRotation: Boolean = true) {
        if(isRotation) {
            val rotation = (360f * (System.currentTimeMillis() % 4000) / 4000) - tickDelta.toFloat()
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation))
        }

        MinecraftClient.getInstance().itemRenderer.renderItem(
            itemStack,
            ModelTransformationMode.FIXED,
            lightLevel,
            OverlayTexture.DEFAULT_UV,
            matrixStack,
            vertexConsumerProvider,
            null,
            0
        )
    }
}