package me.damascus2000.minecraftsurvivalteamsplugin.Commands;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.UUID;

public class ClaimPerm implements CommandExecutor {


    private HashMap<UUID, PermissionAttachment> attachments;
    private final Main plugin;

    public ClaimPerm(Main plugin){
        this.attachments =  new HashMap<>();
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            PermissionAttachment attachment = player.addAttachment(plugin);
            attachment.setPermission("bukkit.command.timings",true);
            attachment = player.addAttachment(plugin);
            attachment.setPermission("bukkit.command.tps",true);
            attachment = player.addAttachment(plugin);
            attachment.setPermission("minecraft.command.scoreboard",true);
        }
        return true;
    }
}
