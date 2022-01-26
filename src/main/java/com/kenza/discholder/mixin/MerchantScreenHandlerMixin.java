package com.kenza.discholder.mixin;

import com.kenza.discholder.item.UItems;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.MerchantScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.kenza.discholder.DiscHolderMod.debug;
import static com.kenza.discholder.block.DiscHolderBlockKt.hasMusicDiscItemType;
import static com.kenza.discholder.item.UItems.MUSIC_DISC_EMPTY;

@Mixin(MerchantScreenHandler.class)
class MerchantScreenHandlerMixin {
//
//    @Inject(method = "autofill(ILnet/minecraft/item/ItemStack;)V",
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;canCombine(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z"))


    boolean musicDiscGetted = false;

    @Redirect(method = "autofill(ILnet/minecraft/item/ItemStack;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;canCombine(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z"))
    public boolean kenza_autofill(ItemStack stack, ItemStack otherStack) {

        boolean isEmptyMusicDisc = stack.getItem() == MUSIC_DISC_EMPTY;

        boolean result = (isEmptyMusicDisc && hasMusicDiscItemType(otherStack) && !musicDiscGetted) || ItemStack.canCombine(stack, otherStack);

        if(isEmptyMusicDisc && result){
            musicDiscGetted = true;
        }

        return result  ;
//        return  (isEmptyMusicDisc && hasMusicDiscItemType(otherStack)) || ItemStack.canCombine(stack, otherStack)  ;
    }


    @Inject(method = "autofill(ILnet/minecraft/item/ItemStack;)V", at = @At("RETURN"))
    public void kenza_autofill(int slot, ItemStack stack, CallbackInfo ci) {
        musicDiscGetted = false;

    }

}
