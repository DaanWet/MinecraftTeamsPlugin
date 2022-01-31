package me.damascus2000.minecraftsurvivalteamsplugin.Commands;

import me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand.*;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.utils.MessageException;
import me.damascus2000.minecraftsurvivalteamsplugin.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TeamCommand implements CommandExecutor {

    private final Main plugin;
    private final Map<String, TeamSubCommand> subCommands;

    public TeamCommand(Main plugin){
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
                put("lock", new ChestLockCommand(plugin));
            }
        };
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        // /teams or /teams help
        if (args.length == 0 || args[0].equalsIgnoreCase("help")){
            sendHelp(sender, 1);
        } else if (subCommands.containsKey(args[0].toLowerCase())){
            try {
                subCommands.get(args[0].toLowerCase()).doCommand(sender, args);
            } catch (MessageException exc){
                sender.sendMessage(ChatColor.DARK_RED + exc.getMessage());
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("help") && Utils.isInt(args[1])){
            sendHelp(sender, Integer.parseUnsignedInt(args[1]));
        }
        //all other
        else {
            sender.sendMessage(ChatColor.DARK_RED + "That is not a legal command, go to /teams help for more info");
        }
        return false;
    }


    public void sendHelp(CommandSender player, int page){
        player.sendMessage("-------" + ChatColor.AQUA + "Teams Help" + ChatColor.RESET + "-------");
        switch (page){
            case 1 -> {

                sendMessage(player, "/teams help: " + ChatColor.RESET + "Shows this help page.");
                sendMessage(player,  "/teams create <teamname> <color>: " + ChatColor.RESET + "Creates (and joins) a team");
                sendMessage(player, "/teams join <team>: " + ChatColor.RESET + "Join a team if it exists");
                sendMessage(player, "/teams leave: " + ChatColor.RESET + "Leave your current team.");
                sendMessage(player, "/teams info [team]: " + ChatColor.RESET + "Shows the info about a team ");
                sendMessage(player,  "/teams kick <player>: " + ChatColor.RESET + "Kicks a player from your team");
                sendMessage(player,  "/teams remove <team>: " + ChatColor.RESET + "Removes a team if it is empty");
                sendMessage(player,  "/teams rename <newname>: " + ChatColor.RESET + "Renames your team");
            }
            case 2 -> {
                sendMessage(player,  "/teams changecolor <color>: " + ChatColor.RESET + "Changes the color of your team");
                sendMessage(player,  "/teams changeFX <effect>: " + ChatColor.RESET + "Changes the displayed effect of your teamname");
                sendMessage(player,  "/teams list: " + ChatColor.RESET + "Gives a list of all the teams on the server");
                sendMessage(player, "/teams lock: " + ChatColor.RESET + "Locks the chest you're looking at");
            }
        }
        player.sendMessage("-------" + ChatColor.AQUA + "Teams Help - page" + page + ChatColor.RESET + "-------");
    }

    private void sendMessage(CommandSender player, String msg){
        player.sendMessage(ChatColor.AQUA + msg);
    }

}
