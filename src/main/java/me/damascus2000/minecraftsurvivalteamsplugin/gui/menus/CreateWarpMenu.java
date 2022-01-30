package me.damascus2000.minecraftsurvivalteamsplugin.gui.menus;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.Menu;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.MenuData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CreateWarpMenu extends Menu {

    public CreateWarpMenu(Main plugin, MenuData menuData){
        super(plugin, menuData);
        this.name = ChatColor.AQUA + "Warp Creation Menu";
    }

    @Override
    public void handleMenu(InventoryClickEvent e){
        switch (e.getCurrentItem().getType()){
            case CRAFTING_TABLE -> {
                Player player = menuData.getOwner();
                Location loc = player.getLocation();
                loc.setX(loc.getBlockX());
                loc.setY(loc.getBlockY());
                loc.setZ(loc.getBlockZ());
                tHandler.setWarp(tHandler.getTeam(player.getUniqueId()), loc);
                this.setMenuItems(); // Does this work
            }
            case BARRIER -> new TravelMainMenu(plugin, menuData).open();
        }
    }

    @Override
    public void setMenuItems(){
        Location loc = tHandler.getWarp(tHandler.getTeam(menuData.getOwner().getUniqueId()));
        String desc = ChatColor.AQUA + (loc == null ? "No Warp Set" : String.format("X: %s, Y: %s, Z: %s", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        inventory.setItem(2, makeItem(Material.BLUE_BED, ChatColor.BLUE + "Current Warp", desc));
        String desc1 = ChatColor.BLUE + (loc == null ? "Set the warp to a warp" : "Replace the current warp");
        String desc2 = ChatColor.BLUE + (loc == null ? "at your current location" : "with a warp at your location");
        inventory.setItem(5, makeItem(Material.CRAFTING_TABLE, ChatColor.GREEN + "Create new warp", desc1, desc2));
        inventory.setItem(8, BACK);
    }

    @Override
    public int getSize(){
        return 9;
    }
}
