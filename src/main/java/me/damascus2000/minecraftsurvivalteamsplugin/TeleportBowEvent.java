package me.damascus2000.minecraftsurvivalteamsplugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class TeleportBowEvent implements Listener {

    private Main plugin;

    public TeleportBowEvent(Main plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onBowShoot(ProjectileHitEvent e){
        if(e.getEntity() instanceof Arrow){
            Player player = (Player) e.getEntity().getShooter();
            ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
            if (itemMeta.hasLore()) {
                String name = plugin.getConfig().getString("bow-name");
                String comparedname = ChatColor.translateAlternateColorCodes('&', (name.startsWith("&f")) ? name.substring(2) : name);
                String lore = plugin.getConfig().getString("bow-description");
                String comparedlore = ChatColor.translateAlternateColorCodes('&', (lore.startsWith("&f")) ? lore.substring(2) : lore);
                if (itemMeta.getDisplayName().equals(comparedname) && itemMeta.getLore().get(0).equals(comparedlore)) {
                    //.(plugin.getConfig().getString("bow-name"))
                    //(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.getConfig().getString("bow-name")))
                    //ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("bow-name"))))
                    Location location = e.getEntity().getLocation();

                    player.teleport(location);
                    player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("landed-msg")));

                } else {
                    player.sendMessage(ChatColor.RED + "You do not have the right bow to teleport!");
                    System.out.println(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
                }
            }
        }
    }

}

