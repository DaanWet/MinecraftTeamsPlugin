package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnderChestHandler implements Listener {

    private final Main plugin;


    public EnderChestHandler(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onEnderChestOpen(PlayerInteractEvent event){
        if (event.hasBlock() && event.getClickedBlock().getType() == Material.ENDER_CHEST){
            if (plugin.getTeamsHandler().getTeam(event.getPlayer().getUniqueId()) != null){

            }
        }
    }


   /* public Inventory openEnderInventory() {

        //Inventory inv = new Invent

    }*/

}
