package com.kenza.discholder

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem.enableBlend
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockBox.rotated
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Quaternion

class DiscHolderBlockEntityRenderer : BlockEntityRenderer<DiscHolderBlockEntity> {
    override fun render(
        entity: DiscHolderBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack?,
        vertexConsumers: VertexConsumerProvider?,
        light: Int,
        overlay: Int
    ) {

        val player = MinecraftClient.getInstance().server?.playerManager?.playerList?.firstOrNull() ?: return

        player.mainHandStack ?: return

        var shiftX: Double
        var shiftY: Double
        var shiftZ: Double

        val blockState = MinecraftClient.getInstance().world?.getBlockState(entity.pos) ?: return

        val facing: Direction = blockState[Properties.HORIZONTAL_FACING]

        val isXAxis = facing.getAxis() === Direction.Axis.X
        for (i in 0..6) {
            val item: ItemStack =  player.mainHandStack //entity.records.getStackInSlot(i)
            if (item.isEmpty) continue
            shiftX = if (isXAxis) .5 - .03125 else .125 + .125 * i
            shiftY = .375
            shiftZ = if (isXAxis) .125 + .125 * i else .5 + .03125


            matrices?.push() ?: return
            matrices.translate( shiftX,  shiftY,  shiftZ)

            if (!isXAxis) matrices.multiply(Quaternion(90f, 0.0f, 1.0f, 0.0f))

            MinecraftClient.getInstance().itemRenderer.renderItem(
                player.mainHandStack,
                ModelTransformation.Mode.FIXED,
                light,
                overlay,
                matrices,
                vertexConsumers,
                0
            )
            matrices.pop()
        }



    }
}
