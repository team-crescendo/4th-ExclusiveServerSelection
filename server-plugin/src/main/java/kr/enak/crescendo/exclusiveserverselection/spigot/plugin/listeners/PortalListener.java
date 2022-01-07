package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.listeners;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import kr.enak.crescendo.exclusiveserverselection.engine.message.request.SpigotRequestSelectServer;
import kr.enak.crescendo.exclusiveserverselection.engine.models.ServerType;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.ServerConfig;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.ServerConfigManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.network.NetworkManager;
import kr.enak.plugintemplate.TemplatePlugin;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.logging.Logger;

public class PortalListener implements Listener {
    private final @NotNull ServerConfigManager configManager;
    private final @NotNull NetworkManager networkManager;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final Map<UUID, Date> pendingPlayers = new HashMap<>();

    public PortalListener(TemplatePlugin plugin) {
        this();
    }

    public PortalListener() {
        this.configManager = Objects.requireNonNull(TemplatePlugin.getResourceManager(ServerConfigManager.class));
        this.networkManager = Objects.requireNonNull(TemplatePlugin.getResourceManager(NetworkManager.class));
    }

    @EventHandler
    public void checkOnPlayerMove(PlayerMoveEvent event) {
        if (configManager.getServerConfig().getServerType() != ServerType.LOBBY) return;

        if (this.pendingPlayers.containsKey(event.getPlayer().getUniqueId())
                && (new Date().getTime() - this.pendingPlayers.get(event.getPlayer().getUniqueId()).getTime()) <= 5000) {
            event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("서버의 응답을 기다리는 중입니다."));
            event.setCancelled(true);
            return;
        }

        ServerConfig config = configManager.getServerConfig();
        CuboidRegion wildPortal = config.getWildPortalRegion();
        CuboidRegion mildPortal = config.getMildPortalRegion();

        if (wildPortal.contains(BlockVector3.at(
                event.getPlayer().getLocation().getX(),
                event.getPlayer().getLocation().getY(),
                event.getPlayer().getLocation().getZ()
        ))) {
            sendPlayerToServer(event.getPlayer(), ServerType.WILD);
        } else if (mildPortal.contains(BlockVector3.at(
                event.getPlayer().getLocation().getX(),
                event.getPlayer().getLocation().getY(),
                event.getPlayer().getLocation().getZ()
        ))) {
            sendPlayerToServer(event.getPlayer(), ServerType.MILD);
        }
    }

    private void sendPlayerToServer(Player player, ServerType serverType) {
        logger.info(String.format("Sending player %s to server %s", player.getName(), serverType.name()));
        restrictPlayer(player);
        this.networkManager.sendMessage(player, new SpigotRequestSelectServer(
                player.getName(), serverType
        ));
    }

    private void restrictPlayer(Player player) {
        this.pendingPlayers.put(player.getUniqueId(), new Date());
    }
}
