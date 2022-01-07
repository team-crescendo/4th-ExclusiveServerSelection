package kr.enak.crescendo.exclusiveserverselection.bungee.plugin.utils;

import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.ExclusiveServerSelection;
import kr.enak.crescendo.exclusiveserverselection.engine.ExSSEngine;
import kr.enak.crescendo.exclusiveserverselection.engine.message.abc.IMessage;
import net.md_5.bungee.api.config.ServerInfo;

public class PacketSender {
    public static void sendPacket(ServerInfo serverInfo, IMessage message) {
        byte[] source = message.serialize().toByteArray();
        byte[] out = new byte[source.length + 1];
        out[0] = message.getMessageId().getId();
        System.arraycopy(source, 0, out, 1, source.length);

        ExclusiveServerSelection.getInstance().getLogger().info(String.format(
                "Sending packet id=%s class=%s to server=%s",
                message.getMessageId(),
                message.getClass().getName(),
                serverInfo.getName()
        ));
        serverInfo.sendData(ExSSEngine.pluginChannelName, out);
    }
}
