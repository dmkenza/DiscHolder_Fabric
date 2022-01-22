package com.kenza.discholder

import com.kenza.discholder.utils.identifier
import com.kenza.discholder.utils.openLastWorldOnInit
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Material
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager

class DiscHolderMod : ModInitializer {


    //data get entity @s SelectedItem
    //give @p iron_pickaxe{Damage:10000} 20


    override fun onInitialize() {
        openLastWorldOnInit()


        val DISC_BLOCK = DiscHolderBlock(
            FabricBlockSettings.of(Material.STONE).strength(6f).breakByTool(FabricToolTags.PICKAXES, 2).requiresTool()
        )
        Registry.register(Registry.BLOCK, Identifier(MOD_ID, "blue_discholder"), DISC_BLOCK)

        val DISC_BLOCK_ITEM = BlockItem(DISC_BLOCK, Item.Settings().group(ItemGroup.REDSTONE))
        Registry.register(Registry.ITEM, Identifier(MOD_ID, "blue_discholder"), DISC_BLOCK_ITEM)

    }


    companion object {

        @JvmField
        val MOD_ID = "discholder"

        @JvmField
        val ID = identifier(MOD_ID)


        @JvmField
        val LOGGER = LogManager.getLogger("discholder")

    }
}

fun Any.debug(msg: String) {
    DiscHolderMod.LOGGER.debug(msg)
}