package me.damascus2000.minecraftsurvivalteamsplugin;

import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GUIBuilder {

    private TeamsYmlHandler tHandler;
    private Player player;
    ItemStack back = new ItemStack(Material.BARRIER);


    public GUIBuilder(Player player, Main plugin){
        tHandler = plugin.getTeamsHandler();
        this.player = player;
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.RED + "Back");
        back.setItemMeta(backMeta);
    }


    public void createTravelMenu() {
        Inventory inv = Bukkit.createInventory(player, 9, ChatColor.AQUA + "Travel Menu");
        ItemStack createw;
        ItemMeta createMeta;
        ArrayList<String> createLore;
        if (tHandler.getTeam(player.getName()) != null) {
            createw = new ItemStack(Material.CRAFTING_TABLE);
            createMeta = createw.getItemMeta();
            createMeta.setDisplayName(ChatColor.GREEN + "Create Travel Point");
            createLore = new ArrayList<>(Collections.singletonList(ChatColor.BLUE + "Create or set the travel point for your team"));

        } else {
            createw = new ItemStack(Material.RED_CONCRETE);
            createMeta = createw.getItemMeta();
            createMeta.setDisplayName(ChatColor.RED + "No Team");
            createLore = new ArrayList<>(Collections.singletonList(ChatColor.RED + "Join a team to set your team's travel point"));
        }
        createMeta.setLore(createLore);
        createw.setItemMeta(createMeta);

        ItemStack travel = new ItemStack(Material.ACTIVATOR_RAIL);
        ItemMeta travelMeta = travel.getItemMeta();
        travelMeta.setDisplayName(ChatColor.GREEN + "Travel");
        ArrayList<String> travelLore = new ArrayList<>(Collections.singletonList(ChatColor.BLUE + "Travel to a certain travel point"));
        travelMeta.setLore(travelLore);
        travel.setItemMeta(travelMeta);


        ItemStack cancel = new ItemStack(Material.BARRIER);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName(ChatColor.RED + "Cancel");
        cancel.setItemMeta(cancelMeta);


        inv.setItem(2, createw);
        inv.setItem(5, travel);
        inv.setItem(8, cancel);

        player.openInventory(inv);
    }

    public void createWarpPointMenu(){
        Inventory inv = Bukkit.createInventory(player, 9, ChatColor.AQUA + "Warp Creation Menu");
        ItemStack current = new ItemStack(Material.BLUE_BED);
        ItemMeta currentMeta = current.getItemMeta();
        currentMeta.setDisplayName(ChatColor.BLUE + "Current Warp");
        Location loc = tHandler.getWarp(tHandler.getTeam(player.getName()));
        ArrayList<String> currentLore;
        if (loc != null){
            currentLore = new ArrayList<>(Collections.singletonList(ChatColor.AQUA + String.format("X: %s, Y: %s, Z: %s", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())));
        } else {
            currentLore = new ArrayList<>(Collections.singletonList(ChatColor.AQUA + "No Warp Set"));
        }
        currentMeta.setLore(currentLore);
        current.setItemMeta(currentMeta);

        ItemStack newWarp = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta newMeta = newWarp.getItemMeta();
        newMeta.setDisplayName(ChatColor.GREEN + "Create new warp");
        ArrayList<String> newLore;
        if (loc != null){
            newLore = new ArrayList<>(Collections.singletonList(ChatColor.BLUE + "Replace the current warp with a warp at your location"));
        } else {
            newLore = new ArrayList<>(Collections.singletonList(ChatColor.BLUE + "Set the warp to a warp at your current location"));
        }
        newMeta.setLore(newLore);
        newWarp.setItemMeta(newMeta);

        inv.setItem(2, current);
        inv.setItem(4, newWarp);
        inv.setItem(7, back);
        player.openInventory(inv);

    }

    public void createTravel(){
        ArrayList<String> teamsWithWarp = new ArrayList<>();
        for (String team : tHandler.getTeams()){
            if (tHandler.getWarp(team) != null){
                teamsWithWarp.add(team);
            }
        }
        int grootte = (teamsWithWarp.size() * 2) + 1;
        Inventory inv = Bukkit.createInventory(player, grootte , ChatColor.AQUA + "Travel Menu");
        int g = 11;
        int i = 0;
        while (i < teamsWithWarp.size()){
            String team = teamsWithWarp.get(i);
            String color = tHandler.getTeamColor(team).toUpperCase();
            ItemStack block = new ItemStack(Material.valueOf(String.format("%s_CONCRETE", color)));
            ItemMeta blockMeta = block.getItemMeta();
            blockMeta.setDisplayName(ChatColor.valueOf(color) + team);
            Location loc = tHandler.getWarp(team);
            ArrayList<String> blockLore = new ArrayList<>(Collections.singletonList(ChatColor.BLUE +  String.format("X: %s, Y: %s, Z: %s", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())));
            blockMeta.setLore(blockLore);
            inv.setItem(g, block);
            g += 4;
            i += 1;
            team = teamsWithWarp.get(i);
            color = tHandler.getTeamColor(team).toUpperCase();
            block = new ItemStack(Material.valueOf(String.format("%s_CONCRETE", color)));
            blockMeta = block.getItemMeta();
            blockMeta.setDisplayName(ChatColor.valueOf(color) + team);
            loc = tHandler.getWarp(team);
            blockLore = new ArrayList<>(Collections.singletonList(ChatColor.BLUE +  String.format("X: %s, Y: %s, Z: %s", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())));
            blockMeta.setLore(blockLore);
            inv.setItem(g, block);
            g += 5;
            i += 1;
        }
    }
}
