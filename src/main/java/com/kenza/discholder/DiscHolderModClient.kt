package com.kenza.discholder

import com.kenza.discholder.DiscHolderMod.Companion.DISC_BLOCKENTITY_GUI_HANDLER_TYPE
import com.kenza.discholder.block.DiscHolderBlockEntityGuiDescription
import com.kenza.discholder.block.DiscHolderBlockEntityRenderer
import com.kenza.discholder.common.IRInventoryScreen
import com.kenza.discholder.common.UpdateDiscHolderPacket
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

class DiscHolderModClient : ClientModInitializer {
    override fun onInitializeClient() {



        DiscHolderMod.mapEntitiesTypes.values.map { type ->
            BlockEntityRendererRegistry.register(type) {
                DiscHolderBlockEntityRenderer()
            }
        }
//        HandledScreens.register(DiscHolderMod.DISC_BLOCKENTITY_GUI_HANDLER_TYPE) { controller, inv, _ -> IRInventoryScreen(controller, inv.player) }

        ScreenRegistry.register<DiscHolderBlockEntityGuiDescription, CottonInventoryScreen<DiscHolderBlockEntityGuiDescription>>(
            DISC_BLOCKENTITY_GUI_HANDLER_TYPE
        ) { description: DiscHolderBlockEntityGuiDescription, inventory: PlayerInventory?, title: Text? ->
            CottonInventoryScreen(
                description,
                inventory,
                title
            )
        }

        UpdateDiscHolderPacket.register()

    }
}