package com.kenza.discholder.utils

fun getSlotInBlock(inc: Double): Int {
    return if (inc < 1 / 16.0 || inc > 15 / 16.0) -1 else (8 * inc - .5).toInt()
}