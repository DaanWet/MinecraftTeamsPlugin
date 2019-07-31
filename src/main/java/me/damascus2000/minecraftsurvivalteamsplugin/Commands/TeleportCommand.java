package me.damascus2000.minecraftsurvivalteamsplugin.Commands;

import me.damascus2000.minecraftsurvivalteamsplugin.GUIBuilder;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {

    private Main plugin;

    public TeleportCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (plugin.getConfig().getBoolean("travel")){
            if (sender instanceof Player){

                new GUIBuilder((Player) sender, plugin).createMainMenu();
            } else {
                sender.sendMessage("Only players can use this command");
            }
        } else {
            sender.sendMessage("Travel is not enabled");
        }
        return false;
    }
}
