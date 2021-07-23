package dev.qool.qoolultimateplugindevtool;

import dev.qool.qoolultimateplugindevtool.httpserver.UltimatePluginAPIServer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class QoolUltimatePluginDevTool extends JavaPlugin {

    private UltimatePluginAPIServer ultimatePluginAPIServer;

    public final static String PREFIX = "Qool 宇宙極超級無敵插件開發工具 >>";

    @Override
    public void onEnable() {
        saveDefaultConfig();
        String host = getConfig().getString("host");
        int port = getConfig().getInt("port");
        try {
            this.ultimatePluginAPIServer = new UltimatePluginAPIServer(host, port);
        } catch (IOException e) {
            e.printStackTrace();
            getServer().getConsoleSender().sendMessage(ChatColor.RED + PREFIX + "無法開始一個HTTP伺服器，原因: " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        ultimatePluginAPIServer.stopServer();
    }
}
