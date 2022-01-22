package com.kenza.discholder

import com.kenza.discholder.utils.identifier
import com.kenza.discholder.utils.openLastWorldOnInit
import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager

class ClickMachine : ModInitializer {


    //data get entity @s SelectedItem
    //give @p iron_pickaxe{Damage:10000} 20


    override fun onInitialize() {

        openLastWorldOnInit()
    }


    companion object {

        @JvmField
        val ID = "discholder"

        @JvmField
        val MOD_ID = identifier(ID)


        @JvmField
        val LOGGER = LogManager.getLogger("discholder")

    }
}

fun Any.debug(msg: String) {
    ClickMachine.LOGGER.debug(msg)
}