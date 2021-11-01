package com.blamejared.dmt.mixin;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;
import java.util.function.Supplier;

@Mixin(ItemTags.class)
public class OptionalMixin {
    @Inject(method = "Lnet/minecraft/tags/ItemTags;createOptional(Lnet/minecraft/util/ResourceLocation;Ljava/util/Set;)Lnet/minecraftforge/common/Tags$IOptionalNamedTag;", at = @At(value = "HEAD"), remap = false)
    private static void printCreateOptional(ResourceLocation name, Set<Supplier<Item>> defaults, CallbackInfoReturnable<Tags.IOptionalNamedTag<Item>> cir){
        System.out.println("ResourceLocation with name: " + name.toString() + " is attempting to register a tag using createOptional!");
    }
}
