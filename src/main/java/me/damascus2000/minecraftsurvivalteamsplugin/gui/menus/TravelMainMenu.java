package me.damascus2000.minecraftsurvivalteamsplugin.gui.menus;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.Menu;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.MenuData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class TravelMainMenu extends Menu {


    public TravelMainMenu(Main plugin, MenuData menuData){
        super(plugin, menuData);
        this.name = ChatColor.AQUA + "Main Menu";
    }

    @Override
    public void handleMenu(InventoryClickEvent e){
        switch (e.getCurrentItem().getType()){
            case ACTIVATOR_RAIL -> new TravelMenu(plugin, menuData).open();
            case CRAFTING_TABLE -> new CreateWarpMenu(plugin, menuData).open();
            case BARRIER -> e.getView().close();
        }
    }

    @Override
    public void setMenuItems(){
        ItemStack item;
        if (tHandler.getTeam(menuData.getOwner().getUniqueId()) != null){
            item = makeItem(Material.CRAFTING_TABLE, ChatColor.GREEN + "Create Travel Point", ChatColor.BLUE + "Create or set the travel point for your team");
        } else {
            item = makeItem(Material.RED_CONCRETE, ChatColor.RED + "No Team", ChatColor.RED + "Join a team to set your team's travel point");
        }
        inventory.setItem(2, item);
        inventory.setItem(5, makeItem(Material.ACTIVATOR_RAIL, ChatColor.GREEN + "Travel", ChatColor.BLUE + "Travel to a certain travel point"));
        inventory.setItem(8, makeItem(Material.BARRIER, ChatColor.RED + "Cancel"));
    }

    @Override
    public int getSize(){
        return 9;
    }
}
