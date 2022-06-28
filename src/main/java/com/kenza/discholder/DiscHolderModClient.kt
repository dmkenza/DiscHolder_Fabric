package com.kenza.discholder

import com.kenza.discholder.block.DiscHolderBlockEntityRenderer
import com.kenza.discholder.common.IRInventoryScreen
import com.kenza.discholder.common.UpdateDiscHolderPacket
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.minecraft.client.gui.screen.ingame.HandledScreens

class DiscHolderModClient : ClientModInitializer {
    override fun onInitializeClient() {



        DiscHolderMod.mapEntitiesTypes.values.map { type ->
            BlockEntityRendererRegistry.register(type) {
                DiscHolderBlockEntityRenderer()
            }
        }
        HandledScreens.register(DiscHolderMod.DISC_BLOCKENTITY_GUI_HANDLER_TYPE) { controller, inv, _ -> IRInventoryScreen(controller, inv.player) }


        UpdateDiscHolderPacket.register()

    }
}