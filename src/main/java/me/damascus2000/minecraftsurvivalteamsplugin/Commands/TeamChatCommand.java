package me.damascus2000.minecraftsurvivalteamsplugin.Commands;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.PlayerYmlHandler;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamChatCommand implements CommandExecutor {

    private Main plugin;

    public TeamChatCommand(Main plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        TeamsYmlHandler tHandler = plugin.getTeamsHandler();
        PlayerYmlHandler pHandler = plugin.getPlayerHandler();
        if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    if (tHandler.getTeam(player.getName()) != null){
                        if (pHandler.checkTeamChat(player.getName()).equalsIgnoreCase("false")) {
                            pHandler.setTeamChat(player.getName(), true);
                            player.sendMessage(ChatColor.GREEN + "You have enabled teamchat");
                        } else {
                            pHandler.setTeamChat(player.getName(), false);
                            player.sendMessage(ChatColor.GREEN + "You have disabled teamchat");
                        }
                    } else {
                        player.sendMessage(ChatColor.DARK_RED + "You have to be part of a team to perform this command");
                    }
                }
            } else{
                sender.sendMessage(ChatColor.DARK_RED + "You have to be a player to perform this command");
            }
        return false;
    }
}
