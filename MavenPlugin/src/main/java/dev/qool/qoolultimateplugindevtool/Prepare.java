package dev.qool.qoolultimateplugindevtool;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * WARNING: This code is for qool only!
 * Leaking it might get you into some legal troubles
 * Qool Network: https://qool.dev
 * Author: fan87
 * Modified:
 * Class location: dev.qool.qoolultimateplugindevtool
 * File Created: 7/23/2021  6:07 PM
 **/
@Mojo(name = "prepare")
public class Prepare extends AbstractMojo {

    @Parameter(property = "prepare.host", defaultValue = "localhost")
    public String host;

    @Parameter(property = "prepare.port", defaultValue = "42753")
    public int port;

    @Parameter(property = "prepare.plugin-name", required = true)
    public String pluginName;

    @Parameter(property = "prepare.plugin-output-path", required = true)
    public String path;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            URL url = new URL("http://" + host + ":" + port + "/start");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("pluginName", pluginName);
            connection.setRequestProperty("filePath", path);
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            System.out.println(reader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
