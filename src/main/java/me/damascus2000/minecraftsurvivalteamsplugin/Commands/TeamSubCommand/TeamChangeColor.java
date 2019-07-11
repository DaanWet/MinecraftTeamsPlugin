package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;

import me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers.ChatPrefix;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamChangeColor extends TeamSubCommand {

    public TeamChangeColor(Main plugin){
        super(plugin);
        succes = "You sucesfully changed the color of ";
    }

    // /teams changecolor color
    public void doCommand(CommandSender sender, String[] args){
        if (args.length == 2 && colors.contains(args[1])){
            if (sender instanceof Player){
                Player player = (Player) sender;
                String team = tHandler.getTeam(player.getName());
                if (team != null){
                    tHandler.setTeamColor(args[1], team);
                    sendSucces(sender, succes + team + " to " + ChatColor.valueOf(args[1].toUpperCase()) + args[1]);
                    for (Player onlineplayer : Bukkit.getOnlinePlayers()){
                        if (tHandler.getTeamMembers(team).contains(onlineplayer.getName())) {
                            new ChatPrefix(plugin).nameChange(onlineplayer);
                        }
                    }
                } else {
                    sendError(sender, notInTeamError);
                }
            } else {
                sendError(sender, playerError);
            }
        } else{
            sendError(sender, colorError);
        }

    }
}
