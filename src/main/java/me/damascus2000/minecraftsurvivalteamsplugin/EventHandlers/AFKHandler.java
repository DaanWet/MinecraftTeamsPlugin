package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.PlayerYmlHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class AFKHandler implements Listener {

    private final Main plugin;
    private final PlayerYmlHandler pHandler;
    private final HashMap<UUID, BukkitTask> starts = new HashMap<>();
    private final HashMap<UUID, BukkitTask> kicks = new HashMap<>();
    private final HashMap<UUID, Long> times = new HashMap<>();

    public AFKHandler(Main plugin){
        this.plugin = plugin;
        this.pHandler = plugin.getPlayerHandler();
    }


    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Location from = e.getFrom();
        Location to = e.getTo();
        if (to != null && (from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ())){
            removePlayer(e.getPlayer());
            pHandler.setAFK(e.getPlayer().getUniqueId(), false);
            new ChatPrefix(plugin).nameChange(e.getPlayer());
            addPlayer(e.getPlayer());
        }

    }

    public void addPlayer(Player player){
        if (plugin.getPlayerHandler().isAFK(player.getUniqueId())){
            long timeTillKick = player.getStatistic(Statistic.PLAY_ONE_MINUTE) - (2 * pHandler.getAFKTIme(player.getUniqueId()));
            BukkitTask kickTask = new BukkitRunnable() {
                @Override
                public void run(){
                    player.kickPlayer("You have been AFK for too long");
                }
            }.runTaskLater(plugin, timeTillKick);
            kicks.put(player.getUniqueId(), kickTask);
        } else {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run(){
                    Bukkit.getServer().broadcastMessage(player.getDisplayName() + " has gone AFK");
                    plugin.getPlayerHandler().setAFK(player.getUniqueId(), true);
                    new ChatPrefix(plugin).nameChange(player);

                    long timeTillKick = player.getStatistic(Statistic.PLAY_ONE_MINUTE) - (2 * pHandler.getAFKTIme(player.getUniqueId()) - 4800L);

                    BukkitTask kickTask = new BukkitRunnable() {
                        @Override
                        public void run(){
                            player.kickPlayer("You have been AFK for too long");
                        }
                    }.runTaskLater(plugin, timeTillKick);

                    kicks.put(player.getUniqueId(), kickTask);


                }
            }.runTaskLater(plugin, 4800L);
            starts.put(player.getUniqueId(), task);
        }
        times.put(player.getUniqueId(), player.getWorld().getFullTime());


    }


    public void removePlayer(Player player){
        UUID playerID = player.getUniqueId();
        if (pHandler.isAFK(player.getUniqueId())){
            long dur = player.getWorld().getFullTime() - times.get(player.getUniqueId());
            Bukkit.getServer().broadcastMessage(player.getName() + " has been AFK for " + dur + " ticks or " + (dur / (20 * 60)) + " minutes");
            pHandler.setAFKTime(player.getUniqueId(), dur + pHandler.getAFKTIme(player.getUniqueId()));
            Bukkit.getServer().broadcastMessage("Total afk time: " + pHandler.getAFKTIme(player.getUniqueId()));
            kicks.get(playerID).cancel();
            kicks.remove(playerID);
        }

        if (starts.containsKey(playerID)){
            starts.get(playerID).cancel();
            starts.remove(playerID);
        }
        times.remove(playerID);

    }


}
