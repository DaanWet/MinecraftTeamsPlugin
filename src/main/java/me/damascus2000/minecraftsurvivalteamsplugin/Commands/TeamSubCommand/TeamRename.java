package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;

import me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers.ChatPrefix;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.utils.MessageException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamRename extends TeamSubCommand {


    public TeamRename(Main plugin){
        super(plugin);
        succes = "You succesfully renamed the name of the team ";

    }

    // /teams rename [teamname] name
    public void doCommand(CommandSender sender, String[] args) throws MessageException{
        if (sender instanceof Player){
            String team = tHandler.getTeam(((Player) sender).getPlayer().getUniqueId());
            if (team == null){
                throw new MessageException(notInTeamError);
            }
            if (args.length != 2 || tHandler.checkTeam(args[1])){
                throw new MessageException(existingNameError);
            }

            tHandler.renameTeam(team, args[1]);
            sendSucces(sender, succes + team + " to " + args[1]);
            for (Player onlineplayer : Bukkit.getOnlinePlayers()){
                if (tHandler.getTeamMembers(args[1]).contains(onlineplayer.getName())){
                    new ChatPrefix(plugin).nameChange(onlineplayer);
                }
            }

        } else {
            if (args.length < 2 || !tHandler.checkTeam(args[1])){
                throw new MessageException(teamNameError);
            }

            if (args.length != 3 || tHandler.checkTeam(args[2])){
                throw new MessageException(existingNameError);
            }

            tHandler.renameTeam(args[1], args[2]);
            sendSucces(sender, succes + args[1] + " to " + args[2]);

        }
    }
}
