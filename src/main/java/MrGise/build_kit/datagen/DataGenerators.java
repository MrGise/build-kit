package MrGise.build_kit.datagen;

import MrGise.build_kit.BuildKit;
import MrGise.build_kit.datagen.data.ModLootTableProvider;
import MrGise.build_kit.datagen.data.WoodRecipeProvider;
import MrGise.build_kit.datagen.model.ModItemModelProvider;
import MrGise.build_kit.datagen.model.WoodenBlockStateProvider;
import MrGise.build_kit.datagen.tag.ModItemTagGenerator;
import MrGise.build_kit.datagen.tag.WoodenBlockTagGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = BuildKit.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        //Recipes
        generator.addProvider(event.includeServer(), new WoodRecipeProvider(output));
        //Loot Tables
        generator.addProvider(event.includeServer(), ModLootTableProvider.create(output));

        //Blocks
        generator.addProvider(event.includeClient(), new WoodenBlockStateProvider(output, exFileHelper));
        //Items
        generator.addProvider(event.includeClient(), new ModItemModelProvider(output, exFileHelper));

        WoodenBlockTagGenerator woodenBlockTagGenerator = generator.addProvider(event.includeServer(),
                new WoodenBlockTagGenerator(output, lookupProvider, exFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagGenerator(output, lookupProvider,
                woodenBlockTagGenerator.contentsGetter(), exFileHelper));
    }
}
