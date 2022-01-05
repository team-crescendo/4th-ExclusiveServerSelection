package kr.enak.plugintemplate.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class CommandExecutor implements CommandInterface {
    private final String label;
    private final List<String> aliases = new ArrayList<>();

    public CommandExecutor(String label, String... aliases) {
        this.label = label;
        Arrays.stream(aliases).forEach((s -> this.aliases.add(s.toLowerCase())));
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public List<String> getAliasList() {
        return this.aliases;
    }

    @Override
    public boolean hasResponsibility(String arg) {
        return !Objects.isNull(arg) && (label.equalsIgnoreCase(arg) || aliases.contains(arg.toLowerCase()));
    }
}
