package com.kenza.discholder.utils

import com.kenza.discholder.DiscHolderMod
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.fabricmc.fabric.impl.screenhandler.ExtendedScreenHandlerType
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.PlayerScreenHandler
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.util.Identifier
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry


val EMPTY_INT_ARRAY = intArrayOf()

fun identifier(id: String) = Identifier(DiscHolderMod.MOD_ID, id)

fun blockSpriteId(id: String) = SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, identifier(id))

fun Identifier.block(block: Block): Identifier {
    Registry.register(Registry.BLOCK, this, block)
    return this
}

fun Identifier.fluid(fluid: Fluid): Identifier {
    Registry.register(Registry.FLUID, this, fluid)
    return this
}

fun Identifier.item(item: Item): Identifier {
    Registry.register(Registry.ITEM, this, item)
    return this
}

fun Identifier.blockEntityType(entityType: BlockEntityType<*>): Identifier {
    Registry.register(Registry.BLOCK_ENTITY_TYPE, this, entityType)
    return this
}

//fun itemSettings(): FabricItemSettings = FabricItemSettings().group(IndustrialRevolution.MOD_GROUP)

fun <T : ScreenHandler> Identifier.registerScreenHandler(
    f: (Int, PlayerInventory, ScreenHandlerContext) -> T
): ExtendedScreenHandlerType<T> =
    ScreenHandlerRegistry.registerExtended(this) { syncId, inv, buf ->
        f(syncId, inv, ScreenHandlerContext.create(inv.player.world, buf.readBlockPos()))
    } as ExtendedScreenHandlerType<T>


fun ChunkPos.toNbt() = NbtCompound().also {
    it.putInt("x", x)
    it.putInt("z", z)
}

fun getChunkPos(nbt: NbtCompound) = ChunkPos(nbt.getInt("x"), nbt.getInt("z"))


fun pack(dirs: Collection<Direction>): Byte {
    var i = 0
    dirs.forEach { dir -> i = i or (1 shl dir.id) }
    return i.toByte()
}

fun unpack(byte: Byte): List<Direction> {
    val i = byte.toInt()
    return DIRECTIONS.filter { dir -> i and (1 shl dir.id) != 0 }
}

private val DIRECTIONS = Direction.values()

val Fluid?.rawId: Int
    get() = Registry.FLUID.getRawId(this)
