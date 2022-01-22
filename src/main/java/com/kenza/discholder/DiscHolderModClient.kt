package com.kenza.discholder

import com.kenza.discholder.DiscHolderMod.Companion.DISC_BLOCK
import com.kenza.discholder.DiscHolderMod.Companion.DISC_BLOCKENTITY_TYPE
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry
import net.minecraft.block.entity.BlockEntityType

class DiscHolderModClient : ClientModInitializer {
    override fun onInitializeClient() {

        DISC_BLOCKENTITY_TYPE = BlockEntityType.Builder.create(::DiscHolderBlockEntity, DISC_BLOCK).build(null)

        BlockEntityRendererRegistry.register(DISC_BLOCKENTITY_TYPE){
            DiscHolderBlockEntityRenderer()
        }
    }
}