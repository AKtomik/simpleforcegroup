package io.github.aktomik.voicechatdictator;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;

public final class VoicechatDictator extends JavaPlugin {

    public static final String PLUGIN_ID = "voicechat_dictator";
    public static final Logger LOGGER = LogManager.getLogger(PLUGIN_ID);

    @Nullable
    private VoiceChatInteraction voicechatPlugin;

    @Override
    public void onEnable() {
        BukkitVoicechatService service = getServer().getServicesManager().load(BukkitVoicechatService.class);
        if (service != null) {
            voicechatPlugin = new VoiceChatInteraction();
            service.registerPlugin(voicechatPlugin);
            LOGGER.info("Successfully registered example plugin");
        } else {
            LOGGER.info("Failed to register example plugin");
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
