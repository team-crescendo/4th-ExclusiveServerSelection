package kr.enak.crescendo.exclusiveserverselection.bungee.plugin.models;

import kr.enak.crescendo.exclusiveserverselection.engine.models.ServerType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ServerTypeMapper {
    private static final Map<ServerType, ServerInfo> serverInfoMap = new HashMap<>();
    private static final Logger logger = Logger.getLogger(ServerTypeMapper.class.getName());

    static {
        ProxyServer proxyServer = ProxyServer.getInstance();
        Arrays.stream(ServerType.values()).forEach(serverTypeMapper -> {
            ServerInfo serverInfo = proxyServer.getServerInfo(serverTypeMapper.name());
            serverInfoMap.put(serverTypeMapper, serverInfo);

            if (serverInfo == null)
                logger.warning(String.format("ServerInfo %s cannot be detected from server list", serverTypeMapper.name()));
        });
    }

    public static ServerInfo getServerInfo(ServerType serverType) {
        return serverInfoMap.get(serverType);
    }
}
