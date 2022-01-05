package kr.enak.plugintemplate.models;

import org.bukkit.plugin.java.JavaPlugin;

public interface ResourceManager {
    JavaPlugin getPlugin();

    void onInit();

    void onRun();

    void onStop();

    void onSave();

    ManagerStatus getStatus();

    enum ManagerStatus {
        DISABLED, INITIALIZED, RUNNING, ERROR, UNKNOWN;
    }
}
