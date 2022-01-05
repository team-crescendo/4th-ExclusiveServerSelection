package kr.enak.plugintemplate.models;

import kr.enak.plugintemplate.TemplatePlugin;

public class DefaultResourceManager implements ResourceManager {
    private final TemplatePlugin plugin;
    private ManagerStatus status = ManagerStatus.DISABLED;

    public DefaultResourceManager(TemplatePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public TemplatePlugin getPlugin() {
        return this.plugin;
    }

    @Override
    public void onInit() {
        TemplatePlugin.log("Initializing manager " + this.getClass().getName());
        setStatus(ManagerStatus.INITIALIZED);
    }

    @Override
    public void onRun() {
        setStatus(ManagerStatus.RUNNING);
    }

    @Override
    public void onStop() {
        setStatus(ManagerStatus.DISABLED);
    }

    @Override
    public void onSave() {

    }

    @Override
    public ManagerStatus getStatus() {
        return null;
    }

    protected final void setStatus(ManagerStatus status) {
        this.status = status;
    }
}
