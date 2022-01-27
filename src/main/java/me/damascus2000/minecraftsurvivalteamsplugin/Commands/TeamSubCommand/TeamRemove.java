package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.utils.MessageException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class TeamRemove extends TeamSubCommand {

    private final String notEmptyError = " is not an empty team.";

    public TeamRemove(Main plugin){
        super(plugin);
        succes = "You succesfully removed the team ";
    }


    // /teams remove teamname
    public void doCommand(CommandSender sender, String[] args) throws MessageException{
        if (args.length != 2 || !tHandler.checkTeam(args[1])){
            throw new MessageException(teamNameError);
        }
        String color = tHandler.getTeamColor(args[1]);
        if (!tHandler.getTeamMembers(args[1]).isEmpty()){
            throw new MessageException(ChatColor.valueOf(color) + args[1] + ChatColor.DARK_RED + notEmptyError);
        }
        tHandler.deleteTeam(args[1]);
        sendSucces(sender, succes + ChatColor.valueOf(color) + args[1]);

    }
}
