package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.helper;

import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.ExSSSpigotPlugin;
import org.bukkit.command.CommandSender;

public class MessageHelper {
    public static void send(CommandSender player, String message) {
        player.sendMessage(ExSSSpigotPlugin.getInstance().getPrefix() + message);
    }
}
