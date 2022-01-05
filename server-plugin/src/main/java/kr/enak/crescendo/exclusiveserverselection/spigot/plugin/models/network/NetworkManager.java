package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import kr.enak.crescendo.exclusiveserverselection.engine.ExSSEngine;
import kr.enak.crescendo.exclusiveserverselection.engine.message.EnumExSSMessage;
import kr.enak.crescendo.exclusiveserverselection.engine.message.abc.IMessage;
import kr.enak.crescendo.exclusiveserverselection.engine.message.abc.IMessageListener;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.ExSSSpigotPlugin;
import kr.enak.plugintemplate.TemplatePlugin;
import kr.enak.plugintemplate.models.DefaultResourceManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class NetworkManager extends DefaultResourceManager implements PluginMessageListener {
    private static final List<IMessageListener> listeners = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(NetworkManager.class.getName());

    public NetworkManager() {
        this(ExSSSpigotPlugin.getInstance());
    }

    public NetworkManager(TemplatePlugin plugin) {
        super(plugin);
    }

    public static IMessage receiveMessage(byte[] data) {
        byte id = data[0];
        EnumExSSMessage enumMessage = EnumExSSMessage.getById(id);

        ByteArrayDataInput input = ByteStreams.newDataInput(Arrays.copyOfRange(data, 1, data.length));
        Class<?> clazz = enumMessage.getMessageClazz();

        try {
            return ((IMessage) clazz.newInstance()).deserialize(input);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void registerListener(IMessageListener listener) {
        listeners.add(listener);
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] data) {
        if (!channel.equals(ExSSEngine.pluginChannelName)) return;

        byte id = data[0];
        IMessage message = receiveMessage(data);

        for (IMessageListener listener : listeners) {
            try {
                listener.onPacketReceived(player, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(Player player, IMessage packet) {
        System.out.printf("Sending packet player=%s, packet=%s%n", player.getName(), packet.toString());

        byte[] source = packet.serialize().toByteArray();
        byte[] out = new byte[source.length + 1];
        out[0] = packet.getMessageId().getId();
        System.arraycopy(source, 0, out, 1, source.length);

        player.sendPluginMessage(
                ExSSSpigotPlugin.getInstance(),
                ExSSEngine.pluginChannelName,
                out
        );
    }
}
