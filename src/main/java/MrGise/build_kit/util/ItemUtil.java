package MrGise.build_kit.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

public class ItemUtil {
    private final String modid;

    public ItemUtil(String modid) {
        this.modid = modid;
    }

    public ItemStack toStack(ItemLike item) {
        return item.asItem().getDefaultInstance();
    }

    public ItemStack toStack(RegistryObject<? extends ItemLike> itemLike) {
        return toStack(itemLike.get().asItem());
    }
}
