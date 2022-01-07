package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.listeners.game;

import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.ServerConfigManager;
import kr.enak.plugintemplate.TemplatePlugin;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ProtectionListener implements Listener {
    private final ServerConfigManager configManager;

    public ProtectionListener() {
        this.configManager = TemplatePlugin.getResourceManager(ServerConfigManager.class);
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        if (this.configManager.getServerConfig().isExplosionAllowed()) return;

        event.setCancelled(true);
        if (event.getEntityType() == EntityType.PRIMED_TNT || event.getEntityType() == EntityType.MINECART_TNT)
            event.getEntity().remove();
    }

    @EventHandler
    public void onExplosionDamage(EntityDamageEvent event) {
        if (!this.configManager.getServerConfig().isExplosionAllowed()) {
            if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockExplodeEvent event) {
        if (this.configManager.getServerConfig().isExplosionAllowed()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void damageByPlayer(EntityDamageByEntityEvent event) {
        if (this.configManager.getServerConfig().isPvpAllowed())
            return;
        else if (event.getEntity().getType() != EntityType.PLAYER
                || event.getDamager().getType() != EntityType.PLAYER) return;
        else if (event.getDamager().isOp()) return;

        event.setCancelled(true);
        event.getDamager().spigot().sendMessage(TextComponent.fromLegacyText("서버가 pvp 비활성화 상태입니다."));
    }
}
