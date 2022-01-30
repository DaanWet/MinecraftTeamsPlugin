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

    public boolean checkTeamChat(UUID player){
        return config.getBoolean(player.toString() + ".Chat");
    }

    public void setTeamChat(UUID player, boolean bl){
        config.set(player.toString() + ".Chat", bl);
        saveYml();
    }

    public boolean isAFK(UUID player){
        String p = player.toString();
        Object obj = config.get(p + ".AFK");
        if (obj == null){
            config.set(p + ".AFK", false);
            saveYml();
        }
        return config.getBoolean(p + ".AFK");
    }

    public void setAFK(UUID player, boolean afk){
        config.set(player.toString() + ".AFK", afk);
        saveYml();
    }

    public void setAFKTime(UUID player, long time){
        config.set(player.toString() + ".AFKTime", time);
        saveYml();
    }

    public long getAFKTIme(UUID player){
        String p = player.toString();
        Object obj = config.get(p + ".AFKTime");
        if (obj == null){
            config.set(p + ".AFKTime", 0L);
            saveYml();
        }
        return config.getLong(p + ".AFKTime");
    }


}
