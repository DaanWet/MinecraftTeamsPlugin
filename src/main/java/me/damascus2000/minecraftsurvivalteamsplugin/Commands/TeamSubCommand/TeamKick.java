package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;

import me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers.ChatPrefix;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.utils.MessageException;
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
    public void doCommand(CommandSender sender, String[] args) throws MessageException{
        if (!(sender instanceof Player)){
            throw new MessageException(playerError);
        }
        Player player = (Player) sender;
        if (!player.hasPermission("teams.kick")){
            return;
        }
        String team = tHandler.getTeam(player.getUniqueId());
        if (team == null){
            throw new MessageException(notInTeamError);
        }
        if (args.length != 2){
            throw new MessageException(playernameError);
        }

        Player player1 = plugin.getServer().getPlayerExact(args[1]);


        if (player1 == null || !team.equals(tHandler.getTeam(player1.getUniqueId()))){
            throw new MessageException(notmemberError);
        }
        tHandler.exitTeam(player1.getUniqueId());
        sendSucces(sender, succes + ChatColor.RED + args[1]);
        if (Bukkit.getOnlinePlayers().contains(player1)){
            new ChatPrefix(plugin).nameChange(player1);
        }
    }
}
