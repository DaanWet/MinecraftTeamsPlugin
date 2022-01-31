package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.Menu;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.MenuData;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.menus.EnderChestMenu;
import net.minecraft.world.level.block.ChestBlock;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ChestHandler implements Listener {

    private final Main plugin;
    private final TeamsYmlHandler tHandler;


    public ChestHandler(Main plugin){
        this.plugin = plugin;
        this.tHandler = plugin.getTeamsHandler();
    }

    @EventHandler
    public void onChestOpen(PlayerInteractEvent event){
        if (event.hasBlock() && (event.getClickedBlock().getType() == Material.CHEST || event.getClickedBlock().getType() == Material.TRAPPED_CHEST) && event.getAction() == Action.RIGHT_CLICK_BLOCK && !event.getPlayer().isSneaking()){
            Location location = event.getClickedBlock().getLocation();
            String team = tHandler.getTeam(event.getPlayer().getUniqueId());
            boolean locked;
            if (((Chest)event.getClickedBlock().getState()).getInventory().getHolder() instanceof DoubleChest doubleChest){
                Chest left = (Chest)doubleChest.getLeftSide();
                Chest right = (Chest)doubleChest.getRightSide();
                locked = tHandler.isLocked(left.getLocation(), team) || tHandler.isLocked(right.getLocation(), team);
            } else {
                locked = plugin.getTeamsHandler().isLocked(location, team);
            }
            if (locked){
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "This chest is locked");
            }

        }
    }

    @EventHandler
    public void onChestBreak(BlockBreakEvent e){
        if (e.getBlock().getType() == Material.CHEST || e.getBlock().getType() == Material.TRAPPED_CHEST){
            Location location = e.getBlock().getLocation();
            String team = plugin.getTeamsHandler().getTeam(e.getPlayer().getUniqueId());
            if (plugin.getTeamsHandler().isLocked(location, team)){
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "This chest is locked");
            }
        }

    }

    @EventHandler
    public void openEnderChest(InventoryOpenEvent e){
        if (e.getInventory().getType() == InventoryType.ENDER_CHEST){
            String team = plugin.getTeamsHandler().getTeam(e.getPlayer().getUniqueId());
            if (team != null){
                MenuData data = new MenuData((Player) e.getPlayer());
                data.setTeam(team);
                BukkitTask task = new BukkitRunnable(){
                    @Override
                    public void run(){
                        e.setCancelled(true);
                        new EnderChestMenu(plugin, data).open();
                    }
                }.runTaskLater(plugin, 1L);
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
