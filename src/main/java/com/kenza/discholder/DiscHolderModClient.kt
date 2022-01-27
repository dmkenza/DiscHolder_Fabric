package com.kenza.discholder

import com.kenza.discholder.block.DiscHolderBlockEntityRenderer
import com.kenza.discholder.common.IRInventoryScreen
import com.kenza.discholder.common.UpdateDiscHolderPacket
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry

class DiscHolderModClient : ClientModInitializer {
    override fun onInitializeClient() {



        DiscHolderMod.mapEntitiesTypes.values.map { type ->
            BlockEntityRendererRegistry.register(type) {
                DiscHolderBlockEntityRenderer()
            }
        }
        ScreenRegistry.register(DiscHolderMod.DISC_BLOCKENTITY_GUI_HANDLER_TYPE) { controller, inv, _ -> IRInventoryScreen(controller, inv.player) }


        UpdateDiscHolderPacket.register()

    }
}