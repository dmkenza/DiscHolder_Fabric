package com.kenza.discholder

import com.kenza.discholder.utils.identifier
import com.kenza.discholder.utils.openLastWorldOnInit
import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager

class DiscHolderMod : ModInitializer {


    //data get entity @s SelectedItem
    //give @p iron_pickaxe{Damage:10000} 20


    override fun onInitialize() {
        openLastWorldOnInit()
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