package kr.enak.crescendo.exclusiveserverselection.bungee.plugin.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageHelper {
    public static String send(CommandSender player, String message, Object... objects) {
        ArrayList<Object> objectList = new ArrayList<>(Arrays.asList(objects));
        String sending = String.format(message, objectList.toArray(new Object[0]));

        player.sendMessage(ChatColor.DARK_BLUE + "[Team Crescendo] " + ChatColor.RESET + sending);

        return sending;
    }
}
