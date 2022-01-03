package kr.enak.crescendo.exclusiveserverselection.handlers;

import kr.enak.crescendo.exclusiveserverselection.ExclusiveServerSelection;
import kr.enak.crescendo.exclusiveserverselection.data.PlayerData;
import kr.enak.crescendo.exclusiveserverselection.data.ServerData;
import kr.enak.crescendo.exclusiveserverselection.models.ServerType;
import net.md_5.bungee.api.config.ServerInfo;
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

    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        PlayerData playerData = getServerData().getPlayerDataOrCreate(event.getPlayer(), true);
        if (playerData.getUuid() == null)
            playerData.setUuid(event.getPlayer().getUniqueId());

        if (playerData.getServerType() == ServerType.LOBBY) return;

        ServerInfo serverInfo = playerData.getServerType().getServerInfo();
        if (serverInfo != null) {
            logger.info(String.format(
                    "Sending player %s to dedicated server %s",
                    event.getPlayer().getName(),
                    serverInfo.getName()
            ));
            event.getPlayer().setReconnectServer(serverInfo);
            event.getPlayer().connect(serverInfo, ServerConnectEvent.Reason.PLUGIN);
        }
    }
}
