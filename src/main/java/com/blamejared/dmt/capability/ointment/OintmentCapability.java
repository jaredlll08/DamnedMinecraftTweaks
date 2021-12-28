package com.blamejared.dmt.capability.ointment;

import com.blamejared.dmt.DamnedMinecraftTweaks;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

import javax.annotation.Nullable;

public class OintmentCapability implements IOintmentCapability {

    private String nature;
    private int strength;

    private static final String NBT_NATURE_KEY = "NATURE";
    private static final String NBT_STRENGTH_KEY = "STRENGTH";

    public OintmentCapability(String nature, int strength){
        this.nature = nature;
        this.strength = strength;
    }

    public OintmentCapability(){
        this("none", 1);
    }


    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public String getNature() {
        return nature;
    }

    @Override
    public void setNature(String nature) {
        this.nature = nature;
    }
    @Override
    @Nullable
    public Elements getElement() {
        return Elements.valueOf(getNature());
    }

    @Override
    public INBT serializeNBT() {
        CompoundNBT global = new CompoundNBT();
        CompoundNBT internal = new CompoundNBT();
        internal.putString(NBT_NATURE_KEY, getNature());
        internal.putInt(NBT_STRENGTH_KEY, getStrength());
        global.put(DamnedMinecraftTweaks.NBT_STORAGE_KEY_GLOBAL, internal);
        return global;
    }



    @Override
    public void deserializeNBT(INBT nbt) {
        CompoundNBT realNBT = (CompoundNBT) ((CompoundNBT) nbt).get(DamnedMinecraftTweaks.NBT_STORAGE_KEY_GLOBAL);
        this.setNature(realNBT.getString(NBT_NATURE_KEY));
        this.setStrength(realNBT.getInt(NBT_STRENGTH_KEY));
    }
}

