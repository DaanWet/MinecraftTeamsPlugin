package me.damascus2000.minecraftsurvivalteamsplugin.gui;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class Menu implements InventoryHolder {

    protected final TeamsYmlHandler tHandler;
    protected final Main plugin;
    protected MenuData menuData;
    protected Inventory inventory;
    protected ItemStack FILLER_GLASS = makeItem(Material.GRAY_STAINED_GLASS_PANE, " ");
    protected ItemStack BACK = makeItem(Material.BARRIER, ChatColor.RED + "Back");
    protected String name;
    protected boolean fill = true;

    public Menu(Main plugin, MenuData menuData){
        this.menuData = menuData;
        this.tHandler = plugin.getTeamsHandler();
        this.plugin = plugin;
    }


    public abstract void handleMenu(InventoryClickEvent e);

    public void handleClose(){}


    public abstract void setMenuItems();

    public abstract int getSize();

    public void open(){
        inventory = Bukkit.createInventory(this, getSize(), name);
        this.setMenuItems();
        if (fill)
            this.setFillerGlass();
        menuData.getOwner().openInventory(inventory);
    }

    @Override
    public Inventory getInventory(){
        return inventory;
    }

    public void setFillerGlass(){
        for (int i = 0; i < getSize(); i++){
            if (inventory.getItem(i) == null){
                inventory.setItem(i, FILLER_GLASS);
            }
        }
    }

    public ItemStack makeItem(Material material, String displayName, String... lore){
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        return item;
    }

}


