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
import org.bukkit.inventory.InventoryView;

import java.util.List;

public class InventoryClickHandler implements Listener {

    TeamsYmlHandler tHandler;
    private final Main plugin;


    public InventoryClickHandler(Main plugin){
        this.plugin = plugin;
        tHandler = plugin.getTeamsHandler();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        InventoryView inv = e.getView();
        Player player = (Player) e.getWhoClicked();
        Material clicked;
        try {
            clicked = e.getCurrentItem().getType();
        } catch (Exception exc){
            clicked = null;
        }
        GUIBuilder builder = new GUIBuilder(player, plugin);
        if (clicked != null && e.getClickedInventory() != null){
            boolean top = e.getClickedInventory().equals(inv.getTopInventory());
            if (top && inv.getTitle().equals(ChatColor.AQUA + "Main Menu")){
                if (clicked.equals(Material.ACTIVATOR_RAIL)){
                    builder.createTravel();
                } else if (clicked.equals(Material.CRAFTING_TABLE)){
                    builder.createWarpPointMenu();
                } else if (clicked.equals(Material.BARRIER)){
                    e.getView().close();
                }
                e.setCancelled(true);
            } else if (top && inv.getTitle().equals(ChatColor.AQUA + "Warp Creation Menu")){
                if (clicked.equals(Material.CRAFTING_TABLE)){
                    Location loc = player.getLocation();
                    loc.setX(loc.getBlockX());
                    loc.setY(loc.getBlockY());
                    loc.setZ(loc.getBlockZ());
                    tHandler.setWarp(tHandler.getTeam(player.getUniqueId()), loc);
                    builder.createWarpPointMenu();
                } else if (clicked.equals(Material.BARRIER)){
                    builder.createMainMenu();
                }
                e.setCancelled(true);
            } else if (top && inv.getTitle().equals(ChatColor.AQUA + "Travel Menu")){
                if (clicked.equals(Material.BARRIER)){
                    builder.createMainMenu();
                } else if (!clicked.equals(Material.BLACK_STAINED_GLASS_PANE)){
                    String team = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);
                    Location loc = tHandler.getWarp(team);
                    builder.createFuelMenu(loc, team);
                }
                e.setCancelled(true);
            } else if (inv.getTitle().equals(ChatColor.BLACK + "Fuel Deposit")){
                if (top && clicked.equals(Material.BARRIER)){
                    builder.createTravel();
                    e.setCancelled(true);
                } else if (top && clicked.equals(Material.GREEN_CONCRETE)){
                    List<String> lore = e.getCurrentItem().getItemMeta().getLore();
                    int cost = Integer.parseInt(lore.get(0).split(" ")[0].substring(2));
                    if (e.getClickedInventory().contains(Material.COAL) && e.getClickedInventory().first(Material.COAL) == 13 && e.getClickedInventory().getItem(13).getAmount() == cost){
                        player.teleport(tHandler.getWarp(lore.get(1)));
                    }
                    e.setCancelled(true);
                } else if (!clicked.equals(Material.COAL) && !clicked.equals(Material.AIR)){
                    e.setCancelled(true);
                }
            }
        }
    }
}
