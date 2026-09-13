package MrGise.build_kit.datagen.data;

import MrGise.build_kit.registry.block.WoodenBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.Map;
import java.util.function.Consumer;

import static MrGise.build_kit.BuildKit.util;

public class WoodRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public WoodRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        woodenPlate(pWriter, RecipeCategory.BUILDING_BLOCKS, WoodenBlocks.OAK_LAYER.get(), Blocks.OAK_PRESSURE_PLATE,
                "wooden_blocks", Ingredient.of(Items.OAK_PRESSURE_PLATE));
    }

    private void woodenPlate(Consumer<FinishedRecipe> pWriter, RecipeCategory category, ItemLike result,
                             ItemLike unlockedBy, String group, Ingredient pressurePlate) {
        shapedRecipe(pWriter, category, result, unlockedBy, group, Map.of(pressurePlate, '#'), "##");
    }

    private void shapedRecipe(Consumer<FinishedRecipe> pWriter, RecipeCategory category, ItemLike result, int count,
                              ItemLike unlockedBy, String group, String identifier,
                              Map<Ingredient, Character> ingredients, String... pattern) {
        if (pattern.length > 3 || pattern.length < 1) {
            throw new IllegalArgumentException("Shaped recipe pattern cannot contain more than 3 rows or less than 1 row, but it was provided with " + pattern.length + " rows!");
        }
        for (String row : pattern) {
            if (row.length() > 3 || row.isEmpty()) {
                throw new IllegalArgumentException("Shaped recipe pattern rows cannot contain more than 3 characters or less than 1 character, but it was provided with " + row.length() + " characters!");
            }
        }

        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(category, result, count);

        for (String row : pattern) {builder.pattern(row);}
        for (Ingredient key : ingredients.keySet()) {builder.define(ingredients.get(key), key);}

        builder.unlockedBy(getHasName(unlockedBy), has(unlockedBy)).group(group);

        builder.save(pWriter, util.resourceUtil().recipeLoc(result, "from_crafting" + identifier));
    }
    private void shapedRecipe(Consumer<FinishedRecipe> pWriter, RecipeCategory category, ItemLike result, int count,
                              ItemLike unlockedBy, String group,
                              Map<Ingredient, Character> ingredients, String... pattern) {
        shapedRecipe(pWriter, category, result, count, unlockedBy, group, "", ingredients, pattern);
    }
    private void shapedRecipe(Consumer<FinishedRecipe> pWriter, RecipeCategory category, ItemLike result,
                              ItemLike unlockedBy, String group, String identifier,
                              Map<Ingredient, Character> ingredients, String... pattern) {
        shapedRecipe(pWriter, category, result, 1, unlockedBy, group, identifier, ingredients, pattern);
    }
    private void shapedRecipe(Consumer<FinishedRecipe> pWriter, RecipeCategory category, ItemLike result,
                              ItemLike unlockedBy, String group,
                              Map<Ingredient, Character> ingredients, String... pattern) {
        shapedRecipe(pWriter, category, result, 1, unlockedBy, group, ingredients, pattern);
    }
}
