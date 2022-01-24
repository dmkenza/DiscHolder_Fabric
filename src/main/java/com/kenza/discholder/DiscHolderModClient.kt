package com.kenza.discholder

import com.kenza.discholder.common.IRInventoryScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry

class DiscHolderModClient : ClientModInitializer {
    override fun onInitializeClient() {

        ScreenRegistry.register(DiscHolderMod.DISC_BLOCKENTITY_GUI_HANDLER_TYPE) { controller, inv, _ -> IRInventoryScreen(controller, inv.player) }

    }
}