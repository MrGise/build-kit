package MrGise.build_kit.util;

import net.minecraft.network.chat.Component;

public class TranslationKeyUtil {
    private String modid;

    private static final String keyTemplate = "%type%.%mod_id%.%name%";

    public TranslationKeyUtil(String modid) {
        this.modid = modid;
    }

    public Component defaultCMTTitle(String name) {
        return Component.translatable(keyTemplate.replace("%type%", "creative_mode_tab").replace("%mod_id%", modid).replace("%name%", name));
    }
}
