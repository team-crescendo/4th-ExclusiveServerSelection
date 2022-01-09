package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.commands.setting;

import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.helper.MessageHelper;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.ServerConfigManager;
import kr.enak.plugintemplate.TemplatePlugin;
import kr.enak.plugintemplate.commands.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandModify extends CommandExecutor {
    private final ServerConfigManager configManager;

    public CommandModify() {
        super("set");

        this.configManager = TemplatePlugin.getResourceManager(ServerConfigManager.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        switch (args[0]) {
            case "pvp": {
                boolean value;
                if (args[1].equalsIgnoreCase("t") || args[1].equalsIgnoreCase("true"))
                    value = true;
                else if (args[1].equalsIgnoreCase("f") || args[1].equalsIgnoreCase("false"))
                    value = false;
                else {
                    MessageHelper.send(sender, String.format("잘못된 값: %s (예상: true/false)", args[1]));
                    return true;
                }

                boolean original = this.configManager.getServerConfig().isPvpAllowed();
                this.configManager.getServerConfig().setPvpAllowed(value);
                MessageHelper.send(sender, String.format(
                        "pvp 허용 여부를 %s (으)로 설정했습니다. (원래 %s 였음)",
                        value, original
                ));
                break;
            }
            case "explosion": {
                boolean value;
                if (args[1].equalsIgnoreCase("t") || args[1].equalsIgnoreCase("true"))
                    value = true;
                else if (args[1].equalsIgnoreCase("f") || args[1].equalsIgnoreCase("false"))
                    value = false;
                else {
                    MessageHelper.send(sender, String.format("잘못된 값: %s (예상: true/false)", args[1]));
                    return true;
                }

                boolean original = this.configManager.getServerConfig().isExplosionAllowed();
                this.configManager.getServerConfig().setProtectExplosion(value);
                MessageHelper.send(sender, String.format(
                        "explosion 허용 여부를 %s (으)로 설정했습니다. (원래 %s 였음)",
                        value, original
                ));
                break;
            }
            case "redstone": {
                boolean value;
                if (args[1].equalsIgnoreCase("t") || args[1].equalsIgnoreCase("true"))
                    value = true;
                else if (args[1].equalsIgnoreCase("f") || args[1].equalsIgnoreCase("false"))
                    value = false;
                else {
                    MessageHelper.send(sender, String.format("잘못된 값: %s (예상: true/false)", args[1]));
                    return true;
                }

                boolean original = this.configManager.getServerConfig().isRedstoneAllowed();
                this.configManager.getServerConfig().setRedstoneAllowed(value);
                MessageHelper.send(sender, String.format(
                        "redstone 허용 여부를 %s (으)로 설정했습니다. (원래 %s 였음)",
                        value, original
                ));
                break;
            }
            default: {
                MessageHelper.send(sender, String.format("사용법: /%s <entry> <value>", label));
                MessageHelper.send(sender, String.format(" > Entries: pvp, explosion, redstone", label));
            }
        }

        return true;
    }
}
