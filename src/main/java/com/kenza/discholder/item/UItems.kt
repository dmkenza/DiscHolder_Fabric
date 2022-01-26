package com.kenza.discholder.item

import com.kenza.discholder.DiscHolderMod
import com.kenza.discholder.DiscHolderMod.Companion.ID_STRING
import com.kenza.discholder.item.USounds.BAROQUE_NIGHTMARE
import com.kenza.discholder.item.USounds.CLEOPONA
import com.kenza.discholder.item.USounds.FOR_THE_NEW_LUNAR_REPUBLIC
import com.kenza.discholder.item.USounds.HARMONY
import com.kenza.discholder.item.USounds.NO_STRINGS_ATTACHED
import com.kenza.discholder.item.USounds.SPIRIT_ANIMAL
import com.kenza.discholder.item.USounds.SUN_BURST
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.MusicDiscItem
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry

object UItems {

    var MUSIC_DISC_BAROQUE_NIGHTMARE: Item = register("music_disc_baroque_nightmare", BAROQUE_NIGHTMARE)
    var MUSIC_DISC_CLEOPONA: Item = register("music_disc_cleopona", CLEOPONA)
    var MUSIC_DISC_FOR_THE_NEW_LUNAR_REPUBLIC: Item = register("music_disc_for_the_new_lunar_republic", FOR_THE_NEW_LUNAR_REPUBLIC)
    var MUSIC_DISC_NO_STRINGS_ATTACHED: Item = register("music_disc_no_strings_attached", NO_STRINGS_ATTACHED)
    var MUSIC_DISC_SUN_BURST: Item = register("music_disc_sun_burst", SUN_BURST)
    var MUSIC_DISC_HARMONY: Item = register("music_disc_harmony", HARMONY)
    var MUSIC_DISC_SPIRIT_ANIMAL: Item = register("music_disc_spirit_animal", SPIRIT_ANIMAL)

    @kotlin.jvm.JvmField
    val MUSIC_DISC_EMPTY =  Registry.register(Registry.ITEM, Identifier(DiscHolderMod.ID_STRING, "music_disc_empty"), Item(
        FabricItemSettings()
    ))


    fun onInit(){
    }

    fun <T : Item?> register(name: String?, item: T): T {
//        ITEMS.add(item)
        if (item is BlockItem) {
            (item as BlockItem).appendBlocks(Item.BLOCK_ITEMS, item)
        }
        return Registry.register(Registry.ITEM, Identifier(ID_STRING, name), item)
    }

    fun register(name: String?, sound: SoundEvent?): MusicDiscItem {
        return register(name, object : MusicDiscItem(
            1, sound, Settings()
                .maxCount(1)
                .group(ItemGroup.MISC)
                .rarity(Rarity.RARE)
        ) {})
    }

}