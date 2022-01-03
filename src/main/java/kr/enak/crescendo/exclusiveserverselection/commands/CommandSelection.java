package kr.enak.crescendo.exclusiveserverselection.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class CommandSelection extends Command {
    public CommandSelection() {
        super("selection", "bungeecord.exclusive");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(
                    new ComponentBuilder()
                            .append("잘못된 인자 개수")
                            .create()
            );
            return;
        }

        if (args.length >= 3 && args[1].equalsIgnoreCase("to")) {
            if (sender.hasPermission("bungeecord.exclusive.admin")) {
                sender.sendMessage("권한 없음");
                return;
            }


        }
    }
}
