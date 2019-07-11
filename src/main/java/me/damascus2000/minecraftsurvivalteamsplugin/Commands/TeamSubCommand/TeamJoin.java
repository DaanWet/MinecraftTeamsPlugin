package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;

import me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers.ChatPrefix;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamJoin extends TeamSubCommand {


    public TeamJoin(Main plugin){
        super(plugin);
        succes = "You succesfully joined ";
    }

    // /teams join teamname
    public void doCommand(CommandSender sender, String[] args){
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (tHandler.getTeam(player.getName()) == null){
                if (args.length == 2 && tHandler.checkTeam(args[1])){
                    tHandler.joinTeam(args[1], player.getName());
                    sendSucces(sender, succes + ChatColor.valueOf(tHandler.getTeamColor(args[1])) + args[1]);
                    new ChatPrefix(plugin).nameChange(player);
                } else{
                    sendError(sender, teamNameError);
                }
            } else{
                sendError(sender, inTeamError);
            }
        } else{
            sendError(sender, playerError);
        }

    }
}
