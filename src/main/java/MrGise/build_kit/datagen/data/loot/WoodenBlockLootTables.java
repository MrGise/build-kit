package MrGise.build_kit.datagen.data.loot;

import MrGise.build_kit.registry.block.WoodenBlocks;
import MrGise.build_kit.types.block.LayeredBlock;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class WoodenBlockLootTables extends BlockLootSubProvider {
    public WoodenBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.add(WoodenBlocks.OAK_LAYER.get(),
                this::createLayeredBlockLootTable);
        this.dropSelf(WoodenBlocks.OAK_PLATING.get());
    }

    private LootTable.Builder createLayeredBlockLootTable(Block block) {
        LootTable.Builder builder = LootTable.lootTable();
        LootPool.Builder pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F));
        LootPoolSingletonContainer.Builder<?> itemBuilder = LootItem.lootTableItem(block);

        for (int i = 2; i <= 16; i++) {
            itemBuilder.apply(SetItemCountFunction.setCount(ConstantValue.exactly(i))
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LayeredBlock.LEVEL, i))));
        }

        pool.add(this.applyExplosionDecay(block, itemBuilder));

        return builder.withPool(pool);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return WoodenBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
