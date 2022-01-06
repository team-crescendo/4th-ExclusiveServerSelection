package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.commands.portal;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.util.formatting.text.TextComponent;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.PermissionReference;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.ServerConfig;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.ServerConfigManager;
import kr.enak.plugintemplate.TemplatePlugin;
import kr.enak.plugintemplate.commands.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPortal extends CommandExecutor {
    private final ServerConfigManager configManager;

    public CommandPortal() {
        super("portal");

        configManager = TemplatePlugin.getResourceManager(ServerConfigManager.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("인자가 부족합니다.");
            sender.sendMessage(String.format("사용법: /%s <wild/mild>", label));
            return true;
        }

        if (!sender.hasPermission(PermissionReference.COMMAND_SELECT_PORTAL) && !sender.isOp()) {
            sender.sendMessage("권한 부족");
            return true;
        } else if (!(args[0].equalsIgnoreCase("wild")) && !(args[0].equalsIgnoreCase("mild"))) {
            sender.sendMessage("인자가 잘못됐습니다.");
            sender.sendMessage(String.format("사용법: /%s <wild/mild>", label));
            return true;
        }

        WorldEdit worldEdit = WorldEdit.getInstance();
        com.sk89q.worldedit.entity.Player player = BukkitAdapter.adapt((Player) sender);
        LocalSession localSession = worldEdit.getSessionManager().get(player);

        com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(((Player) sender).getWorld());
        Region region;

        try {
            region = localSession.getSelection(world);
        } catch (IncompleteRegionException ex) {
            player.printError(TextComponent.of("영역이 선택되지 않았습니다. 나무 도끼로 영역을 선택해주세요"));
            return true;
        }

        CuboidRegion boundingBox = region.getBoundingBox();
        BlockVector3 pos1 = boundingBox.getPos1();
        BlockVector3 pos2 = boundingBox.getPos2();

        ServerConfig config = configManager.getServerConfig();
        if (args[0].equalsIgnoreCase("wild")) {
            config.getWildPortalCoordinates().setKey(pos1);
            config.getWildPortalCoordinates().setValue(pos2);
        } else {
            config.getMildPortalCoordinates().setKey(pos1);
            config.getMildPortalCoordinates().setValue(pos2);
        }

        sender.sendMessage(String.format("포탈 %s 설정 완료", args[0].toUpperCase()));
        return true;
    }
}
