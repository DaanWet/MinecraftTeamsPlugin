package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.PlayerYmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Sleep implements Listener {
    private final Main plugin;
    private PlayerYmlHandler pHandler;

    public Sleep(Main plugin) {
        this.plugin = plugin;
        pHandler = plugin.getPlayerHandler();
    }

    @EventHandler
    public void onSleep(final PlayerBedEnterEvent e) {
        if (e.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            int counter = 0;
            World w = e.getPlayer().getWorld();
            int onlinepl = w.getPlayers().size();
            for (Player player : w.getPlayers()) {
                player.sendMessage(ChatColor.YELLOW + e.getPlayer().getName() + " is going to bed");
                if (player.getUniqueId().equals(e.getPlayer().getUniqueId())){
                    counter++;
                    System.out.println(player.getName() + ": Initiated sleep");
                } else {
                    if (player.isSleeping()) {
                        counter++;
                    }
                    if (pHandler.isAFK(player.getName())){
                        onlinepl--;
                    }
                }
            }
            BukkitRunnable br = new BukkitRunnable(){
                @Override
                public void run(){
                    World w = e.getPlayer().getWorld();
                    if (w.getTime() >= 12542 || w.getTime() <= 1000) {
                        w.setTime(1000);
                    }
                    w.setStorm(false);
                    w.setThundering(false);
                }
            };
            if (onlinepl == 2){
                br.runTaskLater(plugin, 40);
            } else if((counter * 100) / onlinepl >= 60) {
                br.runTaskLater(plugin, 40);
            }
        }

    }
}