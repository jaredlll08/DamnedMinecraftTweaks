package com.blamejared.dmt.capability.ointment;

import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

public interface IOintmentCapability extends INBTSerializable {
    int getStrength();
    void setStrength(int strength);
    String getNature();
    void setNature(String nature);
    @Nullable
    Elements getElement();
}
