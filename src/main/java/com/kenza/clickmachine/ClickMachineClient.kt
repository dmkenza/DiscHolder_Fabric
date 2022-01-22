package com.kenza.clickmachine

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import com.kenza.clickmachine.common.IRInventoryScreen

class ClickMachineClient : ClientModInitializer {
    override fun onInitializeClient() {

//        ScreenRegistry.register<AutoClickerGuiDescription, CottonInventoryScreen<AutoClickerGuiDescription>>(
//            GUI_SCREEN_HANDLER_TYPE
//        ) { description: AutoClickerGuiDescription?, inventory: PlayerInventory?, title: Text? ->
//            CottonInventoryScreen(
//                description,
//                inventory,
//                title
//            )
//        }

        ScreenRegistry.register(ClickMachine.GUI_SCREEN_HANDLER_TYPE) { controller, inv, _ -> IRInventoryScreen(controller, inv.player) }


    }
}