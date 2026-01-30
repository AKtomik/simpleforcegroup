package io.github.aktomik.voicechatdictator;

import io.github.aktomik.voicechatdictator.brigadier.BrigadierToolbox;
import io.github.aktomik.voicechatdictator.command.HiCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.List;

public final class VoicechatDictator extends JavaPlugin {

    public static final String PLUGIN_ID = "voicechat_dictator";
    public static final Logger LOGGER = LogManager.getLogger(PLUGIN_ID);

    @Nullable
    private VoicechatCop voicechatPlugin;

    @Override
    public void onEnable() {
        voicechatPlugin = new VoicechatCop(this);
        BrigadierToolbox.loadCommands(this, List.of( new HiCommand(this, voicechatPlugin)));
    }

    @Override
    public void onDisable() {
        if (voicechatPlugin != null) {
            getServer().getServicesManager().unregister(voicechatPlugin);
            LOGGER.info("Successfully unregistered Voice Chat Dictator plugin");
        }
    }
}
