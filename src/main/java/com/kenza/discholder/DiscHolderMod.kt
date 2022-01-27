package com.kenza.discholder

import com.kenza.discholder.block.DiscHolderBlock
import com.kenza.discholder.block.DiscHolderBlockEntity
import com.kenza.discholder.block.DiscHolderBlockEntityGuiDescription
import com.kenza.discholder.block.DiscHolderBlockEntityRenderer
import com.kenza.discholder.item.UItems
import com.kenza.discholder.item.USounds
import com.kenza.discholder.profession.UProfessions
import com.kenza.discholder.utils.identifier
import com.kenza.discholder.utils.openLastWorldOnInit
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.*
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.DyeColor
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import java.util.*

class DiscHolderMod : ModInitializer {


    //data get entity @music_disc_cleopona.json SelectedItem
    //give @p iron_pickaxe{Damage:10000} 20


    override fun onInitialize() {


        onConfig()

        USounds.onInit()
        UItems.onInit()
        UProfessions.onInit()


//        openLastWorldOnInit()


    }


    fun onConfig() {


        DISC_BLOCKENTITY_GUI_HANDLER_TYPE = ScreenHandlerRegistry.registerExtended<DiscHolderBlockEntityGuiDescription>(
            Identifier(
                ID_STRING, "gui_handler"
            ),
            ScreenHandlerRegistry.ExtendedClientHandlerFactory<DiscHolderBlockEntityGuiDescription> { syncId: Int, inv: PlayerInventory, buf: PacketByteBuf ->
                DiscHolderBlockEntityGuiDescription(
                    syncId, inv, ScreenHandlerContext.create(inv.player.world, buf.readBlockPos())
                )
            })




        val tabItemName = "blue_discholder"


        DyeColor.values().map { dyeColor ->

            val itemName = dyeColor.name.lowercase() + "_discholder"
            registerBlockByName(itemName)
        }

        TAB = FabricItemGroupBuilder.build(
            Identifier(ID_STRING, "discholder_tab")
        ) {
            ItemStack(mapBlocks[tabItemName])
        }


        DyeColor.values().map { dyeColor ->

            val itemName = dyeColor.name.lowercase() + "_discholder"
            val block = mapBlocks[itemName]
            val DISC_BLOCK_ITEM = BlockItem(block, Item.Settings().group(TAB))
            Registry.register(Registry.ITEM, Identifier(ID_STRING, itemName), DISC_BLOCK_ITEM)
        }


        Items.MUSIC_DISC_11

    }

    fun registerBlockByName(itemName: String) {


        val DISC_BLOCK = DiscHolderBlock(
            FabricBlockSettings.of(Material.WOOD).strength(6f).breakByTool(FabricToolTags.AXES, 2).requiresTool(),
            ::DiscHolderBlockEntityGuiDescription
        )

        mapBlocks.put(itemName, DISC_BLOCK)


        var type: BlockEntityType<DiscHolderBlockEntity>? = null

        val DISC_BLOCKENTITY_TYPE = FabricBlockEntityTypeBuilder.create(
            { pos: BlockPos?, state: BlockState? ->
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

        Registry.register(Registry.BLOCK_ENTITY_TYPE, Identifier(ID_STRING, itemName), DISC_BLOCKENTITY_TYPE)




        Registry.register(Registry.BLOCK, Identifier(ID_STRING, itemName), DISC_BLOCK)


    }


    companion object {


        val mapEntitiesTypes = HashMap<String, BlockEntityType<DiscHolderBlockEntity>>()
        val mapBlocks = HashMap<String, DiscHolderBlock>()


        private val discholders: Set<Block> = HashSet()

        //        lateinit var DISC_BLOCK: DiscHolderBlock
//        lateinit var DISC_BLOCK_ITEM: BlockItem
//        lateinit var DISC_BLOCKENTITY_TYPE: BlockEntityType<DiscHolderBlockEntity>
        lateinit var DISC_BLOCKENTITY_GUI_HANDLER_TYPE: ScreenHandlerType<DiscHolderBlockEntityGuiDescription>

        var TAB: ItemGroup? = null


        @JvmField
        val ID_STRING = "discholder"

        @JvmField
        val ID_ITEM = identifier(ID_STRING)

        val UPDATE_INV_PACKET_ID = identifier("update")


        @JvmField
        val LOGGER = LogManager.getLogger("discholder")


        @JvmStatic
        fun debug(msg: String) {
            DiscHolderMod.LOGGER.debug(msg)
        }


    }
}

fun Any.debug(msg: String) {
    DiscHolderMod.LOGGER.debug(msg)
}