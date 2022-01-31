package me.damascus2000.minecraftsurvivalteamsplugin.gui.menus;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.Menu;
import me.damascus2000.minecraftsurvivalteamsplugin.gui.MenuData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OpenTeamEChest extends Menu {
    public OpenTeamEChest(Main plugin, MenuData menuData){
        super(plugin, menuData);
        String team = menuData.getTeam();
        this.FILLER_GLASS = makeItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "Team Ender Chest Opened by " + plugin.isEnderOpen(team).getName());
        this.name = ChatColor.valueOf(tHandler.getTeamColor(team)) + team + "Ender Chest";
    }

    @Override
    public void handleMenu(InventoryClickEvent e){
        if (e.getRawSlot() == 31 && (e.getCursor() == null || e.getCursor().getType() == Material.AIR)){
            new EnderChestMenu(plugin, menuData).open();
        }
    }

    @Override
    public void setMenuItems(){
        inventory.setItem(31, makeItem(Material.ENDER_CHEST, ChatColor.BLUE + "Your Ender Chest", ChatColor.WHITE + "Open Your Ender Chest"));
    }

    @Override
    public int getSize(){
        return 4 * 9;
    }
}
