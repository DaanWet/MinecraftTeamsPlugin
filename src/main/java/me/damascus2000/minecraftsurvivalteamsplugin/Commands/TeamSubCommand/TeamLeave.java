package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;

import me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers.ChatPrefix;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamLeave extends TeamSubCommand {


    public TeamLeave(Main plugin){
        super(plugin);
        succes = "You succesfully left ";
    }

    public void doCommand(CommandSender sender, String[] args){
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (args.length == 1){
                String team = tHandler.getTeam(player.getName());
                if (team != null){
                    tHandler.exitTeam(player.getName());
                    super.sendSucces(player, succes + ChatColor.valueOf(team) + team);
                    new ChatPrefix(plugin).nameChange(player);
                } else{
                    super.sendError(sender, notInTeamError);
                }
            } else{
                super.sendError(sender, notLegalError);
            }
        } else{
            super.sendError(sender, playerError);
        }
    }
}
