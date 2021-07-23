package dev.qool.qoolultimateplugindevtool;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * WARNING: This code is for qool only!
 * Leaking it might get you into some legal troubles
 * Qool Network: https://qool.dev
 * Author: fan87
 * Modified:
 * Class location: dev.qool.qoolultimateplugindevtool
 * File Created: 7/23/2021  6:07 PM
 **/
@Mojo(name = "finish")
public class Finish extends AbstractMojo {

    @Parameter(property = "finish.host", defaultValue = "localhost")
    public String host;

    @Parameter(property = "finish.port", defaultValue = "42753")
    public int port;

    @Parameter(property = "finish.plugin-name", required = true)
    public String pluginName;

    @Parameter(property = "finish.plugin-output-path", required = true)
    public String path;

    @Parameter(property = "finish.original-output-path", required = true)
    public String originalPath;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            try {
                Files.delete(Paths.get(new File(path).toURI()));
                Files.copy(new FileInputStream(originalPath), Paths.get(new File(path).toURI()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            URL url = new URL("http://" + host + ":" + port + "/end");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("pluginName", pluginName);
            connection.setRequestProperty("filePath", new File(path).getName());
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            System.out.println(reader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
