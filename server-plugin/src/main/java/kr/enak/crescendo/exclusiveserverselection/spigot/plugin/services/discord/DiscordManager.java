package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.services.discord;

import kr.enak.crescendo.exclusiveserverselection.engine.models.ServerType;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.ExSSSpigotPlugin;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.DiscordConfig;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.ServerConfigManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.data.PlayerData;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.data.ServerDataManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.services.discord.listeners.AuthMessageListener;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.services.discord.listeners.FetchRoleListener;
import kr.enak.plugintemplate.TemplatePlugin;
import kr.enak.plugintemplate.models.DefaultResourceManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.UUID;

public class DiscordManager extends DefaultResourceManager {
    private final ExSSSpigotPlugin plugin;
    private JDA jda;
    private ServerConfigManager configManager;
    private ServerDataManager dataManager;

    private Guild guild;
    private Role adminRole;
    private Role wildRole;
    private Role mildRole;

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
        this.dataManager = TemplatePlugin.getResourceManager(ServerDataManager.class);

        DiscordConfig discordConfig = this.configManager.getServerConfig().getDiscordConfig();
        JDABuilder builder = JDABuilder.createDefault(discordConfig.getToken())
                .setEnabledIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                .setChunkingFilter(ChunkingFilter.ALL)
                .enableCache(CacheFlag.VOICE_STATE)
                .addEventListeners(new FetchRoleListener())
                .setMemberCachePolicy(MemberCachePolicy.ALL);

        if (configManager.getServerConfig().getServerType() == ServerType.LOBBY) {
            this.plugin.getLogger().info("Injecting LOBBY listeners");
            builder.addEventListeners(
                    new AuthMessageListener()
            );
        }

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

    public void applyRole(UUID uuid, ServerType serverType) {
        this.plugin.getLogger().info(String.format("Applying role to %s as server %s", uuid, serverType));
        if (serverType == ServerType.LOBBY) return;

        PlayerData playerData = this.dataManager.getServerData().getPlayerDataMap().get(uuid);
        long discordId = playerData.getDiscordId();

        if (serverType == ServerType.WILD)
            this.guild.addRoleToMember(discordId, wildRole).queue();
        else if (serverType == ServerType.MILD)
            this.guild.addRoleToMember(discordId, mildRole).queue();
        else return;
    }

    public void setGuild(Guild guild) {
        this.plugin.getLogger().info(String.format("GUILD=%s", guild.toString()));
        this.guild = guild;
    }

    public void setAdminRole(Role adminRole) {
        this.plugin.getLogger().info(String.format("ADMIN=%s", adminRole.toString()));
        this.adminRole = adminRole;
    }

    public void setMildRole(Role mildRole) {
        this.plugin.getLogger().info(String.format("MILD=%s", mildRole.toString()));
        this.mildRole = mildRole;
    }

    public void setWildRole(Role wildRole) {
        this.plugin.getLogger().info(String.format("WILD=%s", wildRole.toString()));
        this.wildRole = wildRole;
    }
}
