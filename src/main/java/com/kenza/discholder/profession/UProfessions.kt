//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package com.kenza.discholder.profession

import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import com.kenza.discholder.item.USounds
import com.kenza.discholder.mixin.PoiTypesInvoker
import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.sound.SoundEvents
import net.minecraft.util.registry.RegistryEntry
import net.minecraft.village.TradeOffers.Factory
import net.minecraft.village.TradeOffers.PROFESSION_TO_LEVELED_TRADE
import net.minecraft.village.VillagerProfession
import net.minecraft.world.poi.PointOfInterestType
import java.util.function.Supplier


object UProfessions {


//    private lateinit var DJ: Supplier<VillagerProfession>
//    private lateinit var DJ_POI: Supplier<PointOfInterestType>

    @JvmStatic
    val DJ_POI = CommonPlatformHelper.registerPoiType(
        "dj"
    ) {
        PointOfInterestType(
            PoiTypesInvoker.invokeGetBlockStates(
                Blocks.JUKEBOX as Block
            ), 1, 1
        )
    }
    @JvmStatic
    val DJ = CommonPlatformHelper.registerProfession(
        "dj"
    ) {
        VillagerProfession(
            "dj",
            { holder: RegistryEntry<PointOfInterestType> -> holder.value() as PointOfInterestType == DJ_POI.get() },
            { holder: RegistryEntry<PointOfInterestType> -> holder.value() as PointOfInterestType == DJ_POI.get() },
            ImmutableSet.of(),
            ImmutableSet.of(),
            USounds.ENTITY_VILLAGER_WORK_DJ
        )
    }

//    val DJ_POI = PointOfInterestHelper.register(identifier("dj"), 10,  16, Blocks.JUKEBOX)
//    val DJ = VillagerProfession.register("dj", DJ_POI, ENTITY_VILLAGER_WORK_DJ)
//    val DJ = VillagerProfession.register(
//        "dj",
//        RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE_KEY, identifier("dj")),
//        SoundEvents.ENTITY_VILLAGER_WORK_SHEPHERD
//    )
//    val DJ: VillagerProfession =
//        VillagerProfessionBuilder.create().id(identifier( "dj")).workstation(RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE_KEY, identifier("dj")))
//            .workSound(SoundEvents.ITEM_LODESTONE_COMPASS_LOCK).build()


    fun onInit() {


//        DJ_POI.blockStates()

        val djLevel1 = arrayOf(
            BuyMusicForEmeraldsFactory(20, 5),
            SellMusicForEmeraldsFactory(10, 5)
        )
        val djLevel2 = arrayOf<Factory>(
            SellMusicForEmeraldsFactory(10, 10),
            SellMusicForEmeraldsFactory(10, 10),
        )
        val djLevel3 = arrayOf<Factory>(
            SellMusicForEmeraldsFactory(10, 10),
            SellMusicForEmeraldsFactory(10, 10),
        )
        val djLevel4 = arrayOf<Factory>(
            SellMusicForEmeraldsFactory(10, 10),
            SellMusicForEmeraldsFactory(10, 10),
        )

        val djLevel5 = arrayOf<Factory>(
            SellMusicForEmeraldsFactory(10, 10),
            SellMusicForEmeraldsFactory(10, 10),
        )

        val djLevel6 = arrayOf<Factory>(
            SellMusicForEmeraldsFactory(10, 10),
            SellMusicForEmeraldsFactory(10, 10),
        )


        PROFESSION_TO_LEVELED_TRADE[DJ.get() as VillagerProfession] = toIntMap(
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