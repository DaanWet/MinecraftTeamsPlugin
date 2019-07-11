package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.PlayerYmlHandler;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.time.temporal.ChronoUnit;


public class Chat implements Listener {

    private TeamsYmlHandler tHandler;
    private PlayerYmlHandler pHandler;


    public Chat(Main plugin){
        tHandler = plugin.getTeamsHandler();
        pHandler = plugin.getplayerHandler();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        if (pHandler.checkTeamChat(e.getPlayer().getName()).equalsIgnoreCase("true")){
            e.setCancelled(true);
            for (String player : tHandler.getTeamMembers(tHandler.getTeam(e.getPlayer().getName()))) {
                Bukkit.getServer().getPlayer(player).sendMessage(ChatColor.AQUA + "TC: " + ChatColor.RESET + e.getPlayer().getDisplayName() + ": " + e.getMessage());
            }
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "TC: " + ChatColor.RESET + e.getPlayer().getDisplayName() + ": " + e.getMessage());
        } else{
            e.setFormat("%1$s: %2$s");
        }


    }
}
