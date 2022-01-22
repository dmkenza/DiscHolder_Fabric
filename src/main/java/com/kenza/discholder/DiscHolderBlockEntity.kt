package com.kenza.discholder

import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.screen.NamedScreenHandlerFactory

class DiscHolderBlockEntity(pos: BlockPos?, state: BlockState?) :
    BlockEntity(DiscHolderMod.DISC_BLOCKENTITY_TYPE, pos, state) {

}