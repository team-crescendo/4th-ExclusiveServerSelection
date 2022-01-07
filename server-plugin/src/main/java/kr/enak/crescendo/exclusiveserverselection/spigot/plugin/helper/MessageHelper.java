package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.helper;

import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.ExSSSpigotPlugin;
import org.bukkit.entity.Player;

public class MessageHelper {
    public static void send(Player player, String message) {
        player.sendMessage(ExSSSpigotPlugin.getInstance().getPrefix() + message);
    }
}
