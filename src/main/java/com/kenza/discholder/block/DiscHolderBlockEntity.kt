package com.kenza.discholder.block

import com.kenza.discholder.DiscHolderMod.Companion.UPDATE_INV_PACKET_ID
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import java.util.function.Consumer


class DiscHolderBlockEntity(type: BlockEntityType<DiscHolderBlockEntity>?, pos: BlockPos?, state: BlockState?) :
    BlockEntity(type, pos, state), ImplementedInventory, NamedScreenHandlerFactory {

    private var inventoriesItems: DefaultedList<ItemStack> = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY)
    private var inventoryTouched = true


//    override fun canInsert(slot: Int, stack: ItemStack?, side: Direction?): Boolean {
//        return false //stack.hasMusicDiscItemType()
//    }
//
//    override fun canExtract(slot: Int, stack: ItemStack?, side: Direction?): Boolean {
//        return false// stack.hasMusicDiscItemType()
//    }

    override fun markDirty() {
        super<BlockEntity>.markDirty()
    }

    override fun setStack(slot: Int, stack: ItemStack?) {
        inventoryTouched = true
        super.setStack(slot, stack)
    }

    override fun getItems(): DefaultedList<ItemStack> {
        inventoryTouched = true
        return inventoriesItems
    }

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity): ScreenHandler {
        return DiscHolderBlockEntityGuiDescription(
            syncId,
            inv,
            ScreenHandlerContext.create(world, pos)
        )
    }

    override fun getDisplayName(): Text {
        return LiteralText("Disc Holder")
    }

    override fun toUpdatePacket(): BlockEntityUpdateS2CPacket {
        return BlockEntityUpdateS2CPacket.create(this)
    }

    override fun toInitialChunkDataNbt(): NbtCompound {
        val nbt = super.toInitialChunkDataNbt()
        writeNbt(nbt)
//        nbt.putBoolean("#c", true)
        return nbt
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        Inventories.readNbt(nbt, items)
//        rightClickMode = nbt.getBoolean("rightClickMode")
//        placerEntityUuid = nbt.getUuid("placerEntityUuid")
    }

    public override fun writeNbt(nbt: NbtCompound) {
        Inventories.writeNbt(nbt, items)
//        nbt.putBoolean("rightClickMode", rightClickMode)
//        nbt.putUuid("placerEntityUuid", placerEntityUuid)
    }


    fun getDiscInSlot(slot: Int): ItemStack {
        return inventoriesItems.getOrNull(slot) ?: ItemStack.EMPTY
    }



    fun tick() {

        if (!world!!.isClient && this.inventoryTouched) {

            val viewers = PlayerLookup.tracking(this)
            val buf = PacketByteBuf(Unpooled.buffer())
            buf.writeBlockPos(pos)

            for (i in 0..6) {
                buf.writeItemStack(items.get(i))
            }
            viewers.forEach(Consumer { player: ServerPlayerEntity? ->
                ServerPlayNetworking.send(
                    player, UPDATE_INV_PACKET_ID,
                    buf
                )
            })
            inventoryTouched = false
        }
    }


//    private fun getTopStacks(): DefaultedList<ItemStack> {
//
//        var topStacks: DefaultedList<ItemStack> = DefaultedList.ofSize(12, ItemStack.EMPTY)
//
//        var startIndex = 0
//        for (i in 0..12) {
//            for (j in startIndex until items.size) {
//                val stack = items[j]
//                if (stack.item !== Items.AIR) {
//                    startIndex = j + 1
//                    topStacks.set(i, stack)
//                    if (startIndex > items.size) {
//                        return topStacks
//                    }
//                    break
//                }
//            }
//        }
//
//        return topStacks
//    }


    companion object {
        const val INVENTORY_SIZE = 7
    }

}