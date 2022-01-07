package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.listeners;

import kr.enak.crescendo.exclusiveserverselection.engine.models.ServerType;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.helper.MessageHelper;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.ServerConfigManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.data.PlayerData;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.data.ServerDataManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.services.discord.AuthCodeManager;
import kr.enak.plugintemplate.TemplatePlugin;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerJoinListener implements Listener {
    private final ServerDataManager dataManager;
    private final ServerConfigManager configManager;
    private final AuthCodeManager authCodeManager;

    public PlayerJoinListener() {
        this.dataManager = TemplatePlugin.getResourceManager(ServerDataManager.class);
        this.configManager = TemplatePlugin.getResourceManager(ServerConfigManager.class);
        this.authCodeManager = TemplatePlugin.getResourceManager(AuthCodeManager.class);
    }

    @EventHandler
    public void teleportWhenJoin(PlayerJoinEvent event) {
        if (this.configManager.getServerConfig().getServerType() != ServerType.LOBBY) return;

        event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());

        PlayerData playerData = this.dataManager.getServerData().getPlayerDataMap().get(event.getPlayer().getUniqueId());
        if (playerData != null && playerData.getDiscordId() > 0L) return;  // Auth-Completed Player

        String code = this.authCodeManager.enqueueCode(event.getPlayer().getUniqueId());
        MessageHelper.send(event.getPlayer(), "팀 크레센도 디스코드의 인증 채널에 아래 인증 코드(숫자)를 입력해주세요.");
        MessageHelper.send(event.getPlayer(), String.format("  > %s <", code));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (this.configManager.getServerConfig().getServerType() != ServerType.LOBBY) return;

        PlayerData playerData = this.dataManager.getServerData().getPlayerDataMap().get(event.getPlayer().getUniqueId());
        if (playerData != null && playerData.getDiscordId() > 0L) return;  // Auth-Completed Player

        event.setCancelled(true);

        String code = this.authCodeManager.enqueueCode(event.getPlayer().getUniqueId());
        event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("인증 코드를 입력해주세요: " + code));
    }
}
