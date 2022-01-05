package kr.enak.plugintemplate.models;

import kr.enak.plugintemplate.TemplatePlugin;
import org.bukkit.Bukkit;

public abstract class Task {
    private final TemplatePlugin plugin;

    public Task(TemplatePlugin plugin) {
        this.plugin = plugin;
    }

    public void enqueueNextRun() {
        this.enqueueNextRun(this.getInterval());
    }

    public void enqueueNextRun(long later) {
        Bukkit.getScheduler().runTaskLater(this.plugin, this::run, later);
    }

    public abstract long getInterval();

    public abstract void run();

    public TemplatePlugin getPlugin() {
        return plugin;
    }
}
