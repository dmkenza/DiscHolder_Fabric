package com.kenza.clickmachine.mixin;

import blue.endless.jankson.annotation.Nullable;
import com.kenza.clickmachine.ext.LivingEntityAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityAttribute {

    @Shadow
    protected int lastAttackedTicks;

    protected LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow protected abstract void sendEquipmentChanges();

    @Override
    public int getLastAttackedTicksValue() {
        return lastAttackedTicks;
    }

    @Override
    public void setLastAttackedTicksValue(int lastAttackedTicksValue) {
        lastAttackedTicks = lastAttackedTicksValue;
    }

    @Override
    public void kenza_sendEquipmentChanges() {
        sendEquipmentChanges();
    }

}