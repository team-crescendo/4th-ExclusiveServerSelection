package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.listeners;

import kr.enak.crescendo.exclusiveserverselection.engine.models.ServerType;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.ExSSSpigotPlugin;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.helper.MessageHelper;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.listeners.game.ChatListener;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.ServerConfigManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.data.PlayerData;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.data.ServerDataManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.services.discord.AuthCodeManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.services.discord.DiscordManager;
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
    private final DiscordManager discordManager;

    public PlayerJoinListener() {
        this.dataManager = TemplatePlugin.getResourceManager(ServerDataManager.class);
        this.configManager = TemplatePlugin.getResourceManager(ServerConfigManager.class);
        this.authCodeManager = TemplatePlugin.getResourceManager(AuthCodeManager.class);
        this.discordManager = TemplatePlugin.getResourceManager(DiscordManager.class);
    }

    @EventHandler
    public void checkIsCrewWhenJoin(PlayerJoinEvent event) {
        if (!this.discordManager.isPlayerCrew(event.getPlayer())) return;

        ExSSSpigotPlugin.getInstance().getLogger().info(String.format(
                "Adding Player name=%s id=%s as CREW",
                event.getPlayer().getName(), event.getPlayer().getUniqueId()
        ));
        ChatListener.getInstance().setPlayerCrew(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void teleportWhenJoin(PlayerJoinEvent event) {
        if (this.configManager.getServerConfig().getServerType() != ServerType.LOBBY) return;

        event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());

        PlayerData playerData = this.dataManager.getServerData().getPlayerDataMap().get(event.getPlayer().getUniqueId());
        if (playerData != null && playerData.getDiscordId() > 0L) return;  // Auth-Completed Player

        String code = this.authCodeManager.enqueueCode(event.getPlayer().getUniqueId());
        MessageHelper.send(event.getPlayer(), "??? ???????????? ??????????????? ?????? ????????? ?????? ?????? ??????(??????)??? ??????????????????.");
        MessageHelper.send(event.getPlayer(), String.format("  > %s <", code));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (this.configManager.getServerConfig().getServerType() != ServerType.LOBBY) return;

        PlayerData playerData = this.dataManager.getServerData().getPlayerDataMap().get(event.getPlayer().getUniqueId());
        if (playerData != null && playerData.getDiscordId() > 0L) return;  // Auth-Completed Player

        event.setCancelled(true);

        String code = this.authCodeManager.enqueueCode(event.getPlayer().getUniqueId());
        event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("?????? ????????? ??????????????????: " + code));
    }
}
