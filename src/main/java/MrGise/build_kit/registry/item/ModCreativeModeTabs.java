package MrGise.build_kit.registry.item;

import MrGise.build_kit.BuildKit;
import MrGise.build_kit.registry.block.StoneBlocks;
import MrGise.build_kit.registry.block.WoodenBlocks;
import net.mcexpanded.fancytabsections.FancyTabSections;
import net.mcexpanded.fancytabsections.Section.SectionTextured;
import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

import static MrGise.build_kit.BuildKit.util;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BuildKit.MODID);


    public static final RegistryObject<CreativeModeTab> MAIN_MOD_TAB = registerTab("build_kit_main",
            () -> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 1)
            .icon(() -> util.itemUtil().toStack(WoodenBlocks.OAK_PLATING)).title(util.keyUtil().defaultCMTTitle("build_kit_main"))
            .withBackgroundLocation(util.resourceUtil().withId("textures/gui/creative_tab/wood_blocks.png"))
            .displayItems((params, output) -> output.accept(Items.BARRIER)).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);

        eventBus.addListener(ModCreativeModeTabs::onCommonSetup);
    }

    private static final ResourceLocation WOOD_ITEMS_SECT = util.resourceUtil().withId("wood_items");
    private static final ResourceLocation STONE_ITEMS_SECT = util.resourceUtil().withId("stone_items");

    private static void registerTabSections() {
        FancyTabSections.addSection(MAIN_MOD_TAB.getId(),
                new SectionTextured(WOOD_ITEMS_SECT, util.keyUtil().withId("fancy_tab_section", "wood_items"),
                util.resourceUtil().tabSection("wood"), 0xC7A86F, true, false,
                new ConglomerateOfItems()
                        .add(List.of(
                                util.itemUtil().toStack(WoodenBlocks.OAK_PLATING.get()),
                                util.itemUtil().toStack(WoodenBlocks.OAK_LAYER.get())
                                )
                        ))
        );
        FancyTabSections.addSection(MAIN_MOD_TAB.getId(),
                new SectionTextured(WOOD_ITEMS_SECT, util.keyUtil().withId("fancy_tab_section", "stone_items"),
                util.resourceUtil().tabSection("stone"), 0x35353C, true, false,
                new ConglomerateOfItems()
                        .add(List.of(
                                util.itemUtil().toStack(StoneBlocks.STONE_TILE.get())
                                )
                        )).setTextOutline(0x7E7E8C)
        );
    }

    private static RegistryObject<CreativeModeTab> registerTab(String name, Supplier<? extends CreativeModeTab> tab) {
        return CREATIVE_MODE_TABS.register(name, tab);
    }

    private static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ModCreativeModeTabs::registerTabSections);
    }
}
