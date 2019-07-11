package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.GUIBuilder;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class InventoryClickHandler implements Listener {

    private Main plugin;


    public InventoryClickHandler(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        InventoryView inv = e.getView();
        Player player = (Player) e.getWhoClicked();
        Material clicked = e.getCurrentItem().getType();
        GUIBuilder builder = new GUIBuilder(player, plugin);
        if (inv.getTitle().equals(ChatColor.AQUA + "Travel Menu")){
            if (clicked.equals(Material.ACTIVATOR_RAIL)){
                builder.createTravel();
            } else if (clicked.equals(Material.CRAFTING_TABLE)){
                builder.createWarpPointMenu();
            } else if (clicked.equals(Material.BARRIER)){
                e.getView().close();
            }
            e.setCancelled(true);
        } else if (inv.getTitle().equals(ChatColor.AQUA + "Warp Creation Menu")){
            if (clicked.equals(Material.CRAFTING_TABLE)){
                Location loc = player.getLocation();
                TeamsYmlHandler tHandler = plugin.getTeamsHandler();
                loc.setX(loc.getBlockX());
                loc.setY(loc.getBlockY());
                loc.setZ(loc.getBlockZ());
                tHandler.setWarp(tHandler.getTeam(player.getName()), loc);
                builder.createWarpPointMenu();
            }
            e.setCancelled(true);
        }
    }
}
