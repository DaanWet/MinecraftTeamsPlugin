package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;

import me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers.ChatPrefix;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.utils.MessageException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class TeamCreate extends TeamSubCommand {

    public TeamCreate(Main plugin){
        super(plugin);
        succes = "You succesfully created ";

    }

    // /teams create teamname teamcolor
    public void doCommand(CommandSender sender, String[] args) throws MessageException{
        if (args.length <= 1 || tHandler.checkTeam(args[1])){
            throw new MessageException(existingNameError);
        }
        if (args.length != 3 || !colors.contains(args[2].toLowerCase())){
            throw new MessageException(colorError);
        }

        if (sender instanceof Player){
            Player player = (Player) sender;
            if (tHandler.getTeam(player.getUniqueId()) != null){
                throw new MessageException(inTeamError);
            }
            tHandler.createTeam(args[1], args[2], Collections.singletonList(player.getUniqueId()));
            sendSucces(sender, succes + ChatColor.valueOf(tHandler.getTeamColor(args[1])) + args[1]);
            new ChatPrefix(plugin).nameChange(player);
        } else {
            tHandler.createTeam(args[1], args[2], Collections.emptyList());
            sendSucces(sender, succes + ChatColor.valueOf(tHandler.getTeamColor(args[1])) + args[1]);
        }

    }

}
