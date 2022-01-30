package me.damascus2000.minecraftsurvivalteamsplugin.gui.menus;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.Menu;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.MenuData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class FuelMenu extends Menu {


    public FuelMenu(Main plugin, MenuData menuData){
        super(plugin, menuData);
    }

    @Override
    public void handleMenu(InventoryClickEvent e){
        switch (e.getCurrentItem().getType()){
            case GREEN_CONCRETE -> {
                ItemStack fuel = e.getClickedInventory().getItem(13);
                if (fuel.getType() == Material.COAL && fuel.getAmount() == menuData.getCost()){
                    menuData.getOwner().teleport(menuData.getWarp());
                }
            }
            case COAL -> e.setCancelled(false);
            case BARRIER -> new TravelMenu(plugin, menuData).open();
        }
    }

    @Override
    public void setMenuItems(){
        Location warp = menuData.getWarp();
        Location currentLoc = menuData.getOwner().getLocation();
        Location vector = currentLoc.subtract(warp);
        double x = vector.getX();
        double y = vector.getY();
        double z = vector.getZ();
        double flatdistance = Math.sqrt(x * x + y * y);
        double distance = Math.sqrt(flatdistance * flatdistance + z * z);
        int cost = (int) (plugin.getConfig().getDouble("travelcost") * distance);
        menuData.setCost(cost);
        ItemStack black = makeItem(Material.BLACK_STAINED_GLASS_PANE, ChatColor.GRAY + String.format("Deposit %s coal", cost));
        for (int i = 0; i < 27; i++){
            if (i != 13){
                inventory.setItem(i, black);
            }
        }
        String desc = ChatColor.GRAY + String.format("%s coal", cost);
        String team = tHandler.getTeam(menuData.getOwner().getUniqueId());
        inventory.setItem(25, makeItem(Material.GREEN_CONCRETE, ChatColor.GREEN + "Confirm", desc, team));
        inventory.setItem(26, BACK);
        inventory.setMaxStackSize(cost);
    }

    @Override
    public int getSize(){
        return 27;
    }
}
