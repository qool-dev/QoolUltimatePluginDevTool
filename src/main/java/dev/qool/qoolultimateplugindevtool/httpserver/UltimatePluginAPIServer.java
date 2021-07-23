package dev.qool.qoolultimateplugindevtool.httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

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
                if (plugin == null) {
                    String output = "PLUGIN_NOT_FOUND";
                    exchange.sendResponseHeaders(400, output.length());
                    out.write(output.getBytes(StandardCharsets.UTF_8));
                    out.flush();
                    out.close();
                    return;
                }
                Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("QoolUltimatePluginDevTool"), () -> Bukkit.getServer().getPluginManager().disablePlugin(plugin));
                String output = "OK";
                exchange.sendResponseHeaders(200, output.length());
                out.write(output.getBytes(StandardCharsets.UTF_8));
                out.flush();
                out.close();
                Bukkit.getServer().broadcastMessage("Plugin " + pluginName + " building process detected. Plugin is now disabled!");
                return;
            }
        });
        server.createContext("/end", exchange -> {
            OutputStream out = exchange.getResponseBody();
            List<String> fileNames = exchange.getRequestHeaders().get("filePath");
            List<String> plugins = exchange.getRequestHeaders().get("pluginName");
            for (String fileName : fileNames) {
                if (Bukkit.getServer().getPluginManager().getPlugin(plugins.get(0)).isEnabled()) {
                    String output = "PLUGIN_ALREADY_LOADED";
                    exchange.sendResponseHeaders(400, output.length());
                    out.write(output.getBytes(StandardCharsets.UTF_8));
                    out.flush();
                    out.close();
                    return;
                }
                Plugin plugin;
                try {
                    plugin = Bukkit.getServer().getPluginManager().loadPlugin(new File(fileName));
                } catch (Exception e) {
                    String output = "PLUGIN_NOT_FOUND";
                    exchange.sendResponseHeaders(400, output.length());
                    out.write(output.getBytes(StandardCharsets.UTF_8));
                    out.flush();
                    out.close();
                    return;
                }

                if (plugin == null) {
                    String output = "PLUGIN_NOT_FOUND";
                    exchange.sendResponseHeaders(400, output.length());
                    out.write(output.getBytes(StandardCharsets.UTF_8));
                    out.flush();
                    out.close();
                    return;
                }
                if ((Bukkit.getPluginManager().getPlugin(plugin.getDescription().getName()) != null && Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(plugin.getDescription().getName())).isEnabled())) {
                    String output = "PLUGIN_ALREADY_LOADED";
                    exchange.sendResponseHeaders(400, output.length());
                    out.write(output.getBytes(StandardCharsets.UTF_8));
                    out.flush();
                    out.close();
                    return;
                }
                Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("QoolUltimatePluginDevTool"), () -> Bukkit.getServer().getPluginManager().enablePlugin(plugin));
                String output = "OK";
                exchange.sendResponseHeaders(200, output.length());
                out.write(output.getBytes(StandardCharsets.UTF_8));
                out.flush();
                out.close();
                Bukkit.getServer().broadcastMessage("Plugin: " + plugin.getDescription().getName() + " is now built and reloaded!");
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
        server.stop(5);
    }

    /**
     * Check if server is stopped
     * @return True if server is stopped, and false if server is still running
     */
    public boolean isStopped() {
        return stopped;
    }
}
