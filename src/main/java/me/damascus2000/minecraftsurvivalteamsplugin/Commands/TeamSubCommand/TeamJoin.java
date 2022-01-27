package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;

import me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers.ChatPrefix;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.utils.MessageException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamJoin extends TeamSubCommand {


    public TeamJoin(Main plugin){
        super(plugin);
        succes = "You succesfully joined ";
    }

    // /teams join teamname
    public void doCommand(CommandSender sender, String[] args) throws MessageException{
        if (!(sender instanceof Player)){
            throw new MessageException(playerError);
        }
        Player player = (Player) sender;
        if (tHandler.getTeam(player.getUniqueId()) != null){
            throw new MessageException(inTeamError);
        }

        if (args.length != 2 || !tHandler.checkTeam(args[1])){
            throw new MessageException(teamNameError);
        }

        tHandler.joinTeam(args[1], player.getUniqueId());
        sendSucces(sender, succes + ChatColor.valueOf(tHandler.getTeamColor(args[1])) + args[1]);
        new ChatPrefix(plugin).nameChange(player);

    }
}
