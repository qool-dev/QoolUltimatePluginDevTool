package dev.qool.qoolultimateplugindevtool.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * I know this is useless but whatever lololololololoolololololoolol
 */
public class PluginLoadingUtil {

    /**
     * Get the class loader of the plugin, or PluginClassLoader (in CraftBukkit)
     * @return PluginClassLoader
     */
    public static URLClassLoader getClassLoader() {
        return (URLClassLoader) PluginLoadingUtil.class.getClassLoader();
    }

    /**
     * Add URL to the PluginClassLoader
     * @param url The URL to you want to load
     */
    public static void addURL(URL url) {
        Method addURLMethod = getAddURLMethod();
        try {
            addURLMethod.setAccessible(true);
            addURLMethod.invoke(getClassLoader(), url);
        } catch (Exception e) {
            e.printStackTrace();
            // It will happen when your Illegal Access is denied. Check readme.md
        }
    }

    /**
     * Load plugin. If it's loaded it should throw some exception
     * @param pluginFile The plugin you want to load
     * @throws InvalidPluginException by Bukkit
     * @throws InvalidDescriptionException by Bukkit
     * @throws NullPointerException If something went wrong
     */
    public static Plugin loadPlugin(File pluginFile) throws InvalidPluginException, InvalidDescriptionException {
        Plugin plugin = Bukkit.getServer().getPluginManager().loadPlugin(pluginFile);
        Bukkit.getServer().getPluginManager().enablePlugin(plugin);
        return plugin;
    }

    /**
     * Unload plugin
     * @param plugin The plugin you want to unload
     */
    public static void unloadPlugin(Plugin plugin) {
        Bukkit.getServer().getPluginManager().disablePlugin(plugin);
    }



    private static Method getAddURLMethod() {
        try {
            return URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        } catch (Exception e) {
            e.printStackTrace();
            // Normally, it won't happen if you are using original version of Java.
        }
        return null;
    }


}
