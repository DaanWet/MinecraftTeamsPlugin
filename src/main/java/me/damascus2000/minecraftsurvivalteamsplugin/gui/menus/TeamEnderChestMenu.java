package me.damascus2000.minecraftsurvivalteamsplugin.gui.menus;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.Menu;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.MenuData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class TeamEnderChestMenu extends Menu {

    public TeamEnderChestMenu(Main plugin, MenuData menuData){
        super(plugin, menuData);
        String team = menuData.getTeam();
        this.name = ChatColor.valueOf(tHandler.getTeamColor(team)) + team + "Ender Chest";
        this.fill = false;
    }

    @Override
    public void handleMenu(InventoryClickEvent e){
        if (e.getRawSlot() < 27 || e.getRawSlot() >= 36){
            e.setCancelled(false);
        }
        if (e.getRawSlot() == 31 && (e.getCursor() == null || e.getCursor().getType() == Material.AIR)){
            new EnderChestMenu(plugin, menuData).open();
        }
    }

    @Override
    public void handleClose(){
        ItemStack[] chest = Arrays.copyOfRange(inventory.getContents(), 0, 27);
        System.out.println(Arrays.toString(chest));
        tHandler.setEnderChest(menuData.getTeam(), chest);
        plugin.closeEnder(menuData.getTeam());
    }

    @Override
    public void setMenuItems(){
        String team = menuData.getTeam();
        plugin.setEnderOpen(team, menuData.getOwner());

        ItemStack[] chest = tHandler.getEnderChest(team);
        System.out.println(chest);
        if (chest != null){
            for (int i = 0; i < 27; i++){
                inventory.setItem(i, chest[i]);
            }
        }
        for (int i = 27; i < getSize(); i++){
            inventory.setItem(i, FILLER_GLASS);
        }
        inventory.setItem(31, makeItem(Material.ENDER_CHEST, ChatColor.BLUE + "Your Ender Chest", ChatColor.WHITE + "Open Your Ender Chest"));
    }


    @Override
    public int getSize(){
        return 4 * 9;
    }
}
