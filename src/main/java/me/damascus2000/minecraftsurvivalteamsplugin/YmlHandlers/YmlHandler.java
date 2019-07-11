package me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class YmlHandler {

    private Main plugin;
    protected YamlConfiguration config;
    protected File ymlFile;

    public YmlHandler(Main plugin){
        this.plugin = plugin;
    }

    public void saveYml() {
        try {
            config.save(ymlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
