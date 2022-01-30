package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;


import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClickHandler implements Listener {

    private final Main plugin;
    TeamsYmlHandler tHandler;


    public InventoryClickHandler(Main plugin){
        this.plugin = plugin;
        tHandler = plugin.getTeamsHandler();
    }


    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu menu){
            e.setCancelled(true);
            if (e.getCurrentItem() == null){
                return;
            }
            menu.handleMenu(e);
        }

    }
}
