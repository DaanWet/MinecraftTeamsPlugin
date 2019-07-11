package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class TeamList extends TeamSubCommand {


    public TeamList(Main plugin){
        super(plugin);
    }

    public void doCommand(CommandSender sender, String[] args){
        for (String team : tHandler.getTeams()){
            sender.sendMessage(ChatColor.valueOf(tHandler.getTeamColor(team)) + team + ": " + ChatColor.RESET + tHandler.getTeamMembers(team).size() + " members");
        }
    }
}
