package com.kenza.discholder.item

import com.kenza.discholder.DiscHolderMod.Companion.ID_STRING
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object USounds {


    fun register(name: String?): SoundEvent {
        val id = Identifier(ID_STRING, name)
        return Registry.register(Registry.SOUND_EVENT, id, SoundEvent(id))
    }


    fun onInit() {}

    val BAROQUE_NIGHTMARE = register("record.baroque_nightmare")
    val CLEOPONA = register("record.cleopona")
    val FOR_THE_NEW_LUNAR_REPUBLIC = register("record.for_the_new_lunar_republic")
    val NO_STRINGS_ATTACHED = register("record.no_strings_attached")
    val SUN_BURST = register("record.sun_burst")
    val HARMONY = register("record.harmony")
    val SPIRIT_ANIMAL = register("record.spirit_animal")


    val ENTITY_VILLAGER_WORK_DJ = register("profession.dj")
}