package com.kenza.discholder.common

import com.kenza.discholder.DiscHolderMod.Companion.UPDATE_INV_PACKET_ID
import com.kenza.discholder.block.DiscHolderBlockEntity
import com.kenza.discholder.utils.identifier
import com.kenza.discholder.utils.isLoaded
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.collection.DefaultedList

object UpdateDiscHolderPacket {


    fun register() {
//        ServerPlayNetworking.registerGlobalReceiver(UPDATE_VALUE_PACKET_ID) { server, player, _, buf, _ ->
//            val value = buf.readBoolean()
//            val pos = buf.readBlockPos()
//            val world = player.world
//            server.execute {
//                if (world.isLoaded(pos)) {
////                    val blockEntity = world.getBlockEntity(pos) as? AutoClickerBlockEntity ?: return@execute
////                    blockEntity.rightClickMode = value
////                    blockEntity.markDirty()
////                    blockEntity.sync()
//                }
//            }
//        }

        // DiscHolder Rendering Packets
        ClientPlayNetworking.registerGlobalReceiver(UPDATE_INV_PACKET_ID) { client: MinecraftClient, handler: ClientPlayNetworkHandler?, buf: PacketByteBuf, responseSender: PacketSender? ->
            val pos = buf.readBlockPos()
            val inv = DefaultedList.ofSize(7, ItemStack.EMPTY)
            for (i in 0..6) {
                inv[i] = buf.readItemStack()
            }
            client.execute {
                val blockEntity: DiscHolderBlockEntity? =
                    MinecraftClient.getInstance().world!!.getBlockEntity(pos) as DiscHolderBlockEntity?

                inv.mapIndexed { index, itemStack ->
                    blockEntity?.setStack(index, itemStack)
                }
            }
        }

    }
}