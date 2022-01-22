package com.kenza.discholder

import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.HopperBlockEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.item.MusicDiscItem
import net.minecraft.state.StateManager
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.BlockRotation
import net.minecraft.util.Hand
import net.minecraft.util.ItemScatterer
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World

class DiscHolderBlock(settings: Settings?) : BlockWithEntity(settings) {


    val SHAPE: VoxelShape = createCuboidShape(0.01, 0.01, .01, 16.0, 6.0, 16.0)


    override fun getOutlineShape(
        state: BlockState,
        view: BlockView,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape {
        return SHAPE
    }

    override fun getPlacementState(ctx: ItemPlacementContext?): BlockState? {
        super.getPlacementState(ctx)
        return this.defaultState.with(HORIZONTAL_FACING, ctx?.playerFacing?.opposite)//.with(ACTIVE, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        super.appendProperties(builder)
        builder?.add(HORIZONTAL_FACING)
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


        if (!world.isClient) {
            val vec3d: Vec3d = hitResult.pos
            val facing: Direction = state[HORIZONTAL_FACING]
            val inc = if (facing === Direction.NORTH || facing === Direction.SOUTH) vec3d.x % 1 else vec3d.z % 1

//            val slot: Int = getSlot(inc)
//            if (slot != -1) {
            val heldItem: ItemStack = player.mainHandStack
            val blockEntity: DiscHolderBlockEntity? = world.getBlockEntity(pos) as? DiscHolderBlockEntity

//            if (blockEntity is DiscHolderBlockEntity) {
                val discHolder = blockEntity as? DiscHolderBlockEntity
//                if (heldItem.item is MusicDiscItem && discHolder.records.getStackInSlot(slot).isEmpty()) {
//                    discHolder.records.setStackInSlot(slot, heldItem.copy())
//                    player.getHeldItem(handIn).shrink(1)
//                } else if (heldItem.isEmpty && !discHolder.records.getStackInSlot(slot).isEmpty()) {
//                    val record: ItemStack = discHolder.records.extractItem(slot, 64, false)
//                    world.addEntity(ItemEntity(world, player.posX, pos.y + .5, player.posZ, record))
                val list = DefaultedList.ofSize(1, ItemStack.EMPTY)
//                list.add(heldItem)
                ItemScatterer.spawn(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), player.mainHandStack)

//                }
//            }
//            }

        }

//        HopperBlock

//        screenHandler?.let {
//            player.openHandledScreen(IRScreenHandlerFactory(screenHandler, pos!!))
//        }

        return ActionResult.SUCCESS
    }

//    override fun onPlaced(
//        world: World?,
//        pos: BlockPos?,
//        state: BlockState?,
//        placer: LivingEntity?,
//        itemStack: ItemStack?
//    ) {
////        (world?.getBlockEntity(pos) as? AutoClickerBlockEntity)?.apply {
////            placerEntityUuid = placer?.uuid
////            this.markDirty()
////        }
//        super.onPlaced(world, pos, state, placer, itemStack)
//    }


    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? {
        return DiscHolderBlockEntity(pos, state)
    }

    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }


//
//    override fun <T : BlockEntity?> getTicker(
//        world: World,
//        state: BlockState?,
//        type: BlockEntityType<T>?
//    ): BlockEntityTicker<T>? {
//
//        return if (world.isClient)
//            BlockEntityTicker { _, _, _, blockEntity -> (blockEntity as? AutoClickerBlockEntity)?.clientTick() }
//        else
//            BlockEntityTicker { _, _, _, blockEntity -> (blockEntity as? AutoClickerBlockEntity)?.tick(getFacing(state!!)) }
//
//    }

//    @Suppress("DEPRECATION")
//    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
////        val oldBlockEntity = world.getBlockEntity(pos) as? AutoClickerBlockEntity
////        super.onStateReplaced(state, world, pos, newState, moved)
////        if (world.isClient) return
////
////        if (oldBlockEntity?.items?.isNotEmpty() == true) {
////            ItemScatterer.spawn(world, pos, oldBlockEntity.items)
////            world.updateComparators(pos, this)
////        }
//    }


    fun getFacing(state: BlockState): Direction = state[HORIZONTAL_FACING]

    companion object {
        val HORIZONTAL_FACING: DirectionProperty = Properties.HORIZONTAL_FACING

        fun getRotated(direction: Direction, rotation: BlockRotation): Direction =
            if (direction.axis.isVertical) direction else when (rotation) {
                BlockRotation.NONE -> direction
                BlockRotation.CLOCKWISE_90 -> direction.rotateYClockwise()
                BlockRotation.CLOCKWISE_180 -> direction.opposite
                BlockRotation.COUNTERCLOCKWISE_90 -> direction.rotateYCounterclockwise()
            }
    }
}