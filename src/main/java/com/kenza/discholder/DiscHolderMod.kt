package com.kenza.discholder

import com.kenza.discholder.block.DiscHolderBlock
import com.kenza.discholder.block.DiscHolderBlockEntity
import com.kenza.discholder.block.DiscHolderBlockEntityGuiDescription
import com.kenza.discholder.block.DiscHolderBlockEntityRenderer
import com.kenza.discholder.utils.identifier
import com.kenza.discholder.utils.openLastWorldOnInit
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.DyeColor
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import java.util.*
import kotlin.collections.HashMap

class DiscHolderMod : ModInitializer {


    //data get entity @s SelectedItem
    //give @p iron_pickaxe{Damage:10000} 20


    override fun onInitialize() {


        test1()

        openLastWorldOnInit()

    }


    fun test1() {


        DISC_BLOCKENTITY_GUI_HANDLER_TYPE = ScreenHandlerRegistry.registerExtended<DiscHolderBlockEntityGuiDescription>(
            Identifier(
                MOD_ID, "qwerty"
            ),
            ScreenHandlerRegistry.ExtendedClientHandlerFactory<DiscHolderBlockEntityGuiDescription> { syncId: Int, inv: PlayerInventory, buf: PacketByteBuf ->
                DiscHolderBlockEntityGuiDescription(
                    syncId, inv, ScreenHandlerContext.create(inv.player.world, buf.readBlockPos())
                )
            })


//        val x1 = "blue_discholder"
//        registerBlockByName(x1)

        DyeColor.values().map { dyeColor ->

            val x1 = "blue_discholder"
            val itemName = dyeColor.name.lowercase() + "_discholder"

            registerBlockByName(itemName)
//
//            DiscHolderBlock(
//                FabricBlockSettings.of(Material.WOOD).strength(6f).breakByTool(FabricToolTags.AXES, 2).requiresTool(),
//                ::DiscHolderBlockEntityGuiDescription
//            )
        }

    }

    fun registerBlockByName(itemName: String) {


        val DISC_BLOCK = DiscHolderBlock(
            FabricBlockSettings.of(Material.STONE).strength(6f).breakByTool(FabricToolTags.PICKAXES, 2).requiresTool(),
            ::DiscHolderBlockEntityGuiDescription
        )

        val DISC_BLOCK_ITEM = BlockItem(DISC_BLOCK, Item.Settings().group(ItemGroup.REDSTONE))


        var type: BlockEntityType<DiscHolderBlockEntity>? = null

        val DISC_BLOCKENTITY_TYPE = FabricBlockEntityTypeBuilder.create<DiscHolderBlockEntity>(
            FabricBlockEntityTypeBuilder.Factory<DiscHolderBlockEntity> { pos: BlockPos?, state: BlockState? ->
                DiscHolderBlockEntity(
                    type!!,
                    pos,
                    state
                )
            }, DISC_BLOCK
        ).build(null).apply {
            type = this
        }

        mapEntitiesTypes.put(itemName, DISC_BLOCKENTITY_TYPE)

        Registry.register(Registry.BLOCK_ENTITY_TYPE, Identifier(MOD_ID, itemName), DISC_BLOCKENTITY_TYPE)


        BlockEntityRendererRegistry.register(DISC_BLOCKENTITY_TYPE) {
            DiscHolderBlockEntityRenderer()
        }


        Registry.register(Registry.BLOCK, Identifier(MOD_ID, itemName), DISC_BLOCK)
        Registry.register(Registry.ITEM, Identifier(MOD_ID, itemName), DISC_BLOCK_ITEM)


    }


    companion object {


        val mapEntitiesTypes = HashMap<String, BlockEntityType<DiscHolderBlockEntity>>()


        private val discholders: Set<Block> = HashSet()

        //        lateinit var DISC_BLOCK: DiscHolderBlock
//        lateinit var DISC_BLOCK_ITEM: BlockItem
//        lateinit var DISC_BLOCKENTITY_TYPE: BlockEntityType<DiscHolderBlockEntity>
        lateinit var DISC_BLOCKENTITY_GUI_HANDLER_TYPE: ScreenHandlerType<DiscHolderBlockEntityGuiDescription>


        @JvmField
        val MOD_ID = "discholder"

        @JvmField
        val ID = identifier(MOD_ID)

        val UPDATE_INV_PACKET_ID = identifier("update")


        @JvmField
        val LOGGER = LogManager.getLogger("discholder")

    }
}

fun Any.debug(msg: String) {
    DiscHolderMod.LOGGER.debug(msg)
}