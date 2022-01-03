package kr.enak.crescendo.exclusiveserverselection.spigot.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class ExSSSpigotPlugin extends JavaPlugin {
    private static ExSSSpigotPlugin instance;

    public static ExSSSpigotPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        super.onEnable();
    }
}
