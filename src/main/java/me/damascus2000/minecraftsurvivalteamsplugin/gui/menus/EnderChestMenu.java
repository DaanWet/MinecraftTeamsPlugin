package me.damascus2000.minecraftsurvivalteamsplugin.gui.menus;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.Menu;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.MenuData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class EnderChestMenu extends Menu {


    public EnderChestMenu(Main plugin, MenuData menuData){
        super(plugin, menuData);
        this.name = "Your Ender Chest";
        this.fill = false;
    }

    @Override
    public void handleMenu(InventoryClickEvent e){
        if (e.getRawSlot() < 27 || e.getRawSlot() > 36){
            e.setCancelled(false);
        }
        if (e.getRawSlot() == 31 && (e.getCursor() == null || e.getCursor().getType() == Material.AIR)){
            if (plugin.isEnderOpen(menuData.getTeam()) == null){
                new TeamEnderChestMenu(plugin, menuData).open();
            } else {
                new OpenTeamEChest(plugin, menuData).open();
            }
        }
    }

    @Override
    public void handleClose(){
        ItemStack[] chest = Arrays.copyOfRange(inventory.getContents(), 0, 27);
        menuData.getOwner().getEnderChest().setContents(chest);
    }

    @Override
    public void setMenuItems(){
        Inventory chest = menuData.getOwner().getEnderChest();
        for (int i = 0; i < 27; i++){
            inventory.setItem(i, chest.getItem(i));
        }
        for (int i = 27; i < getSize(); i++){
            inventory.setItem(i, FILLER_GLASS);
        }
        String team = menuData.getTeam();
        inventory.setItem(31, makeItem(Material.ENDER_CHEST, ChatColor.BLUE + "Team Enderchest", ChatColor.valueOf(tHandler.getTeamColor(team)) + "Open " + team + " Ender Chest"));
    }

    @Override
    public int getSize(){
        return 4 * 9;
    }
}
