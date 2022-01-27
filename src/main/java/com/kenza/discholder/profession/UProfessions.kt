//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package com.kenza.discholder.profession

import com.google.common.collect.ImmutableMap
import com.kenza.discholder.item.USounds.ENTITY_VILLAGER_WORK_DJ
import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import net.minecraft.block.Blocks
import net.minecraft.item.Items
import net.minecraft.item.MusicDiscItem
import net.minecraft.item.map.MapIcon
import net.minecraft.sound.SoundEvents
import net.minecraft.util.registry.Registry
import net.minecraft.village.TradeOffers
import net.minecraft.village.TradeOffers.*
import net.minecraft.village.VillagerProfession
import net.minecraft.world.gen.feature.StructureFeature
import net.minecraft.world.poi.PointOfInterestType

object UProfessions {


    val DJ_POI = PointOfInterestType.register("dj", PointOfInterestType.getAllStatesOf(Blocks.JUKEBOX), 1, 1)
    val DJ = VillagerProfession.register("dj", DJ_POI, ENTITY_VILLAGER_WORK_DJ)


    fun fillTradeData() {

        val djLevel1 = arrayOf(
            BuyMusicForEmeraldsFactory(20, 5),
            SellMusicForEmeraldsFactory( 10, 5)
        )
        val djLevel2 = arrayOf<Factory>(
            SellMusicForEmeraldsFactory( 10, 10),
            SellMusicForEmeraldsFactory( 10, 10),
        )
        val djLevel3 = arrayOf<Factory>(
            SellMusicForEmeraldsFactory( 10, 10),
            SellMusicForEmeraldsFactory( 10, 10),
        )
        val djLevel4 = arrayOf<Factory>(
            SellMusicForEmeraldsFactory( 10, 10),
            SellMusicForEmeraldsFactory( 10, 10),
        )

        val djLevel5 = arrayOf<Factory>(
            SellMusicForEmeraldsFactory( 10, 10),
            SellMusicForEmeraldsFactory( 10, 10),
        )

        val djLevel6 = arrayOf<Factory>(
            SellMusicForEmeraldsFactory( 10, 10),
            SellMusicForEmeraldsFactory( 10, 10),
        )

        PROFESSION_TO_LEVELED_TRADE[DJ] =
            toIntMap(
                ImmutableMap.of(
                    1,
                    djLevel1,
                    2,
                    djLevel2,
                    3,
                    djLevel3,
                    4,
                    djLevel4,
                    5,
                    djLevel5,
                    6,
                    djLevel6
                )
            )
    }

    private fun toIntMap(trades: ImmutableMap<Int, Array<Factory>>): Int2ObjectMap<Array<Factory>> {
        return Int2ObjectOpenHashMap(trades)
    }


}