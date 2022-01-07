package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.services.discord;

import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.ExSSSpigotPlugin;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.DiscordConfig;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.ServerConfigManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.services.discord.listeners.AuthMessageListener;
import kr.enak.plugintemplate.TemplatePlugin;
import kr.enak.plugintemplate.models.DefaultResourceManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class DiscordManager extends DefaultResourceManager {
    private final ExSSSpigotPlugin plugin;
    private JDA jda;
    private ServerConfigManager configManager;

    public DiscordManager(TemplatePlugin plugin) {
        super(plugin);
        this.plugin = (ExSSSpigotPlugin) plugin;
    }

    @Override
    public TemplatePlugin getPlugin() {
        return this.plugin;
    }

    @Override
    public void onInit() {
        this.configManager = TemplatePlugin.getResourceManager(ServerConfigManager.class);

        DiscordConfig discordConfig = this.configManager.getServerConfig().getDiscordConfig();
        JDABuilder builder = JDABuilder.createDefault(discordConfig.getToken())
                .setEnabledIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                .setChunkingFilter(ChunkingFilter.ALL)
                .enableCache(CacheFlag.VOICE_STATE)
                .addEventListeners(
                        new AuthMessageListener()
                )
                .setMemberCachePolicy(MemberCachePolicy.ALL);

        try {
            this.jda = builder.build();
            JDAHolder.jda = this.jda;
        } catch (LoginException e) {
            e.printStackTrace();

            setStatus(ManagerStatus.ERROR);
            return;
        }

        setStatus(ManagerStatus.INITIALIZED);
    }

    @Override
    public void onRun() {
        super.onRun();
    }

    @Override
    public void onSave() {
        super.onSave();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public JDA getJda() {
        return jda;
    }
}
