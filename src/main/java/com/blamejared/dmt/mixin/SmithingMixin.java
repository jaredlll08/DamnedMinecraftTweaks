package com.blamejared.dmt.mixin;

import com.bettersmithing.init.ModTags;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(targets = "com/bettersmithing/init/ModTags$Items")
public class SmithingMixin {
    /**
     * @author Witixin
     * @reason stop Breaking datapacks horribly
     */
    @Overwrite(aliases = "tag", remap = false)
    private static ITag.INamedTag<Item> tag(String name) {
        return tag("bettersmithing" , name);
    }

    /**
     * @author Witixin
     * @reason stop Breaking datapacks horribly
     */
    @Overwrite(aliases = "tag", remap = false)
    private static ITag.INamedTag<Item> tag(String namespace, String path) {
        return ItemTags.bind(namespace + ":" + path);
    }
}