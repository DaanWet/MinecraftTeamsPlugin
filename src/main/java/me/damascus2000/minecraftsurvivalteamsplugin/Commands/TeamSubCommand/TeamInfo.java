package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;


import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.utils.MessageException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamInfo extends TeamSubCommand {

    public TeamInfo(Main plugin){
        super(plugin);
    }

    // /teams info [teamname]
    public void doCommand(CommandSender sender, String[] args) throws MessageException{
        // Info about team of the player executing the command
        if (args.length == 1 && sender instanceof Player){
            String team = tHandler.getTeam(((Player) sender).getPlayer().getUniqueId());
            if (team == null)
                throw new MessageException(notInTeamError);

            String color = tHandler.getTeamColor(team);
            sendMessage(sender, "Your team:");
            sendMessage(sender, "Name " + ChatColor.valueOf(color) + team);
            sendMessage(sender, "Members " + ChatColor.valueOf(color) + team);

            return;
        }
        // Info about a specific team
        if (args.length != 2 || !tHandler.checkTeam(args[1])){
            throw new MessageException(teamNameError);
        }

        String color = tHandler.getTeamColor(args[1]);
        sendMessage(sender, "Name: " + ChatColor.valueOf(color) + args[1]);
        sendMessage(sender, "Members: " + ChatColor.valueOf(color) + tHandler.getTeamMembers(args[1]).stream().map(m -> plugin.getServer().getOfflinePlayer(m).getName()));


    }
}

