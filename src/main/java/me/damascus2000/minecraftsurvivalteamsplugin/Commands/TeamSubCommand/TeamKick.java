package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;

import me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers.ChatPrefix;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamKick extends TeamSubCommand {

    private final String playernameError = "You have to give a valid playername to kick from your team";
    private final String notmemberError = " is not a member of your team";

    public TeamKick(Main plugin){
        super(plugin);
        succes = "The following player has successfully been kicked from your team: ";

    }

    // /teams kick playername
    public void doCommand(CommandSender sender, String[] args){
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("teams.kick")){
                String team = tHandler.getTeam(player.getName());
                if (team != null){
                    if (args.length == 2){
                        if (team.equals(tHandler.getTeam(args[1]))){
                            tHandler.exitTeam(args[1]);
                            sendSucces(sender, succes + ChatColor.RED + args[1]);
                            Player player2 = Bukkit.getPlayer(args[1]);
                            if (Bukkit.getOnlinePlayers().contains(player2)){
                                new ChatPrefix(plugin).nameChange(player2);
                            }
                        } else {
                            sendError(sender, notmemberError);
                        }
                    } else {
                        sendError(sender, playernameError);
                    }
                } else {
                    sendError(sender, notInTeamError);
                }
            }
        } else {
            sendError(sender, playerError);
        }
    }
}
