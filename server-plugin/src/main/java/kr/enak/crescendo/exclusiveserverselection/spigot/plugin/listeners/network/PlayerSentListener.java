package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.listeners.network;

import kr.enak.crescendo.exclusiveserverselection.engine.message.EnumExSSMessage;
import kr.enak.crescendo.exclusiveserverselection.engine.message.abc.IMessage;
import kr.enak.crescendo.exclusiveserverselection.engine.message.abc.IMessageListener;
import kr.enak.crescendo.exclusiveserverselection.engine.message.response.BungeeSentPlayer;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.services.discord.DiscordManager;
import kr.enak.plugintemplate.TemplatePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerSentListener implements IMessageListener {
    private final DiscordManager discordManager;

    public PlayerSentListener() {
        this.discordManager = TemplatePlugin.getResourceManager(DiscordManager.class);
    }

    @Override
    public byte getPacketId() {
        return EnumExSSMessage.BUNGEE_SENT_PLAYER.getId();
    }

    @Override
    public void onPacketReceived(Object obj, IMessage message) {
        BungeeSentPlayer packet = (BungeeSentPlayer) message;

        Player player = Bukkit.getPlayer(packet.getUsername());
        this.discordManager.applyRole(player.getUniqueId(), packet.getServerType());
    }
}
