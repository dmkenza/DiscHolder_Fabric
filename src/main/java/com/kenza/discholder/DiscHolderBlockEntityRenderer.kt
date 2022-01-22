package com.kenza.discholder

import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.util.math.MatrixStack

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



        MinecraftClient.getInstance().itemRenderer.renderItem(
            player.mainHandStack,
            ModelTransformation.Mode.FIXED,
            light,
            overlay,
            matrices,
            vertexConsumers,
            0
        )
    }
}
