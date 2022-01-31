package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.Menu;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.MenuData;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.menus.EnderChestMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

public class EnderChestHandler implements Listener {

    private final Main plugin;


    public EnderChestHandler(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onEnderChestOpen(PlayerInteractEvent event){
        if (event.hasBlock() && event.getClickedBlock().getType() == Material.ENDER_CHEST && event.getAction() == Action.RIGHT_CLICK_BLOCK){
            String team = plugin.getTeamsHandler().getTeam(event.getPlayer().getUniqueId());
            if (team != null){
                event.setCancelled(true);
                MenuData data = new MenuData(event.getPlayer());
                data.setTeam(team);
                new EnderChestMenu(plugin, data).open();
            }
        }
    }

    @EventHandler
    public void openEnderChest(InventoryOpenEvent e){
        if (e.getInventory().getType() == InventoryType.ENDER_CHEST){
            if (plugin.getTeamsHandler().getTeam(e.getPlayer().getUniqueId()) != null){
                e.getPlayer().sendMessage("oi");
                e.getView().close();
                new EnderChestMenu(plugin, new MenuData((Player) e.getPlayer())).open();
            }
        }
    }

    @EventHandler
    public void closeTrade(InventoryCloseEvent e){
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu menu){
            menu.handleClose();
        }
    }

   /* public Inventory openEnderInventory() {

        //Inventory inv = new Invent

    }*/

}
