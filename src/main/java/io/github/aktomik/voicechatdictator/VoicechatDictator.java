package io.github.aktomik.voicechatdictator;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;

public final class VoicechatDictator extends JavaPlugin {

    public static final String PLUGIN_ID = "voicechat_dictator";
    public static final Logger LOGGER = LogManager.getLogger(PLUGIN_ID);

    private static VoicechatDictator instance;

    @Nullable
    private VoiceChatInteraction voicechatPlugin;

    @Override
    public void onEnable() {
        BukkitVoicechatService service = getServer().getServicesManager().load(BukkitVoicechatService.class);
        instance = this;
        if (service != null) {
            voicechatPlugin = new VoiceChatInteraction(this);
            service.registerPlugin(voicechatPlugin);
            LOGGER.info("Successfully registered voice chat dictator plugin");
        } else {
            LOGGER.info("Failed to register voice chat dictator plugin");
        }
    }

    @Override
    public void onDisable() {
        if (voicechatPlugin != null) {
            getServer().getServicesManager().unregister(voicechatPlugin);
            LOGGER.info("Successfully unregistered example plugin");
        }
    }
}
