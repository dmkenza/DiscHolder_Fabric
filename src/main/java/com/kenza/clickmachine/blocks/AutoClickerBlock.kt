package com.kenza.clickmachine.blocks

import com.kenza.clickmachine.common.IRScreenHandlerFactory
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.BlockRotation
import net.minecraft.util.Hand
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

open class AutoClickerBlock(
    settings: Settings,
    val screenHandler: ((Int, PlayerInventory, ScreenHandlerContext) -> ScreenHandler)?
) : BlockWithEntity(settings) {

    override fun getPlacementState(ctx: ItemPlacementContext?): BlockState? {
        super.getPlacementState(ctx)
        return this.defaultState.with(HORIZONTAL_FACING, ctx?.playerFacing?.opposite)//.with(ACTIVE, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        super.appendProperties(builder)
        builder?.add(HORIZONTAL_FACING)
        builder?.add(ACTIVE)
    }

    override fun rotate(state: BlockState, rotation: BlockRotation): BlockState {
        return state.with(HORIZONTAL_FACING, getRotated(state[HORIZONTAL_FACING], rotation))
    }

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hitResult: BlockHitResult
    ): ActionResult {

        screenHandler?.let {
            player.openHandledScreen(IRScreenHandlerFactory(screenHandler, pos!!))
        }

        return ActionResult.SUCCESS
    }

    override fun onPlaced(
        world: World?,
        pos: BlockPos?,
        state: BlockState?,
        placer: LivingEntity?,
        itemStack: ItemStack?
    ) {
        (world?.getBlockEntity(pos) as? AutoClickerBlockEntity)?.apply {
            placerEntityUuid = placer?.uuid
            this.markDirty()
        }
        super.onPlaced(world, pos, state, placer, itemStack)
    }


    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? {
        return AutoClickerBlockEntity(pos, state)
    }

    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun <T : BlockEntity?> getTicker(
        world: World,
        state: BlockState?,
        type: BlockEntityType<T>?
    ): BlockEntityTicker<T>? {

        return if (world.isClient)
            BlockEntityTicker { _, _, _, blockEntity -> (blockEntity as? AutoClickerBlockEntity)?.clientTick() }
        else
            BlockEntityTicker { _, _, _, blockEntity -> (blockEntity as? AutoClickerBlockEntity)?.tick(getFacing(state!!)) }

    }

    @Suppress("DEPRECATION")
    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        val oldBlockEntity = world.getBlockEntity(pos) as? AutoClickerBlockEntity
        super.onStateReplaced(state, world, pos, newState, moved)
        if (world.isClient) return

        if (oldBlockEntity?.items?.isNotEmpty() == true) {
            ItemScatterer.spawn(world, pos, oldBlockEntity.items)
            world.updateComparators(pos, this)
        }
    }


    fun getFacing(state: BlockState): Direction = state[HORIZONTAL_FACING]

    companion object {
        val HORIZONTAL_FACING: DirectionProperty = Properties.HORIZONTAL_FACING
        val ACTIVE: BooleanProperty = BooleanProperty.of("active");

        fun getRotated(direction: Direction, rotation: BlockRotation): Direction =
            if (direction.axis.isVertical) direction else when (rotation) {
                BlockRotation.NONE -> direction
                BlockRotation.CLOCKWISE_90 -> direction.rotateYClockwise()
                BlockRotation.CLOCKWISE_180 -> direction.opposite
                BlockRotation.COUNTERCLOCKWISE_90 -> direction.rotateYCounterclockwise()
            }
    }
}