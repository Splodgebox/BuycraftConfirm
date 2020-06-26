package net.splodgebox.buycraftconfirm.utils;

import com.google.common.base.Charsets;
import lombok.Getter;
import lombok.Setter;
import net.splodgebox.buycraftconfirm.BuycraftConfirm;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Getter @Setter
public class FileManager {

    /* File */
    private File file;
    /* Strings */
    private String name, directory;
    /* Configuration */
    private YamlConfiguration config;

    /**
     * Bukkit Configuration Class
     *
     * @param name - Is the identifier for the configuration file and object.
     * @param directory - Directory.
     */
    public FileManager(JavaPlugin plugin, String name, String directory){
        /* Set the Name */
        setName(name);
        /* Set the Directory */
        setDirectory(directory);
        /* Set File */
        file = new File(directory, name + ".yml");
        /* If file does not already exist, then grab it internally from the resources folder */
        if (!file.exists()) {
            plugin.saveResource(name + ".yml", false);
        }
        /* Load the files configuration */
        this.config = YamlConfiguration.loadConfiguration(this.getFile());
    }

    /**
     * Saves the configuration file from memory to storage
     */
    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(this.getFile());

        final InputStream defConfigStream = BuycraftConfirm.getInstance().getResource(name + ".yml");
        if (defConfigStream == null) {
            return;
        }

        config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
    }
}
