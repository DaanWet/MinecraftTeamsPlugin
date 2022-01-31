package me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers;

import com.google.common.collect.Lists;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        config.set(name + ".Members", members.stream().map(UUID::toString).collect(Collectors.toList()));
        config.set(name + ".Warp", null);
        saveYml();
    }

    public List<UUID> getTeamMembers(String team){
        return config.getStringList(team + ".Members").stream().map(UUID::fromString).collect(Collectors.toList());
    }

    public void joinTeam(String team, UUID player){
        if (config.getStringList(team + ".Members").isEmpty()){
            config.set(team + ".Members", Collections.singletonList(player.toString()));
        } else {
            List<String> templist = config.getStringList(team + ".Members");
            templist.add(player.toString());
            config.set(team + ".Members", templist);
        }
        saveYml();
    }

    public void exitTeam(UUID player){
        String team = getTeam(player);
        if (team != null){
            List<String> tempList = config.getStringList(team + ".Members");
            tempList.remove(player.toString());
            config.set(team + ".Members", tempList);
            saveYml();
        }

    }

    public String getTeam(UUID player){
        String p = player.toString();
        for (String teamName : config.getKeys(false)){
            if (config.getStringList(teamName + ".Members").contains(p)){
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

    public boolean checkTeam(String teamname){
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


    public void setEnderChest(String team, ItemStack[] ender){
        config.set(team + ".EnderChest", ender);
        saveYml();
    }

    public ItemStack[] getEnderChest(String team){
        try {
            ItemStack[] list = (ItemStack[]) config.get(team + ".EnderChest");
            System.out.println("array");
            return list;
        } catch(ClassCastException e){
            System.out.println("list");
            List<ItemStack> list = (List<ItemStack>) config.getList(team + ".EnderChest");
            if (list == null){
                return null;
            }
            ItemStack[] chest = new ItemStack[list.size()];
            list.toArray(chest);
            return chest;
        }
    }

    public boolean isLocked(Location location, String team){
        for (String teamName : config.getKeys(false)){
            if (!teamName.equals(team)){
                List<Location> locks = (List<Location>) config.getList(teamName + ".Chests");
                if (locks.contains(location)){
                    return true;
                }
            } else {
                List<Location> locks = (List<Location>) config.getList(teamName + ".Chests");
                if (locks.contains(location)){
                    return false;
                }
            }
        }
        return false;
    }

    public void setLocked(Location location, String team){
        ArrayList<Location> locks = (ArrayList<Location>) config.getList(team + ".Chests");
        if (locks == null){
            locks = new ArrayList<>();
        }
        locks.add(location);
        config.set(team + ".Chests", locks);
    }

    public void removeLocked(Location location, String team){
        ArrayList<Location> locks = (ArrayList<Location>) config.getList(team + ".Chests");
        if (locks == null){
            locks = new ArrayList<>();
        }
        locks.remove(location);
        config.set(team + ".Chests", locks);
    }

}
