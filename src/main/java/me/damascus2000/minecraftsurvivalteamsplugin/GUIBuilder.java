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
    private Location loc;
    private Main plugin;


    public GUIBuilder(Player player, Main plugin){
        this.plugin = plugin;
        tHandler = plugin.getTeamsHandler();
        this.player = player;
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.RED + "Back");
        back.setItemMeta(backMeta);
    }

    public Location getLoc(){
        return loc;
    }

    public Inventory createBasicInv(Player player, int grootte, String name){
        Inventory inv = Bukkit.createInventory(player, grootte, name);
        for (int i = 0; i < grootte; i++){
            ItemStack black = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta blackMeta = black.getItemMeta();
            blackMeta.setDisplayName(ChatColor.GRAY + "");
            black.setItemMeta(blackMeta);
            inv.setItem(i, black);
        }
        return inv;
    }

    public void createMainMenu() {
        Inventory inv = createBasicInv(player, 9, ChatColor.AQUA + "Main Menu");
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
        Inventory inv = createBasicInv(player, 9, ChatColor.AQUA + "Warp Creation Menu");
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
            newLore = new ArrayList<>(Arrays.asList(ChatColor.BLUE + "Replace the current warp", ChatColor.BLUE + "with a warp at your location"));
        } else {
            newLore = new ArrayList<>(Arrays.asList(ChatColor.BLUE + "Set the warp to a warp", ChatColor.BLUE + "at your current location"));
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

        int grootte = (((teamsWithWarp.size() + 1)/ 2) * 18) + 9;
        Inventory inv = createBasicInv(player, grootte , ChatColor.AQUA + "Travel Menu");
        int place = 11;
        int team = 0;
        while (team < teamsWithWarp.size()){
            int j = 0;
            while (team + j < teamsWithWarp.size() &&  j < 2) {
                String teamname = teamsWithWarp.get(team + j);
                String color = tHandler.getTeamColor(teamname).toUpperCase();
                ItemStack block;
                try {
                    block = new ItemStack(Material.valueOf(String.format("%s_CONCRETE", color)));
                } catch (Exception e) {
                    block = new ItemStack(Material.WHITE_CONCRETE);
                }
                ItemMeta blockMeta = block.getItemMeta();
                blockMeta.setDisplayName(ChatColor.valueOf(color) + teamname);
                Location loc = tHandler.getWarp(teamname);
                ArrayList<String> blockLore = new ArrayList<>(Collections.singletonList(ChatColor.BLUE + String.format("Warp to X: %s, Y: %s, Z: %s", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())));
                blockMeta.setLore(blockLore);
                block.setItemMeta(blockMeta);
                inv.setItem(place, block);
                place += 4;
                j++;
            }
            team += 2;
            place += 10;

        }
        inv.setItem(grootte - 5, back);
        player.openInventory(inv);
    }

    public void createFuelMenu(Location loc, String team){
        this.loc = loc;
        Inventory inv = Bukkit.createInventory(player, 27, ChatColor.BLACK + "Fuel Deposit");
        Location currentLoc = player.getLocation();
        Location vector = currentLoc.subtract(loc);
        double x = vector.getX();
        double y = vector.getY();
        double z = vector.getZ();
        double flatdistance = Math.sqrt(x*x + y*y);
        double distance = Math.sqrt(flatdistance*flatdistance + z*z);
        int cost = (int)(plugin.getConfig().getDouble("travelcost") * distance);
        for(int i = 0; i< 27; i++){
            if (i != 13) {
                ItemStack black = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                ItemMeta blackMeta = black.getItemMeta();
                blackMeta.setDisplayName(ChatColor.GRAY + String.format("Deposit %s coal", cost));
                black.setItemMeta(blackMeta);
                inv.setItem(i, black);
            }
        }
        ItemStack confirm = new ItemStack(Material.GREEN_CONCRETE);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName(ChatColor.GREEN + "Confirm");
        confirmMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.GRAY + String.format("%s coal", cost), team)));
        confirm.setItemMeta(confirmMeta);
        inv.setItem(25, confirm);
        inv.setItem(26, back);
        inv.setMaxStackSize(cost);
        player.openInventory(inv);

    }
}
