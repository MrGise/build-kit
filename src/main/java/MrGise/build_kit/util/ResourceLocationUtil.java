package MrGise.build_kit.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class ResourceLocationUtil {
    private final String modid;
    private final Affix affix;

    public ResourceLocationUtil(String modid) {
        this.modid = modid;
        this.affix = new Affix();
    }

    public Affix affix() {return this.affix;}

    public ResourceLocation namespaceAndPath(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
    public ResourceLocation withId(String path) {
        return namespaceAndPath(this.modid, path);
    }

    public ResourceLocation defaultId(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }


    public String blockPath(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }
    public String blockId(Block block) {
        return blockPath(block).replace('/', '_');
    }
    public String itemPath(ItemLike item) {
        return ForgeRegistries.ITEMS.getKey(item.asItem()).getPath();
    }
    public String itemId(ItemLike item) {
        return itemPath(item).replace('/', '_');
    }

    public ResourceLocation recipeBase(ItemLike item) {
        return withId(itemId(item));
    }
    public ResourceLocation recipeLoc(String prefix, ItemLike crafted, String suffix) {
        return affix().dirAffixes(recipeBase(crafted), prefix, suffix);
    }
    public ResourceLocation recipeLoc(ItemLike crafted, String suffix) {
        return affix().addSuffix(recipeBase(crafted), suffix);
    }
    public ResourceLocation recipeLoc(String prefix, ItemLike crafted) {
        return affix().addPrefix(recipeBase(crafted), prefix);
    }

    public String recipeId(ItemLike crafted, String recipePrefix, ItemLike required, String recipeSuffix) {
        return recipeId(crafted, recipePrefix, required) + "_" + recipeSuffix;
    }
    public String recipeId(ItemLike crafted, String recipePrefix, ItemLike required) {
        return this.modid + ":" + itemId(crafted) + "_" + recipePrefix + "_" + itemId(required);
    }


    public class Affix {
        private Affix() {}

        public ResourceLocation dirPrefix(ResourceLocation location, String prefix) {
            return validatePopulation(location, prefix, namespaceAndPath(location.getNamespace(), prefix + "/" + location.getPath()));
        }
        public ResourceLocation addPrefix(ResourceLocation location, String prefix) {
            return validatePopulation(location, prefix, namespaceAndPath(location.getNamespace(), prefix + "_" + location.getPath()));
        }

        public ResourceLocation dirSuffix(ResourceLocation location, String suffix) {
            return validatePopulation(location, suffix, namespaceAndPath(location.getNamespace(), location.getPath() + "/" + suffix));
        }
        public ResourceLocation addSuffix(ResourceLocation location, String suffix) {
            return validatePopulation(location, suffix, namespaceAndPath(location.getNamespace(), location.getPath() + "_" + suffix));
        }


        public ResourceLocation dirAffixes(ResourceLocation location, String prefix, String suffix) {
            return validatePopulation(location, prefix, suffix,
                    namespaceAndPath(location.getNamespace(), prefix + "/" + location.getPath() + "/" + suffix));
        }
        public ResourceLocation addAffixes(ResourceLocation location, String prefix, String suffix) {
            return validatePopulation(location, prefix, suffix,
                    namespaceAndPath(location.getNamespace(), prefix + "_" + location.getPath() + "_" + suffix));
        }

        private ResourceLocation validatePopulation(ResourceLocation location, String checked, ResourceLocation modified) {
            return !checked.isBlank() ? modified : location;
        }
        private ResourceLocation validatePopulation(ResourceLocation location, String checked, String alsoChecked, ResourceLocation modified) {
            return !checked.isBlank() && !alsoChecked.isBlank() ? modified : location;
        }
    }
}
