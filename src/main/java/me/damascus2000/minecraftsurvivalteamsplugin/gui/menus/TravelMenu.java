package me.damascus2000.minecraftsurvivalteamsplugin.gui.menus;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.Menu;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.MenuData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class TravelMenu extends Menu {

    public TravelMenu(Main plugin, MenuData menuData){
        super(plugin, menuData);
        this.name = ChatColor.AQUA + "Travel Menu";
    }


    @Override
    public void handleMenu(InventoryClickEvent e){
        Material clicked = e.getCurrentItem().getType();
        if (clicked.equals(Material.BARRIER)){
            new TravelMainMenu(plugin, menuData).open();
        } else if (!clicked.equals(Material.BLACK_STAINED_GLASS_PANE)){
            String team = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);
            menuData.setWarp(tHandler.getWarp(team));
            new FuelMenu(plugin, menuData).open();
        }
    }

    @Override
    public void setMenuItems(){
        List<String> warps = getTeamWarp();
        int place = 11;
        int team = 0;
        while (team < warps.size()){
            int j = 0;
            while (team + j < warps.size() && j < 2){
                String teamname = warps.get(team + j);
                String color = tHandler.getTeamColor(teamname).toUpperCase();
                Material mat;
                try {
                    mat = Material.valueOf(String.format("%s_CONCRETE", color));
                } catch (Exception e){
                    mat = Material.WHITE_CONCRETE;
                }
                Location loc = tHandler.getWarp(teamname);
                String desc = ChatColor.BLUE + String.format("Warp to X: %s, Y: %s, Z: %s", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());

                inventory.setItem(place, makeItem(mat, ChatColor.valueOf(color) + teamname, desc));
                place += 4;
                j++;
            }
            team += 2;
            place += 10;

        }
        inventory.setItem(getSize(warps) - 5, BACK);
    }

    @Override
    public int getSize(){
        return getSize(getTeamWarp());
    }

    public int getSize(List<String> warps){
        return (((warps.size() + 1) / 2) * 18) + 9;
    }


    private List<String> getTeamWarp(){
        ArrayList<String> teamsWithWarp = new ArrayList<>();
        for (String team : tHandler.getTeams()){
            if (tHandler.getWarp(team) != null){
                teamsWithWarp.add(team);
            }
        }
        return teamsWithWarp;
    }
}
