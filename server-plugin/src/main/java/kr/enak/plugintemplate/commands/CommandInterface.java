package kr.enak.plugintemplate.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface CommandInterface {
    String getLabel();

    List<String> getAliasList();

    boolean hasResponsibility(String arg);

    boolean onCommand(CommandSender sender, String label, String[] args);
}
