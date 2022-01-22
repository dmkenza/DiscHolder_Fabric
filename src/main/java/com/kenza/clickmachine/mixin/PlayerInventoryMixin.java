package com.kenza.clickmachine.mixin;


import com.kenza.clickmachine.ext.PlayerInventoryAccessor;
import dev.cafeteria.fakeplayerapi.server.FakeServerPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin implements PlayerInventoryAccessor {


    @Shadow
    @Final
    public  DefaultedList<ItemStack> main;

    @NotNull
    @Override
    public DefaultedList<ItemStack> getMain() {
        return main;
    }

    @Override
    public void setMain(@NotNull DefaultedList<ItemStack> main) {

    }
}