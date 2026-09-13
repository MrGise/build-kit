package MrGise.build_kit.registry.item;

import MrGise.build_kit.BuildKit;
import MrGise.build_kit.registry.block.WoodenBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static MrGise.build_kit.BuildKit.util;

public class ModCreativeModeTabs {
    public static DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BuildKit.MODID);


    public static RegistryObject<CreativeModeTab> WOODEN_TAB = register("wooden_blocks",
            () -> new CreativeModeTab.Builder(CreativeModeTab.Row.BOTTOM, 1).title(util.keyUtil().defaultCMTTitle("wooden_blocks"))
                    .withBackgroundLocation(util.resourceUtil().withId("textures/gui/creative_tab/wood_blocks.png"))
                    .withSlotColor(2)
                    .icon(() -> new ItemStack(WoodenBlocks.OAK_LAYER.get())).displayItems((params, output) -> {
                        output.accept(WoodenBlocks.OAK_PLATING.get());
                        output.accept(WoodenBlocks.OAK_LAYER.get());
                    }).build());


    private static RegistryObject<CreativeModeTab> register(String name, Supplier<? extends CreativeModeTab> sup) {
        return CREATIVE_MODE_TABS.register(name, sup);
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
