package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.PlayerYmlHandler;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;


public class Chat implements Listener {

    private final TeamsYmlHandler tHandler;
    private final PlayerYmlHandler pHandler;
    private final AFKHandler afkHandler;
    private final Main plugin;


    public Chat(Main plugin){
        this.plugin = plugin;
        tHandler = plugin.getTeamsHandler();
        pHandler = plugin.getPlayerHandler();
        afkHandler = plugin.getAfkHandler();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        if (pHandler.checkTeamChat(e.getPlayer().getUniqueId())){
            e.setCancelled(true);
            for (UUID player : tHandler.getTeamMembers(tHandler.getTeam(e.getPlayer().getUniqueId()))){
                Player pl = Bukkit.getServer().getPlayer(player);
                if (pl != null){
                    pl.sendMessage(ChatColor.AQUA + "TC: " + ChatColor.RESET + e.getPlayer().getDisplayName() + ": " + e.getMessage());
                }
            }
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "TC: " + ChatColor.RESET + e.getPlayer().getDisplayName() + ": " + e.getMessage());
        } else {
            e.setFormat("%1$s: %2$s");
        }


    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        new ChatPrefix(plugin).nameChange(p);
        pHandler.setTeamChat(p.getUniqueId(), false);
        pHandler.setAFK(p.getUniqueId(), false);
        afkHandler.addPlayer(e.getPlayer());


    }

    @EventHandler
    public void onPlayerLeaveEvent(PlayerQuitEvent e){
        //afkHandler.removePlayer(e.getPlayer());
        e.setQuitMessage(e.getPlayer().getDisplayName() + ChatColor.RESET + " is a pussy and stopped playing");
    }

}
