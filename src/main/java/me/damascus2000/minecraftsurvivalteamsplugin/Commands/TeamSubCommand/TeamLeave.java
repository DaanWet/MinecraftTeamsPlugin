package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;

import me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers.ChatPrefix;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.utils.MessageException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamLeave extends TeamSubCommand {


    public TeamLeave(Main plugin){
        super(plugin);
        succes = "You succesfully left ";
    }

    public void doCommand(CommandSender sender, String[] args) throws MessageException{
        if (!(sender instanceof Player)){
            throw new MessageException(playerError);
        }
        if (args.length != 1){
            throw new MessageException(notLegalError);
        }
        Player player = (Player) sender;
        String team = tHandler.getTeam(player.getUniqueId());
        if (team == null){
            throw new MessageException(notInTeamError);
        }

        tHandler.exitTeam(player.getUniqueId());
        super.sendSucces(player, succes + ChatColor.valueOf(tHandler.getTeamColor(team)) + team);
        new ChatPrefix(plugin).nameChange(player);

    }
}
