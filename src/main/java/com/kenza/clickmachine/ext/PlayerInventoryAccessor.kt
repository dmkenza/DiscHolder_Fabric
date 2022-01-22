package com.kenza.clickmachine.ext

import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

interface PlayerInventoryAccessor {

    var main: DefaultedList<ItemStack>
}