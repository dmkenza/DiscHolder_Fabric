package com.kenza.discholder.block

import com.kenza.discholder.DiscHolderMod
import com.kenza.discholder.common.IRScreenHandlerFactory
import com.kenza.discholder.utils.getSlotInBlock
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.gui.screen.Screen
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.item.MusicDiscItem
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.BlockRotation
import net.minecraft.util.Hand
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.event.listener.GameEventListener

class DiscHolderBlock(
    settings: Settings?,
    val screenHandler: ((Int, PlayerInventory, ScreenHandlerContext) -> ScreenHandler)?,
) : BlockWithEntity(settings) {


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


    override fun <T : BlockEntity?> getTicker(
        world: World,
        state: BlockState?,
        type: BlockEntityType<T>?
    ): BlockEntityTicker<T>? {

        return BlockEntityTicker { _, _, _, blockEntity -> (blockEntity as? DiscHolderBlockEntity)?.tick() }

    }

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hitResult: BlockHitResult
    ): ActionResult {
//        if (world.isClient) return ActionResult.CONSUME


//            if (!world.isClient) {
        val vec3d: Vec3d = hitResult.pos
        val facing: Direction = state[HORIZONTAL_FACING]
        val inc = if (facing === Direction.NORTH || facing === Direction.SOUTH) vec3d.x % 1 else vec3d.z % 1

        val slot: Int = getSlotInBlock(inc)
//                val heldItem: ItemStack = player.mainHandStack
        val blockEntity: DiscHolderBlockEntity? = world.getBlockEntity(pos) as? DiscHolderBlockEntity

        val itemStackInSlot = blockEntity?.items?.getOrNull(slot)

        if (slot != -1) {

            if (itemStackInSlot?.isEmpty == true && player.mainHandStack.hasMusicDiscItemType()) {
                blockEntity.items.set(slot, player.mainHandStack.copy())
                player.mainHandStack.decrement(1)
                blockEntity.markDirty()
            } else if (itemStackInSlot.hasMusicDiscItemType()) {

//                    val itemEntity = ItemEntity(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), itemStack)
                player.inventory.offerOrDrop(itemStackInSlot);
                itemStackInSlot?.decrement(1)
//                    if (!player.giveItemStack(itemStack)) {
//                        player.dropItem(itemStack?.copy(), false)
//                        itemStack?.decrement(1)
//                    }

//                    world.spawnEntity(itemEntity)
            } else {
                screenHandler?.let {
                    player.openHandledScreen(IRScreenHandlerFactory(screenHandler, pos!!))
                }

            }

//            ItemScatterer.spawn(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), player.mainHandStack)
        }else{
            screenHandler?.let {
                player.openHandledScreen(IRScreenHandlerFactory(screenHandler, pos!!))
            }
        }


//        HopperBlock

//        screenHandler?.let {
//            player.openHandledScreen(IRScreenHandlerFactory(screenHandler, pos!!))
//        }

        return ActionResult.SUCCESS
    }

    @Suppress("DEPRECATION")
    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        val oldBlockEntity = world.getBlockEntity(pos) as? DiscHolderBlockEntity
        super.onStateReplaced(state, world, pos, newState, moved)
//        if (world.isClient) return

        if (oldBlockEntity?.items?.isNotEmpty() == true) {
            ItemScatterer.spawn(world, pos, oldBlockEntity.items)
            world.updateComparators(pos, this)
        }
    }



    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? {
        val keyType = this.translationKey.split(".").lastOrNull()
        val type = DiscHolderMod.mapEntitiesTypes.get(keyType)

        return DiscHolderBlockEntity(type, pos, state)
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


//    override fun neighborUpdate(
//        state: BlockState?,
//        world: World?,
//        pos: BlockPos?,
//        block: Block?,
//        fromPos: BlockPos?,
//        notify: Boolean
//    ) {
//        debug("asd")
//    }


    override fun <T : BlockEntity?> getGameEventListener(world: World?, blockEntity: T): GameEventListener? {
        return super.getGameEventListener(world, blockEntity)
    }

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

fun ItemStack?.hasMusicDiscItemType(): Boolean {
    return (this?.item is MusicDiscItem)
}
