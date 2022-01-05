package kr.enak.plugintemplate;

import kr.enak.plugintemplate.helpers.ClassFinderUtil;
import kr.enak.plugintemplate.models.ResourceManager;
import kr.enak.plugintemplate.models.StorableData;
import kr.enak.plugintemplate.models.Task;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public abstract class TemplatePlugin extends JavaPlugin {
    public static TemplatePlugin INSTANCE;
    public static String PREFIX = "";
    private static Logger logger;
    public final List<Class<?>> DEFAULT_MANAGERS = new ArrayList<>();
    public final List<Class<?>> DEFAULT_LISTENERS = new ArrayList<>();
    public final List<Class<?>> DEFAULT_TASKS = new ArrayList<>();
    private final List<ResourceManager> managers = new ArrayList<>();
    private final List<Listener> listeners = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();

    public TemplatePlugin() {
        logger = Logger.getLogger(this.getClass().getName());
        PREFIX = getPrefix();
    }

    public static void log(Object obj) {
        logger.info(obj.toString());
    }

    public static void warn(Object obj) {
        logger.warning(obj.toString());
    }

    public static <T extends ResourceManager> T getResourceManager(Class<T> classType) {
        for (ResourceManager manager : INSTANCE.managers) {
            if (manager.getClass().equals(classType)) return (T) manager;
        }
        return null;
    }

    public abstract String getPrefix();

    @Override
    public void onEnable() {
        INSTANCE = this;

        log("Enabling");
        registerConfigurationSerialization();
        registerDefaultManager();
        initializeManagers();
        runManagers();
    }

    @Override
    public void onDisable() {
        log("Disabling");
        stopManagers();
    }

    private void registerConfigurationSerialization() {
        try {
            ClassFinderUtil.getClasses(this.getClass(), this.getClass().getPackage().getName()).stream()
                    .filter(StorableData.class::isAssignableFrom)
                    .forEach(v -> {
                        log("Add data class for serialization: " + v.getName());
                        ConfigurationSerialization.registerClass(v);
                    });
        } catch (Exception e) {
            e.printStackTrace();
            logger.throwing(this.getClass().getName(), "registerConfigurationSerialization", e);
        }
    }

    private void registerDefaultManager() {
        for (Class<?> c : DEFAULT_MANAGERS) {
            try {
                ResourceManager manager = (ResourceManager) c.getConstructor(TemplatePlugin.class).newInstance(this);
                addResourceManager(manager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Class<?> c : DEFAULT_LISTENERS) {
            try {
                Listener listener = (Listener) c.getConstructor().newInstance();
                if (!this.listeners.contains(listener)) {
                    Bukkit.getPluginManager().registerEvents(listener, this);
                    this.listeners.add(listener);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Class<?> c : DEFAULT_TASKS) {
            try {
                Task task = (Task) c.getConstructor().newInstance();
                if (!this.tasks.contains(task)) {
                    task.enqueueNextRun();
                    this.tasks.add(task);
                } else {
                    warn("Double registering of task " + c.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected final boolean addResourceManager(ResourceManager manager) {
        if (Objects.isNull(manager)) return false;
        if (this.managers.contains(manager)) return false;

        this.managers.add(manager);
        return true;
    }

    public final void initializeManagers() {
        this.managers.stream()
                .filter((resourceManager -> resourceManager.getStatus() != ResourceManager.ManagerStatus.DISABLED))
                .forEach((resourceManager -> {
                    try {
                        resourceManager.onInit();
                    } catch (Exception ex) {
                        logger.severe("Error in " + resourceManager.getClass().getName() + "#onInit()");
                        ex.printStackTrace();
                    }
                }));
    }

    public final void runManagers() {
        this.managers.stream()
                .filter((resourceManager -> resourceManager.getStatus() != ResourceManager.ManagerStatus.INITIALIZED))
                .forEach((resourceManager -> {
                    try {
                        resourceManager.onRun();
                    } catch (Exception ex) {
                        logger.severe("Error in " + resourceManager.getClass().getName() + "#onRun()");
                        ex.printStackTrace();
                    }
                }));
    }

    public final void stopManagers() {
        this.managers.stream()
                .filter((resourceManager -> resourceManager.getStatus() != ResourceManager.ManagerStatus.RUNNING
                        && resourceManager.getStatus() != ResourceManager.ManagerStatus.INITIALIZED))
                .forEach((resourceManager -> {
                    try {
                        resourceManager.onStop();
                    } catch (Exception ex) {
                        logger.severe("Error in " + resourceManager.getClass().getName() + "#onStop()");
                        ex.printStackTrace();
                    }
                }));
    }
}
