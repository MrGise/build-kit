package MrGise.build_kit.datagen.model;

import MrGise.build_kit.BuildKit;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BuildKit.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
