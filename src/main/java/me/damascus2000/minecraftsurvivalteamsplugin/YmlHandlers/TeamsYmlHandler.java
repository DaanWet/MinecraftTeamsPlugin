package me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers;

import com.google.common.collect.Lists;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TeamsYmlHandler extends YmlHandler {


    public TeamsYmlHandler(Main plugin){
        super(plugin);
        ymlFile = new File(plugin.getDataFolder(), "teams.yml");
        config = YamlConfiguration.loadConfiguration(ymlFile);
    }

    public void createTeam(String name, String color, List<UUID> members){
        config.createSection(name);
        config.createSection(name + ".Color");
        config.createSection(name + ".Members");
        config.createSection(name + ".Effect");
        config.createSection(name + ".Warp");
        config.createSection(name + ".Villagers");
        config.set(name + ".Effect", "Bold");
        config.set(name + ".Color", color);
        config.set(name + ".Members", members);
        config.set(name + ".Warp", null);
        saveYml();
    }

    public List<UUID> getTeamMembers(String team){
        return (List<UUID>) config.getList(team + ".Members");
    }

    public void joinTeam(String team, UUID player){
        if (config.getStringList(team + ".Members").isEmpty()){
            config.set(team + ".Members", Collections.singletonList(player));
        } else {
            List<UUID> templist = (List<UUID>) config.getList(team + ".Members");
            templist.add(player);
            config.set(team + ".Members", templist);
        }
        saveYml();
    }

    public void exitTeam(UUID player){
        String team = getTeam(player);
        if (team != null){
            List<UUID> tempList = (List<UUID>) config.getList(team + ".Members");
            tempList.remove(player);
            config.set(team + ".Members", tempList);
            saveYml();
        }

    }

    public String getTeam(UUID player){
        for (String teamName : config.getKeys(false)){
            if (config.getList(teamName + ".Members").contains(player)){
                return teamName;
            }

        }

        return null;
    }

    public String getTeamColor(String team){
        return config.getString(team + ".Color").toUpperCase() + "";
    }

    public void deleteTeam(String team){
        if (config.getStringList(team + ".Members").isEmpty())
            config.set(team + ".Members", null);
        config.set(team + ".Color", null);
        config.set(team + ".Effects", null);
        config.set(team, null);
        saveYml();
    }

    public Boolean checkTeam(String teamname){
        for (String team : config.getKeys(false)){
            if (team.equals(teamname)){
                return true;
            }
        }
        return false;
    }

    public List<String> getTeams(){
        List<String> list = Lists.newArrayList();
        list.addAll(config.getKeys(false));
        return list;
    }

    public void setTeamColor(String color, String teamname){
        config.set(teamname + ".Color", color);
        saveYml();
    }

    public void renameTeam(String old, String newstr){
        List<UUID> members = getTeamMembers(old);
        String color = getTeamColor(old);
        deleteTeam(old);
        createTeam(newstr, color, members);
    }

    public void setTeamFX(String team, String fx){
        config.set(team + ".Effect", fx);
    }

    public String getTeamFX(String team){
        return config.getString(team + ".Effect").toUpperCase() + "";
    }

    public Location getWarp(String team){
        return (Location) config.get(team + ".Warp");
    }

    public void setWarp(String team, Location location){
        config.set(team + ".Warp", location);
        saveYml();
    }

    public void setVillager(String team, UUID uuid, int value){
        if (!config.contains(team + ".Villagers." + uuid)){
            config.createSection(team + ".Villagers." + uuid);
        }
        config.set(team + ".Villagers." + uuid, value);
    }

    public int getVillager(String team, UUID uuid){
        if (config.contains(team + ".Villagers." + uuid)){
            return config.getInt(team + ".Villagers." + uuid);
        } else {
            return -1;
        }
    }
}
