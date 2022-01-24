package com.kenza.discholder.block

import com.kenza.discholder.DiscHolderMod
import net.minecraft.util.math.BlockPos
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.ItemScatterer
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World

class DiscHolderBlockEntity(pos: BlockPos?, state: BlockState?) :
    BlockEntity(DiscHolderMod.DISC_BLOCKENTITY_TYPE, pos, state), ImplementedInventory, NamedScreenHandlerFactory {

    private var inventoriesItems = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY)



    override fun markDirty() {
        super<BlockEntity>.markDirty()
    }

    override fun getItems(): DefaultedList<ItemStack> {
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


    companion object {
        const val INVENTORY_SIZE = 7
    }

}