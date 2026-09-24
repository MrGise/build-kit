package MrGise.build_kit.datagen.model;

import MrGise.build_kit.BuildKit;
import MrGise.build_kit.registry.block.StoneBlocks;
import MrGise.build_kit.registry.block.WoodenBlocks;
import MrGise.build_kit.types.block.LayeredBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import static MrGise.build_kit.BuildKit.util;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BuildKit.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (int i = 1; i < 16; i++) {
            int level = i;
            models().withExistingParent("layered_block_" + i, "block/block").renderType("cutout")
                    .texture("top", modLoc("block/oak_plate")).texture("sides",
                            util.affix().addSuffix(modLoc("block/stages/oak_plate/oak_plate"), String.valueOf(level)))
                    .texture("particle", modLoc("block/oak_plate"))
                    .element().from(0.0f, 0.0f, 0.0f).to(16.0f, i, 16.0f)
                    .allFaces((dir, fBuilder) -> {
                        if (dir == Direction.NORTH || dir == Direction.SOUTH || dir == Direction.EAST || dir == Direction.WEST) {
                            fBuilder.texture("#sides").uvs(0.0f, 16.0f - level, 16.0f, 16.0f).end();
                        } else if (dir != null) {
                            fBuilder.texture("#top").uvs(0.0f, 0.0f, 16.0f, 16.0f).end();
                        }
                    }).end();
        }

        // Wood
        layeredBlock((LayeredBlock) WoodenBlocks.OAK_LAYER.get(), modLoc("block/oak_plate"),
                modLoc("block/stages/oak_plate/oak_plate"));

        simpleBlockWithItem(WoodenBlocks.OAK_PLATING.get(), modLoc("block/oak_plate"));

        // Stone
        simpleBlockWithItem(StoneBlocks.STONE_TILE.get(), modLoc("block/stone_plate"));
    }

    private void simpleBlockWithItem(Block block, ResourceLocation texture) {
        String blockName = util.resourceUtil().blockId(block);
        simpleBlockWithItem(block, models().cubeAll(blockName, texture));
    }

    private void layeredBlock(LayeredBlock block, ResourceLocation texture, ResourceLocation leveledLocation) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        String blockName = util.resourceUtil().blockPath(block);
        ModelFile model_16 = models().cubeAll(blockName, texture);
        builder.partialState().with(LayeredBlock.LEVEL, 16)
                .modelForState().modelFile(model_16).addModel();

        for (int level = 1; level < 16; level++) {
            ModelFile model_temp = models().withExistingParent(blockName + "_" + level, modLoc("layered_block_" + level))
                    .texture("top", texture).texture("sides", util.affix().addSuffix(leveledLocation, String.valueOf(level)))
                    .texture("particle", texture);
            builder.partialState().with(LayeredBlock.LEVEL, level)
                    .modelForState().modelFile(model_temp).addModel();
        }

        simpleBlockItem(block, models().getExistingFile(modLoc(blockName + "_1")));
    }
}
