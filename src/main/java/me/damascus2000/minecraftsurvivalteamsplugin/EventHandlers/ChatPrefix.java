package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.PlayerYmlHandler;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;


public class ChatPrefix {

    private TeamsYmlHandler tHandler;
    private PlayerYmlHandler pHandler;
    private AFKHandler afkHandler;

    public ChatPrefix(Main plugin){
        tHandler = plugin.getTeamsHandler();
        pHandler = plugin.getPlayerHandler();
    }

    public void nameChange(Player p){
        String name = p.getName();
        String team = tHandler.getTeam(name);
        String displayname;
        if (team != null){
            displayname = "" + ChatColor.valueOf(tHandler.getTeamColor(team)) + "" + ChatColor.valueOf(tHandler.getTeamFX(team)) + "[" + team + "] " + ChatColor.RESET + name + "" ;

        } else {
            displayname = "" + ChatColor.WHITE + "" + ChatColor.BOLD  + "[FreeBird] " + ChatColor.RESET + name;

        }
        if (pHandler.isAFK(p.getName())){
            displayname = ChatColor.GRAY + "" + ChatColor.stripColor(displayname);
        }
        p.setDisplayName(displayname);
        p.setPlayerListName(displayname);

    }

}
