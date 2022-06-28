package com.kenza.discholder.profession;


import java.util.function.Supplier;

import com.kenza.discholder.mixin.PoiTypesInvoker;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

import static com.kenza.discholder.DiscHolderMod.ID_STRING;

public class CommonPlatformHelperImpl {
    public CommonPlatformHelperImpl() {
    }

    public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        T registry = (T) Registry.register(Registry.BLOCK, new Identifier(ID_STRING, name), (Block)block.get());
        return () -> {
            return registry;
        };
    }

    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
        T registry = (T) Registry.register(Registry.ITEM, new Identifier(ID_STRING, name), (Item)item.get());
        return () -> {
            return registry;
        };
    }

    public static Supplier<VillagerProfession> registerProfession(String name, Supplier<VillagerProfession> profession) {
        VillagerProfession registry = (VillagerProfession)Registry.register(Registry.VILLAGER_PROFESSION, new Identifier(ID_STRING, name), (VillagerProfession)profession.get());
        return () -> {
            return registry;
        };
    }

    public static Supplier<PointOfInterestType> registerPoiType(String name, Supplier<PointOfInterestType> poiType) {
        RegistryKey<PointOfInterestType> resourceKey = RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE_KEY, new Identifier(ID_STRING, name));
        PointOfInterestType registry = (PointOfInterestType)Registry.register(Registry.POINT_OF_INTEREST_TYPE, resourceKey, (PointOfInterestType)poiType.get());
        PoiTypesInvoker.invokeRegisterBlockStates(Registry.POINT_OF_INTEREST_TYPE.entryOf(resourceKey));
        return () -> {
            return registry;
        };
    }

    public static ItemGroup registerCreativeModeTab(Identifier name, Supplier<ItemStack> icon) {
        return FabricItemGroupBuilder.build(name, icon);
    }
}
