package com.kenza.discholder

import com.kenza.discholder.utils.value
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3f

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

        val facing: Direction = blockState.getOrEmpty(Properties.HORIZONTAL_FACING).value ?: return

        val isXAxis = facing.axis === Direction.Axis.X
        for (i in 0..6) {
            val item: ItemStack =  player.mainHandStack //entity.records.getStackInSlot(i)
            if (item.isEmpty) continue
            shiftX = if (isXAxis) .5 - .03125 else .125 + .125 * i
            shiftY = .375
            shiftZ = if (isXAxis) .125 + .125 * i else .5 + .03125


            matrices?.push() ?: return
//            matrices.translate( shiftX,  shiftY,  shiftZ)
            matrices.translate( shiftX,  shiftY,  shiftZ)

//            matrices.translate( 0.5,  0.5,  0.5)

            if (!isXAxis) matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90f))

//            matrices.translate( -0.5,  -0.5,  -0.5)

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
