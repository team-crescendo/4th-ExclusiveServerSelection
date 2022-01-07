package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.listeners.game;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ChatListener implements Listener {
    private static ChatListener instance;
    private Set<UUID> crewIdSet = new HashSet<>();

    public ChatListener() {
        instance = this;
    }

    public static ChatListener getInstance() {
        return instance;
    }

    public void setPlayerCrew(UUID uuid) {
        this.crewIdSet.add(uuid);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.getPlayer().isOp())
            event.setFormat("<§4ADMIN§f %1$s> %2$s");
        else if (this.crewIdSet.contains(event.getPlayer().getUniqueId()))
            event.setFormat("<§bCREW§f %1$s> %2$s");
    }
}
