package com.kenza.discholder.profession

import com.kenza.discholder.DiscHolderMod
import com.kenza.discholder.item.UItems.MUSIC_DISC_EMPTY
import com.kenza.discholder.utils.random
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.Entity
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.item.*
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.village.TradeOffers
import net.minecraft.village.TradeOffer
import java.util.*

class BuyMusicForEmeraldsFactory(maxUses: Int, experience: Int) : TradeOffers.Factory {

    private val maxUses: Int
    private val experience: Int
    private val multiplier: Float

    override fun create(entity: Entity, random: Random): TradeOffer? {


        val itemStack = ItemStack(MUSIC_DISC_EMPTY, 1)
        return TradeOffer(itemStack, ItemStack(Items.EMERALD).apply {
            count = random(4, 7)
        }, maxUses, experience, multiplier)
    }

    init {
        this.maxUses = maxUses
        this.experience = experience
        multiplier = 0.05f
    }
}