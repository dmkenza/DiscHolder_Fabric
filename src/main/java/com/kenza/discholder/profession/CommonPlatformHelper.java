package com.kenza.discholder.profession;


import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class CommonPlatformHelper {
    public CommonPlatformHelper() {
    }

    @ExpectPlatform
    @Transformed
    public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        return CommonPlatformHelperImpl.registerBlock(name, block);
    }

    @ExpectPlatform
    @Transformed
    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
        return CommonPlatformHelperImpl.registerItem(name, item);
    }

    @ExpectPlatform
    @Transformed
    public static Supplier<VillagerProfession> registerProfession(String name, Supplier<VillagerProfession> profession) {
        return CommonPlatformHelperImpl.registerProfession(name, profession);
    }

    @ExpectPlatform
    @Transformed
    public static Supplier<PointOfInterestType> registerPoiType(String name, Supplier<PointOfInterestType> poiType) {
        return CommonPlatformHelperImpl.registerPoiType(name, poiType);
    }

    @ExpectPlatform
    @Transformed
    public static ItemGroup registerCreativeModeTab(Identifier name, Supplier<ItemStack> icon) {
        return CommonPlatformHelperImpl.registerCreativeModeTab(name, icon);
    }
}
