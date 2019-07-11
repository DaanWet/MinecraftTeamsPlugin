package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;


import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamInfo extends TeamSubCommand {

    public TeamInfo(Main plugin){
        super(plugin);
    }

    // /teams info [teamname]
    public void doCommand(CommandSender sender, String[] args){
        if (args.length == 1 && sender instanceof Player){
            String team = tHandler.getTeam(sender.getName());
            if (team != null){
                String color = tHandler.getTeamColor(team);
                sendMessage(sender, "Your team:");
                sendMessage(sender, "Name " + ChatColor.valueOf(color) + team);
                sendMessage(sender, "Members " + ChatColor.valueOf(color) + team);
            } else{
                sendError(sender, notInTeamError);
            }

        } else if(args.length == 2 && tHandler.checkTeam(args[1])){
            String color = tHandler.getTeamColor(args[1]);
            sendMessage(sender, "Name: " + ChatColor.valueOf(color) + args[1]);
            sendMessage(sender, "Members: " + ChatColor.valueOf(color) + tHandler.getTeamMembers(args[1]));
        } else{
            sendError(sender, teamNameError);
        }

    }
}

