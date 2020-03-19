package me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;

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

    public boolean isAFK(String playername){
        Object obj = config.get(playername + ".AFK");
        if (obj == null){
            config.set(playername + ".AFK", false);
            saveYml();
        }
        return (boolean) config.get(playername + ".AFK");
    }

    public void setAFK(String playername, boolean afk){
        config.set(playername + ".AFK", afk);
        saveYml();
    }

    public void setAFKTime(String playername, long time){
        config.set(playername + ".AFKTime", time);
        saveYml();
    }
    public long getAFKTIme(String playername){
        Object obj = config.get(playername + ".AFKTime");
        if (obj == null){
            config.set(playername + ".AFKTime", 0L);
            saveYml();
        }
        return ((Number)config.get(playername + ".AFKTime")).longValue();
    }


}
