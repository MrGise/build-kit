package MrGise.build_kit.util;

public class UtilCollection {
    private final String modid;
    private final TranslationKeyUtil transKeyUtil;
    private final ResourceLocationUtil resLocUtil;

    public UtilCollection(String modid) {
        this.modid = modid;
        this.transKeyUtil = new TranslationKeyUtil(modid);
        this.resLocUtil = new ResourceLocationUtil(modid);
    }

    public ResourceLocationUtil resourceUtil() {
        return this.resLocUtil;
    }
    public ResourceLocationUtil.Affix affix() {
        return this.resLocUtil.affix();
    }

    public TranslationKeyUtil keyUtil() {
        return this.transKeyUtil;
    }

}
