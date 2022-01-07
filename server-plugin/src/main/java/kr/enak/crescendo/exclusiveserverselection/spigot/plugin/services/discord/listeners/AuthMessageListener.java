package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.services.discord.listeners;

import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.DiscordConfig;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.ServerConfigManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.services.discord.AuthCodeManager;
import kr.enak.plugintemplate.TemplatePlugin;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class AuthMessageListener extends ListenerAdapter {
    private final ServerConfigManager configManager;
    private final AuthCodeManager authCodeManager;

    public AuthMessageListener() {
        super();

        this.configManager = TemplatePlugin.getResourceManager(ServerConfigManager.class);
        this.authCodeManager = TemplatePlugin.getResourceManager(AuthCodeManager.class);
    }

    private DiscordConfig getConfig() {
        return this.configManager.getServerConfig().getDiscordConfig();
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String code = event.getMessage().getContentRaw();

        if (event.getChannel().getIdLong() != getConfig().getVerificationChannelId()) return;
        else if (code.length() != getConfig().getAuthCodeLength()) return;

        // Assert auth code
        try {
            Integer.parseInt(code);
        } catch (NumberFormatException ex) {
            return;
        }

        UUID uuid = this.authCodeManager.tryUseAuthCode(code);
        if (uuid == null) {  // Invalid Auth Code
            return;
        }

        if (!authCodeManager.completeAuth(code, Objects.requireNonNull(event.getMember()).getUser())) {
            event.getMessage().reply("인증에 실패했습니다. 재시도해주세요.").queue();
            return;
        }

        event.getMessage().addReaction("⭕").queue();
    }
}
