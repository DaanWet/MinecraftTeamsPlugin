package me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class PlayerYmlHandler extends YmlHandler{

    public PlayerYmlHandler(Main plugin){
        super(plugin);
        ymlFile = new File(plugin.getDataFolder(), "players.yml");
        config = YamlConfiguration.loadConfiguration(ymlFile);
    }

    public String checkTeamChat(String playername) {
        return config.get(playername + ".Chat").toString();
    }

    public void setTeamChat(String playername, Boolean bl) {
        config.set(playername + ".Chat", bl);
        saveYml();
    }

}
