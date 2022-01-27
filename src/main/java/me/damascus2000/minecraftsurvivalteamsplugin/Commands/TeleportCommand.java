package me.damascus2000.minecraftsurvivalteamsplugin.Commands;

import me.damascus2000.minecraftsurvivalteamsplugin.GUIBuilder;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {

    private final Main plugin;

    public TeleportCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (args.length == 0){
            if (plugin.getConfig().getBoolean("travel")){
                if (sender instanceof Player){
                    new GUIBuilder((Player) sender, plugin).createMainMenu();
                } else {
                    sender.sendMessage("Only players can use this command");
                }
            } else {
                sender.sendMessage("Travel is not enabled");
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("editcost")){
            sender.sendMessage(ChatColor.GREEN + "Cost: " + plugin.getConfig().getDouble("travelcost"));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("editcost")){
            boolean valid = true;
            if (sender instanceof Player){
                Player player = (Player) sender;
                valid = player.isOp();
            }
            if (valid){
                double cost;
                try {
                    cost = Double.parseDouble(args[1]);
                } catch (Exception exc){
                    cost = -1;
                }
                if (cost > 0){
                    plugin.getConfig().set("travelcost", cost);
                    plugin.saveConfig();
                    sender.sendMessage(ChatColor.GREEN + "Succesfully set the travelcost to " + cost);
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Cost");
                }
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("enable")){
            plugin.getConfig().set("travel", true);
            plugin.saveConfig();
        } else if (args.length == 1 && args[0].equalsIgnoreCase("disable")){
            plugin.getConfig().set("travel", false);
            plugin.saveConfig();
        }
        return true;
    }
}
