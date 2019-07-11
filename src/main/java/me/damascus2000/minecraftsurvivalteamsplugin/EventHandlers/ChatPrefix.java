package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.PlayerYmlHandler;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class ChatPrefix implements Listener {

    private TeamsYmlHandler tHandler;
    private PlayerYmlHandler pHandler;

    public ChatPrefix(Main plugin){
        tHandler = plugin.getTeamsHandler();
        pHandler = plugin.getplayerHandler();
    }

    public void nameChange(Player p){
        String name = p.getName();
        String team = tHandler.getTeam(name);
        if (team != null){
            String teammember = "" + ChatColor.valueOf(tHandler.getTeamColor(team)) + "" + ChatColor.valueOf(tHandler.getTeamFX(team)) + "[" + team + "] " + ChatColor.RESET + name + "" ;
            p.setDisplayName(teammember);
            p.setPlayerListName(teammember);
        } else {
            String member = "" + ChatColor.WHITE + "" + ChatColor.BOLD  + "[FreeBird] " + ChatColor.RESET + name;
            p.setDisplayName(member);
            p.setPlayerListName(member);
        }
    }
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        nameChange(p);
        pHandler.setTeamChat(p.getName(), false);


    }
    @EventHandler void onPlayerLeaveEvent (PlayerQuitEvent e){
        e.setQuitMessage(e.getPlayer().getDisplayName() + ChatColor.RESET + " is a pussy and stopped playing");
    }
}
