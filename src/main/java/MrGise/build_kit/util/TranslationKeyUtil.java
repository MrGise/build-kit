package MrGise.build_kit.util;

import net.minecraft.network.chat.Component;

public class TranslationKeyUtil {
    private String modid;

    private static final String keyTemplate = "%type%.%mod_id%.%name%";

    public TranslationKeyUtil(String modid) {
        this.modid = modid;
    }

    public Component withTemplate(String type, String modid, String name) {
        return Component.translatable(keyTemplate.replace("%type%", type).replace("%mod_id%", modid).replace("%name%", name));
    }

    public Component defaultCMTTitle(String name) {
        return withId("creative_mode_tab", name);
    }

    public Component withId(String type, String name) {
        return withTemplate(type, this.modid, name);
    }
}
