package me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

public class PlayerYmlHandler extends YmlHandler {

    public PlayerYmlHandler(Main plugin){
        super(plugin);
        ymlFile = new File(plugin.getDataFolder(), "players.yml");
        config = YamlConfiguration.loadConfiguration(ymlFile);
    }

    public boolean checkTeamChat(UUID playername){
        return (boolean) config.get(playername + ".Chat");
    }

    public void setTeamChat(UUID playername, boolean bl){
        config.set(playername + ".Chat", bl);
        saveYml();
    }

    public boolean isAFK(UUID playername){
        Object obj = config.get(playername + ".AFK");
        if (obj == null){
            config.set(playername + ".AFK", false);
            saveYml();
        }
        return (boolean) config.get(playername + ".AFK");
    }

    public void setAFK(UUID playername, boolean afk){
        config.set(playername + ".AFK", afk);
        saveYml();
    }

    public void setAFKTime(UUID playername, long time){
        config.set(playername + ".AFKTime", time);
        saveYml();
    }

    public long getAFKTIme(UUID playername){
        Object obj = config.get(playername + ".AFKTime");
        if (obj == null){
            config.set(playername + ".AFKTime", 0L);
            saveYml();
        }
        return ((Number) config.get(playername + ".AFKTime")).longValue();
    }


}
