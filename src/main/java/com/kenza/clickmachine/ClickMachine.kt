package com.kenza.clickmachine

import com.kenza.clickmachine.blocks.AutoClickerBlock
import com.kenza.clickmachine.blocks.AutoClickerBlockEntity
import com.kenza.clickmachine.blocks.AutoClickerGuiDescription
import com.kenza.clickmachine.common.UpdateAutoClickerPacket
import com.kenza.clickmachine.utils.identifier
import dev.cafeteria.fakeplayerapi.server.FakePlayerBuilder
import dev.cafeteria.fakeplayerapi.server.FakeServerPlayer
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
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
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import java.util.*

class ClickMachine : ModInitializer {


    //data get entity @s SelectedItem
    //give @p iron_pickaxe{Damage:10000} 20


    override fun onInitialize() {

//        openLastWorldOnInit()


        GUI_BLOCK = AutoClickerBlock(
            FabricBlockSettings.of(Material.STONE).strength(6f).breakByTool(FabricToolTags.PICKAXES, 2).requiresTool(),
            ::AutoClickerGuiDescription
        )
        Registry.register(Registry.BLOCK, Identifier(ID, "auto_clicker"), GUI_BLOCK)
        GUI_BLOCK_ITEM = BlockItem(GUI_BLOCK, Item.Settings().group(ItemGroup.REDSTONE))
        Registry.register(Registry.ITEM, Identifier(ID, "auto_clicker"), GUI_BLOCK_ITEM)
        GUI_BLOCKENTITY_TYPE = FabricBlockEntityTypeBuilder.create<AutoClickerBlockEntity>(
            FabricBlockEntityTypeBuilder.Factory<AutoClickerBlockEntity> { pos: BlockPos?, state: BlockState? ->
                AutoClickerBlockEntity(
                    pos,
                    state
                )
            }, GUI_BLOCK
        ).build(null)
        Registry.register(Registry.BLOCK_ENTITY_TYPE, Identifier(ID, "auto_clicker"), GUI_BLOCKENTITY_TYPE)

        GUI_SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerExtended<AutoClickerGuiDescription>(
            Identifier(
                ID, "auto_clicker"
            ),
            ScreenHandlerRegistry.ExtendedClientHandlerFactory<AutoClickerGuiDescription> { syncId: Int, inv: PlayerInventory, buf: PacketByteBuf ->
                AutoClickerGuiDescription(
                    syncId, inv, ScreenHandlerContext.create(inv.player.world, buf.readBlockPos())
                )
            })



        UpdateAutoClickerPacket.register()
    }


    companion object {

//        lateinit var SCREEN_HANDLER_TYPE: ScreenHandlerType<ExampleGuiDescription>
        lateinit var GUI_SCREEN_HANDLER_TYPE: ScreenHandlerType<AutoClickerGuiDescription>
        var GUI_BLOCK: AutoClickerBlock? = null
        var NO_BLOCK_INVENTORY_BLOCK: Block? = null
        var GUI_BLOCK_ITEM: BlockItem? = null
        var GUI_BLOCKENTITY_TYPE: BlockEntityType<AutoClickerBlockEntity>? = null




        @JvmField
        val ID = "clickmachine"

        @JvmField
        val MOD_ID = identifier(ID)


        @JvmField
        val LOGGER = LogManager.getLogger("click_machine")


        fun createFakePlayerBuilder(uuid: UUID?): FakePlayerBuilder {
            return FakePlayerBuilder(identifier("default_fake_player")) { builder, server, world, profile ->
                object : FakeServerPlayer(builder, server, world, profile) {

                    override fun getId(): Int {
                        return super.getId()
                    }

                    override fun getUuid(): UUID {
                        return uuid ?: super.getUuid()
                    }
                    override fun isCreative(): Boolean = false
                    override fun isSpectator(): Boolean = false
                    override fun playSound(sound: SoundEvent?, volume: Float, pitch: Float) {}
                    override fun playSound(event: SoundEvent?, category: SoundCategory?, volume: Float, pitch: Float) {}
                }
            }
        }

//        val FAKE_PLAYER_BUILDER =

    }
}

fun Any.debug(msg: String) {
    ClickMachine.LOGGER.debug(msg)
}