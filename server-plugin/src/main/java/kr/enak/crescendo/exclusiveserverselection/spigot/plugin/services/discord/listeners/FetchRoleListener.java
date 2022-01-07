package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.services.discord.listeners;

import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.DiscordConfig;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.ServerConfigManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.services.discord.DiscordManager;
import kr.enak.plugintemplate.TemplatePlugin;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class FetchRoleListener extends ListenerAdapter {
    private final ServerConfigManager configManager;
    private final DiscordManager discordManager;

    public FetchRoleListener() {
        super();

        this.configManager = TemplatePlugin.getResourceManager(ServerConfigManager.class);
        this.discordManager = TemplatePlugin.getResourceManager(DiscordManager.class);
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        super.onReady(event);

        DiscordConfig config = this.configManager.getServerConfig().getDiscordConfig();

        {
            Guild guild = this.discordManager.getJda().getGuildById(config.getGuildId());
            this.discordManager.setGuild(guild);
        }
        {
            Role adminRole = this.discordManager.getJda().getRoleById(config.getAdminRoleId());
            this.discordManager.setAdminRole(adminRole);
        }
        {
            Role wildRole = this.discordManager.getJda().getRoleById(config.getWildRoleId());
            this.discordManager.setWildRole(wildRole);
        }
        {
            Role mildRole = this.discordManager.getJda().getRoleById(config.getMildRoleId());
            this.discordManager.setMildRole(mildRole);
        }
    }
}
