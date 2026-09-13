package MrGise.build_kit.registry.block;

import MrGise.build_kit.BuildKit;
import MrGise.build_kit.types.block.LayeredBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class WoodenBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BuildKit.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BuildKit.MODID);


    public static RegistryObject<Block> OAK_LAYER = registerBlock("oak_layer", () -> new LayeredBlock(Properties.copy(Blocks.OAK_PLANKS)));

    public static RegistryObject<Block> OAK_PLATING = registerBlock("oak_plating", () -> new Block(Properties.copy(Blocks.OAK_PLANKS)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        return registerBlock(name, block, b -> () -> new BlockItem(b.get(), new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, Function<RegistryObject<T>, Supplier<? extends BlockItem>> item) {
        RegistryObject<T> registered = BLOCKS.register(name, block);
        BLOCK_ITEMS.register(name, item.apply(registered));
        return registered;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ITEMS.register(eventBus);
    }
}
