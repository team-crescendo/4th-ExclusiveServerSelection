package kr.enak.plugintemplate.data;

import kr.enak.plugintemplate.TemplatePlugin;
import kr.enak.plugintemplate.models.DefaultResourceManager;

public abstract class DefaultDataManager extends DefaultResourceManager {
    public DefaultDataManager(TemplatePlugin plugin) {
        super(plugin);
    }

    @Override
    public void onInit() {
        super.onInit();
        loadData();
    }

    @Override
    public void onStop() {
        super.onStop();
        saveData();
    }

    public abstract void loadData();

    public abstract void saveData();
}
