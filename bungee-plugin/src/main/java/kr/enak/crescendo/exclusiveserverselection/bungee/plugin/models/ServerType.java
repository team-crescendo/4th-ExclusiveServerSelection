package kr.enak.crescendo.exclusiveserverselection.bungee.plugin.models;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public enum ServerType {
    LOBBY, WILD, MILD;

    private static final Map<ServerType, ServerInfo> serverInfoMap = new HashMap<>();
    private static final Logger logger = Logger.getLogger(ServerType.class.getName());

    static {
        ProxyServer proxyServer = ProxyServer.getInstance();
        Arrays.stream(values()).forEach(serverType -> {
            ServerInfo serverInfo = proxyServer.getServerInfo(serverType.name());
            serverInfoMap.put(serverType, serverInfo);

            if (serverInfo == null)
                logger.warning(String.format("ServerInfo %s cannot be detected from server list", serverType.name()));
        });
    }

    public ServerInfo getServerInfo() {
        return serverInfoMap.get(this);
    }
}
