package kr.enak.crescendo.exclusiveserverselection.bungee.plugin.handlers;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.handlers.network.PacketSelectServerListener;
import kr.enak.crescendo.exclusiveserverselection.engine.ExSSEngine;
import kr.enak.crescendo.exclusiveserverselection.engine.message.EnumExSSMessage;
import kr.enak.crescendo.exclusiveserverselection.engine.message.abc.IMessage;
import kr.enak.crescendo.exclusiveserverselection.engine.message.abc.IMessageListener;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class NetworkPacketListener implements Listener {
    private static final List<IMessageListener> listeners = new ArrayList<>();
    private final Logger logger = Logger.getLogger(NetworkPacketListener.class.getName());

    public NetworkPacketListener() {
        registerListener(new PacketSelectServerListener());
    }

    public static void registerListener(IMessageListener listener) {
        listeners.add(listener);
    }

    private static IMessage receivePacket(byte[] data) {
        EnumExSSMessage enumMessage = EnumExSSMessage.getById(data[0]);
        ByteArrayDataInput input = ByteStreams.newDataInput(Arrays.copyOfRange(data, 1, data.length));
        Class<?> clazz = enumMessage.getMessageClazz();

        try {
            return ((IMessage) clazz.newInstance()).deserialize(input);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    @EventHandler
    public void onPacketReceived(PluginMessageEvent event) {
        if (!event.getTag().equals(ExSSEngine.pluginChannelName))
            return;


        byte[] data = event.getData();
        byte id = data[0];
        IMessage message = receivePacket(data);

        if (message == null) {
            logger.warning("Ignoring null message");
            return;
        }

        for (IMessageListener listener : listeners) {
            if (listener.getPacketId() != id)
                return;

            try {
                listener.onPacketReceived(null, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
