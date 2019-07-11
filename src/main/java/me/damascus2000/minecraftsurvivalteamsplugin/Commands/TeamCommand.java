package me.damascus2000.minecraftsurvivalteamsplugin.Commands;

import me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand.*;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class TeamCommand implements CommandExecutor {

    private Main plugin;
    private Map<String, TeamSubCommand> subCommands;

    public TeamCommand(Main plugin) {
        this.plugin = plugin;
        subCommands = new HashMap<String, TeamSubCommand>() {
            {
                put("create", new TeamCreate(plugin));
                put("join", new TeamJoin(plugin));
                put("kick", new TeamKick(plugin));
                put("remove", new TeamRemove(plugin));
                put("leave", new TeamLeave(plugin));
                put("changecolor", new TeamChangeColor(plugin));
                put("changefx", new TeamChangeFX(plugin));
                put("info", new TeamInfo(plugin));
                put("list", new TeamList(plugin));
                put("rename", new TeamRename(plugin));

            }
        };
    }




    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // /teams or /teams help
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("-------" + ChatColor.AQUA + "Teams Help" + ChatColor.RESET + "-------");
            sender.sendMessage(ChatColor.AQUA + "/teams help: " + ChatColor.RESET + "Shows this help page.");
            sender.sendMessage(ChatColor.AQUA + "/teams create <teamname> <color>: " + ChatColor.RESET + "Creates (and joins) a team");
            sender.sendMessage(ChatColor.AQUA + "/teams join <team>: " + ChatColor.RESET + "Join a team if it exists");
            sender.sendMessage(ChatColor.AQUA + "/teams leave: " + ChatColor.RESET + "Leave your current team.");
            sender.sendMessage(ChatColor.AQUA + "/teams info [team]: " + ChatColor.RESET + "Shows the info about a team ");
            sender.sendMessage(ChatColor.AQUA + "/teams kick <player>: " + ChatColor.RESET + "Kicks a player from your team");
            sender.sendMessage(ChatColor.AQUA + "/teams remove <team>: " + ChatColor.RESET + "Removes a team if it is empty");
            sender.sendMessage(ChatColor.AQUA + "/teams rename <newname>: " + ChatColor.RESET + "Renames your team");
            sender.sendMessage(ChatColor.AQUA + "/teams changecolor <color>: " + ChatColor.RESET + "Changes the color of your team");
            sender.sendMessage(ChatColor.AQUA + "/teams changeFX <effect>: " + ChatColor.RESET + "Changes the displayed effect of your teamname");
            sender.sendMessage(ChatColor.AQUA + "/teams list: " + ChatColor.RESET + "Gives a list of all the teams on the server");
            sender.sendMessage("-------" + ChatColor.AQUA + "Teams Help" + ChatColor.AQUA + "-------");
        } else if (subCommands.containsKey(args[0])) {
            subCommands.get(args[0].toLowerCase()).doCommand(sender, args);
        }
        //al other
        else {
            sender.sendMessage(ChatColor.DARK_RED + "That is not a legal command, go to /teams help for more info");
        }
        return false;
    }
}
