package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.listeners.game;

import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.ExSSSpigotPlugin;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.ServerConfigManager;
import kr.enak.plugintemplate.TemplatePlugin;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ProtectionListener implements Listener {
    private final ServerConfigManager configManager;

    public ProtectionListener() {
        this.configManager = TemplatePlugin.getResourceManager(ServerConfigManager.class);
        ExSSSpigotPlugin.getInstance().getLogger().info("ProtectionListener initialized");
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

    public static boolean isShotAllowed(Projectile projectile) {
//        Bukkit.broadcastMessage(String.format("Type: %s / Thrower: %s / isOp: %s", projectile.getName(), projectile.getShooter(), ((Entity) projectile.getShooter()).isOp()));
        return (
                !(projectile.getShooter() instanceof Player)
                        || ((Player) projectile.getShooter()).isOp()
        );
    }

    public static boolean isRedStoneBlock(Material material) {
        return (
                material == Material.REDSTONE
                        || material == Material.REDSTONE_BLOCK
                        || material == Material.REDSTONE_TORCH
                        || material == Material.REDSTONE_WALL_TORCH
                        || material == Material.REDSTONE_WIRE
        );
    }

    @EventHandler
    public void preventPistonExtend(BlockPistonExtendEvent event) {
        if (this.configManager.getServerConfig().isRedstoneAllowed())
            return;

        event.setCancelled(true);
    }

    // Disable due to expected performance issue
//    @EventHandler
//    public void block(BlockPhysicsEvent event) {
//        if (
//                this.configManager.getServerConfig().isRedstoneAllowed() && (
//                    isRedStoneBlock(event.getBlock().getType())
//                    || isRedStoneBlock(event.getSourceBlock().getType())
//                )
//        ) {
//            event.setCancelled(true);
//        }
//    }

    @EventHandler
    public void blockRedStoneUpdate(BlockRedstoneEvent event) {
        if (this.configManager.getServerConfig().isRedstoneAllowed())
            return;
        else if (event.getNewCurrent() == 0)
            return;
        else if (!isRedStoneBlock(event.getBlock().getType()))
            return;

        event.setNewCurrent(0);
    }

    @EventHandler
    public void damageByPlayer(EntityDamageByEntityEvent event) {
        if (this.configManager.getServerConfig().isPvpAllowed()) {
//            Bukkit.broadcastMessage("PVP allowed");
            return;
        } else if (event.getEntity().getType() != EntityType.PLAYER) return;
        else if (event.getDamager().isOp()) {
//            Bukkit.broadcastMessage("OP allowed");
            return;
        }

        if (event.getDamager().getType() != EntityType.PLAYER) {
            switch (event.getCause()) {
                // Arrow, SpectralArrow, Trident, Snowball
                case PROJECTILE: {
                    switch (event.getDamager().getType()) {
                        case ARROW:
                        case SPECTRAL_ARROW:
                        case TRIDENT:
                        case SNOWBALL:
                            if (isShotAllowed((Projectile) event.getDamager())) {
//                                Bukkit.broadcastMessage("Allowed shooting");
                                return;
                            }
                            break;
                        default:
                            return;
                    }
                }

                default:
                    return;
            }
        }

//        Bukkit.broadcastMessage("Cancelled");
        event.setCancelled(true);
        event.getDamager().spigot().sendMessage(TextComponent.fromLegacyText("서버가 pvp 비활성화 상태입니다."));
    }
}
