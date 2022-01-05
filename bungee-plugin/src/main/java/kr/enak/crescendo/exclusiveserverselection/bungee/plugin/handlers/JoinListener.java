package kr.enak.crescendo.exclusiveserverselection.bungee.plugin.handlers;

import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.ExclusiveServerSelection;
import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.data.PlayerData;
import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.data.ServerData;
import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.models.ServerTypeMapper;
import kr.enak.crescendo.exclusiveserverselection.engine.models.ServerType;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.logging.Logger;

public class JoinListener implements Listener {
    private final Logger logger = Logger.getLogger(JoinListener.class.getName());

    private ServerData getServerData() {
        return ExclusiveServerSelection.getServerData();
    }

    @EventHandler
    public void onPreLogin(PreLoginEvent event) {

    }

    @EventHandler
    public void onLogin(LoginEvent event) {
        PendingConnection connection = event.getConnection();
        String username = connection.getName();
        if (username == null) {
            logger.warning(String.format("Rejecting incoming connection %s with unknown name", connection));

            event.setCancelReason(new TextComponent("Unknown connection name"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        PlayerData playerData = getServerData().getPlayerDataOrCreate(event.getPlayer(), true);
        if (playerData.getUuid() == null)
            playerData.setUuid(event.getPlayer().getUniqueId());

        if (playerData.getServerType() == ServerType.LOBBY) return;

        ServerInfo serverInfo = ServerTypeMapper.getServerInfo(playerData.getServerType());
        if (serverInfo != null) {
            logger.info(String.format(
                    "Sending player %s to dedicated server %s",
                    event.getPlayer().getName(),
                    serverInfo.getName()
            ));
            event.getPlayer().setReconnectServer(serverInfo);

            if (event.getPlayer().getReconnectServer() != serverInfo)
                event.getPlayer().connect(serverInfo, ServerConnectEvent.Reason.PLUGIN);
        }
    }
}
