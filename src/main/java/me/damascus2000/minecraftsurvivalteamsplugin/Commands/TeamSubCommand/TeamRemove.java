package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.GrassSpecies;
import org.bukkit.command.CommandSender;

public class TeamRemove extends TeamSubCommand {

    private final String notEmptyError = " is not an empty team.";

    public TeamRemove(Main plugin){
        super(plugin);
        succes = "You succesfully removed the team ";
    }


    // /teams remove teamname
    public void doCommand(CommandSender sender, String[] args){
        if (args.length == 2 && tHandler.checkTeam(args[1])){
            String color = tHandler.getTeamColor(args[1]);
            if (tHandler.getTeamMembers(args[1]).isEmpty()){
                tHandler.deleteTeam(args[1]);
                sendSucces(sender, succes + ChatColor.valueOf(color) + args[1]);
            } else {
                sendError(sender, ChatColor.valueOf(color) + args[1] + ChatColor.DARK_RED + notEmptyError);
            }
        } else {
            sendError(sender, teamNameError);
        }
    }
}
