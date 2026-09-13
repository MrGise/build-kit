package MrGise.build_kit.datagen.tag;

import MrGise.build_kit.BuildKit;
import MrGise.build_kit.registry.block.WoodenBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class WoodenBlockTagGenerator extends BlockTagsProvider {
    public WoodenBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, BuildKit.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                WoodenBlocks.OAK_LAYER.get()
        );
    }
}
