package kr.enak.plugintemplate.helpers;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassFinderUtil {
    public static Vector<Class> getClassListFromClassloader(ClassLoader loader)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Class loaderClass = loader.getClass();
        while (loaderClass != ClassLoader.class) {
            loaderClass = loaderClass.getSuperclass();
        }
        Field classesField = loaderClass.getDeclaredField("classes");
        classesField.setAccessible(true);
        return (Vector<Class>) classesField.get(loader);
    }

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws IOException
     */
    public static List<Class> getClasses(Class<? extends JavaPlugin> T, String packageName) throws IOException {
        String path = packageName.replace('.', '/');
        ArrayList<Class> classes = new ArrayList<>();

        File jarFile = new File(T.getProtectionDomain().getCodeSource().getLocation().getPath()
                .replace("%20", " "));
        JarFile jar = new JarFile(jarFile);
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.isDirectory()) continue;

            String name = entry.getName();
            if (!name.startsWith(path + "/")) continue;

            String className = name.replace("/", ".");
            if (className.endsWith(".class"))
                className = className.substring(0, className.length() - 6);

            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignored) {
            }
        }
        jar.close();
        return classes;
    }
}