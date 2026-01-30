package io.github.aktomik.voicechatdictator;

import de.maxhenkel.voicechat.api.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public class VoicechatCop implements VoicechatPlugin {


    private final JavaPlugin plugin;
    private VoicechatServerApi serverApi;

    public VoicechatCop(JavaPlugin plugin) {
        this.plugin = plugin;
        initializeApi();
    }

    private void initializeApi() {
        try {
            var reg = Bukkit.getServicesManager().getRegistration(VoicechatServerApi.class);
            if (reg != null) {
                serverApi = reg.getProvider();
                plugin.getLogger().info("VoiceChat API found via ServiceManager.");
            } else {
                var svc = Bukkit.getServicesManager().getRegistration(BukkitVoicechatService.class);
                if (svc != null) {
                    svc.getProvider().registerPlugin(this);
                    plugin.getLogger().info("Registered VoiceChatInteraction as VoiceChatPlugin.");
                }
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to initialize VoiceChat API: " + e.getMessage());
        }
    }

    @Override
    public String getPluginId() {
        return VoicechatDictator.PLUGIN_ID;
    }

    /**
     * Called when the voice chat initializes the plugin.
     *
     * @param api the voice chat API
     */
    @Override
    public void initialize(VoicechatApi api) {
            if (api instanceof VoicechatServerApi server) {
                this.serverApi = server;
                plugin.getLogger().info("VoiceChat API initialized successfully.");
            } else {
                plugin.getLogger().warning("VoiceChat API not compatible.");
            }
    }

    public boolean isAvailable() {
        return serverApi != null;
    }

    public Group getGroup(String groupName)
    {
        List<Group> groups = serverApi.getGroups().stream().filter(group -> Objects.equals(group.getName(), groupName)).toList();
        if (groups.isEmpty()) return null;
        return groups.getFirst();
    }

    public void addPlayerToGroup(Player player, Group group)
    {
        VoicechatConnection connection = serverApi.getConnectionOf(player.getUniqueId());
        assert connection != null;
        connection.setGroup(group);
    }

    public void removePlayerGroup(Player player)
    {
        VoicechatConnection connection = serverApi.getConnectionOf(player.getUniqueId());
		assert connection != null;
		connection.setGroup(null);
    }
}
