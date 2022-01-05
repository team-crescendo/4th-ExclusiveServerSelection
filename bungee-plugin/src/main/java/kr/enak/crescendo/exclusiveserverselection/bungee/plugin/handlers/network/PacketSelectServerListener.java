package kr.enak.crescendo.exclusiveserverselection.bungee.plugin.handlers.network;

import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.ExclusiveServerSelection;
import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.data.PlayerData;
import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.models.ServerTypeMapper;
import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.utils.MessageHelper;
import kr.enak.crescendo.exclusiveserverselection.engine.message.EnumExSSMessage;
import kr.enak.crescendo.exclusiveserverselection.engine.message.abc.IMessage;
import kr.enak.crescendo.exclusiveserverselection.engine.message.abc.IMessageListener;
import kr.enak.crescendo.exclusiveserverselection.engine.message.request.SpigotRequestSelectServer;
import kr.enak.crescendo.exclusiveserverselection.engine.models.ServerType;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PacketSelectServerListener implements IMessageListener {
    @Override
    public byte getPacketId() {
        return EnumExSSMessage.SPIGOT_REQUEST_SELECT_SERVER.getId();
    }

    @Override
    public void onPacketReceived(Object obj, IMessage message) {
        SpigotRequestSelectServer packet = (SpigotRequestSelectServer) message;

        ProxiedPlayer player = ExclusiveServerSelection.getInstance().getProxy().getPlayer(packet.getUsername());
        PlayerData playerData = ExclusiveServerSelection.getServerData().getPlayerDataOrCreate(player.getName(), true);
        ServerType serverType = packet.getServerType();
        ServerInfo serverInfo = ServerTypeMapper.getServerInfo(serverType);

        if (player.getServer().getInfo() == serverInfo) {
            MessageHelper.send(player, "/경고/ 이미 서버에 연결되어 있습니다.");
        } else if (playerData.getServerType() == ServerType.LOBBY) {
            MessageHelper.send(player, "/성공/ %s 서버로 이동합니다.", serverType.name());
            playerData.setServerType(serverType);
            player.setReconnectServer(serverInfo);
            player.connect(serverInfo);
        } else {
            MessageHelper.send(player, "/경고/ 서버를 이미 선택하여 변경할 수 없습니다.");
        }
    }
}
