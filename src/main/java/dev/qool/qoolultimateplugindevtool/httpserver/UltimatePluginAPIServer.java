package dev.qool.qoolultimateplugindevtool.httpserver;

import com.rylinaux.plugman.util.PluginUtil;
import com.sun.net.httpserver.HttpServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UltimatePluginAPIServer {

    private HttpServer server;
    private boolean stopped = false;

    public UltimatePluginAPIServer(String host, int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(host, port), 0);
        server.createContext("/start", exchange -> {
            OutputStream out = exchange.getResponseBody();
            List<String> plugins = exchange.getRequestHeaders().get("pluginName");
            for (String pluginName : plugins) {
                Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(pluginName);
                Bukkit.getServer().broadcastMessage(ChatColor.GREEN + pluginName + "的建置程序已經開始! 正在停用插件...");
                if (plugin == null) {
                    String output = "PLUGIN_NOT_FOUND";
                    exchange.sendResponseHeaders(400, output.length());
                    out.write(output.getBytes(StandardCharsets.UTF_8));
                    out.flush();
                    out.close();
                    return;
                }
                Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("QoolUltimatePluginDevTool"), () -> PluginUtil.unload(plugin));
                String output = "OK";
                exchange.sendResponseHeaders(200, output.length());
                out.write(output.getBytes(StandardCharsets.UTF_8));
                out.flush();
                out.close();
                return;
            }
        });
        server.createContext("/end", exchange -> {
            OutputStream out = exchange.getResponseBody();
            List<String> fileNames = exchange.getRequestHeaders().get("filePath");
            List<String> plugins = exchange.getRequestHeaders().get("pluginName");
            for (String fileName : fileNames) {
                Bukkit.getServer().broadcastMessage(ChatColor.GREEN + plugins.get(0) + "的建置程序已結束! 正在啟用插件...");
                Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("QoolUltimatePluginDevTool"), () -> PluginUtil.load(fileName.replaceAll(".jar", "")));
                String output = "OK";
                exchange.sendResponseHeaders(200, output.length());
                out.write(output.getBytes(StandardCharsets.UTF_8));
                out.flush();
                out.close();
                return;
            }
        });
        server.start();
    }

    public HttpServer getServer() {
        return server;
    }

    /**
     * Stop the server, only do it on plugin disabled or it won't start ever again
     */
    public void stopServer() {
        if (isStopped()) return;
        stopped = true;
        server.stop(1);
    }

    /**
     * Check if server is stopped
     * @return True if server is stopped, and false if server is still running
     */
    public boolean isStopped() {
        return stopped;
    }
}
