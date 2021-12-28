package com.blamejared.dmt.capability.ointment;

import com.blamejared.dmt.DamnedMinecraftTweaks;
import com.blamejared.dmt.item.ItemOintment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OintmentManager {
    @CapabilityInject(IOintmentCapability.class)
    public static Capability<IOintmentCapability> STATIC_CAPABILITY = null;
    public static final ResourceLocation OINTMENT_CAPABILITY = DamnedMinecraftTweaks.rl("ointment_capability");

    @SubscribeEvent
    public static void capabilityAdd(final AttachCapabilitiesEvent<ItemStack> event){
        if (event.getObject().getItem() instanceof ItemOintment){
            event.addCapability(OINTMENT_CAPABILITY, SimplePersistentCapabilityProvider.from(STATIC_CAPABILITY, OintmentCapability::new));
        }
    }
    public static void registerCap(){
        CapabilityManager.INSTANCE.register(IOintmentCapability.class, SimplePersistentCapabilityProvider.from(STATIC_CAPABILITY, () -> STATIC_CAPABILITY.getDefaultInstance()), OintmentCapability::new);
    }
    public static class SimplePersistentCapabilityProvider<C, S extends INBT> implements ICapabilityProvider, INBTSerializable<S>, Capability.IStorage<C> {
        //Powered by Silk lol
        private final Capability<C> capability;
        private final LazyOptional<C> implementation;
        private final Direction direction;

        protected SimplePersistentCapabilityProvider(@Nonnull final Capability<C> capability, @Nonnull final LazyOptional<C> implementation, final Direction direction) {
            this.capability = capability;
            this.implementation = implementation;
            this.direction = direction;
        }

        @Nonnull
        public static <C> SimplePersistentCapabilityProvider<C, INBT> from(@Nonnull final Capability<C> cap, @Nonnull final NonNullSupplier<C> impl) {
            return from(cap, null, impl);
        }

        @Nonnull
        public static <C> SimplePersistentCapabilityProvider<C, INBT> from(@Nonnull final Capability<C> cap,  final Direction direction, @Nonnull final NonNullSupplier<C> impl) {
            return new SimplePersistentCapabilityProvider<>(cap, LazyOptional.of(impl), direction);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, final Direction side) {
            if (cap == this.capability) return this.implementation.cast();
            return LazyOptional.empty();
        }

        @Override
        @SuppressWarnings("unchecked")
        public S serializeNBT() {
            return (S) this.capability.writeNBT(this.getInstance(), this.direction);
        }

        @Override
        public void deserializeNBT(@Nonnull final S nbt) {
            this.capability.readNBT(this.getInstance(), this.direction, nbt);
        }

        @Nonnull
        private C getInstance() {
            return this.implementation.orElseThrow(() -> new IllegalStateException("Unable to obtain capability instance"));
        }

        @Nullable
        @Override
        public INBT writeNBT(Capability<C> capability, C instance, Direction side) {
            return capability.writeNBT(instance, side);
        }

        @Override
        public void readNBT(Capability<C> capability, C instance, Direction side, INBT nbt) {
            capability.readNBT(instance, side, nbt);
        }
    }
}
