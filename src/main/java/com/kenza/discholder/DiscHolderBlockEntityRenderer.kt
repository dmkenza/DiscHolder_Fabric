package com.kenza.discholder

import com.kenza.discholder.utils.getSlotInBlock
import com.kenza.discholder.utils.toVec3d
import com.kenza.discholder.utils.value
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.state.property.Properties
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Matrix4f
import net.minecraft.util.math.Quaternion
import net.minecraft.util.math.Vec3f

class DiscHolderBlockEntityRenderer : BlockEntityRenderer<DiscHolderBlockEntity> {

    private val mc: MinecraftClient
        get() = MinecraftClient.getInstance()

    override fun render(
        entity: DiscHolderBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int
    ) {
        val player = mc.server?.playerManager?.playerList?.firstOrNull() ?: return

        player.mainHandStack ?: return


        val blockState = MinecraftClient.getInstance().world?.getBlockState(entity.pos) ?: return

        val facing: Direction = blockState.getOrEmpty(Properties.HORIZONTAL_FACING).value ?: return

        val isXAxis = facing.axis === Direction.Axis.X
        for (i in 0..6) {
            val item: ItemStack = player.mainHandStack //entity.records.getStackInSlot(i)
            if (item.isEmpty) continue
            val shiftX = if (isXAxis) .5 - .03125 else .125 + .125 * i
            val shiftY = .375
            val shiftZ = if (isXAxis) .125 + .125 * i else .5 + .03125

            matrices.push()
            matrices.translate(shiftX, shiftY, shiftZ)

            if (!isXAxis) matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90f))


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

        val slot = getSlot(entity, facing)

        if (slot != -1) {
            renderText(matrices, slot)
        }

    }

    private fun getSlot(
        entity: DiscHolderBlockEntity,
        facing: Direction,
    ): Int {

        val target = mc.crosshairTarget ?: return -1
        return if (target.type == HitResult.Type.BLOCK && target.pos.isInRange(
                entity.pos.toVec3d().add(0.5, 0.5, 0.5),
                0.5
            )
        ) {

            val inc =
                if (facing === Direction.NORTH || facing === Direction.SOUTH) target.pos.x % 1 else target.pos.z % 1
            getSlotInBlock(inc)

        } else {
            -1
        }

    }

    private fun renderText(
        matrices: MatrixStack,
        slot: Int
    ) {

        matrices.push()

        val shiftX = .125 + .125 * slot
        val shiftY = .375
        val shiftZ = .5 + .03125

        val renderManager = MinecraftClient.getInstance().entityRenderDispatcher

        matrices.translate(shiftX, shiftY + .7f, shiftZ)

        val immediate = mc.bufferBuilders.effectVertexConsumers


        val rotation: Quaternion = renderManager.camera.getRotation().copy()
        rotation.scale(-1.0f)
        matrices.multiply(rotation)

        matrices.scale(-0.025f, -0.025f, 0.025f)

        val time1 = "Rainbow Dash $slot"

        val offset = (-mc.textRenderer.getWidth(time1) / 2).toFloat()

        val modelViewMatrix: Matrix4f = matrices.peek().positionMatrix


        mc.textRenderer.draw(
            time1,
            offset,
            0f,
            553648127,
            false,
            modelViewMatrix,
            immediate,
            true,
            1056964608,
            15728640
        )
        mc.textRenderer.draw(time1, offset, 0f, -1, false, modelViewMatrix, immediate, true, 0, 15728640)


        matrices.pop()
    }
}
