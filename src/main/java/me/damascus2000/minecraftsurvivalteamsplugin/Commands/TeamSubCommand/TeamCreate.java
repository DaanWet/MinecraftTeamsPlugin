package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;

import me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers.ChatPrefix;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.PlayerYmlHandler;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TeamCreate extends TeamSubCommand {

    public TeamCreate(Main plugin){
        super(plugin);
        succes = "You succesfully created ";

    }

    // /teams create teamname teamcolor
    public void doCommand(CommandSender sender, String[] args){
        if (args.length > 1 && !(tHandler.checkTeam(args[1]))){
            if (args.length == 3 && (colors.contains(args[2].toLowerCase()))){
                if (sender instanceof Player){
                    Player player = (Player) sender;
                    if (tHandler.getTeam(player.getName()) == null) {
                        String [] list = {player.getName()};
                        tHandler.createTeam(args[1], args[2], Arrays.asList(list));
                        sendSucces(sender, succes + ChatColor.valueOf(tHandler.getTeamColor(args[1])) + args[1]);
                        new ChatPrefix(plugin).nameChange(player);
                    } else {
                        sendError(sender, inTeamError);
                    }
                } else {
                    String[] list = {};
                    tHandler.createTeam(args[1], args[2], Arrays.asList(list));
                    sendSucces(sender, succes + ChatColor.valueOf(tHandler.getTeamColor(args[1])) + args[1]);
                }
            } else {
                sendError(sender, colorError);
            }
        } else{
            sendError(sender, existingNameError);
        }
    }

}
