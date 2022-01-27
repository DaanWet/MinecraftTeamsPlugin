package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;

import me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers.ChatPrefix;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.utils.MessageException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class TeamChangeFX extends TeamSubCommand {

    private final List<String> effects = Arrays.asList("magic", "bold", "italic", "strikethrough", "underline");
    private final String effectsError = "You have to pick a (valid) effect";

    public TeamChangeFX(Main plugin){
        super(plugin);
        succes = "You successfully have set your team FX to ";
    }

    // /teams changeFX effect
    public void doCommand(CommandSender sender, String[] args) throws MessageException{
        if (!(sender instanceof Player)){
            throw new MessageException(playerError);
        }
        if (args.length != 2 || !effects.contains(args[1].toLowerCase())){
            throw new MessageException(effectsError);
        }
        Player player = (Player) sender;
        String team = tHandler.getTeam(player.getUniqueId());
        if (team == null){
            throw new MessageException(notInTeamError);
        }

        tHandler.setTeamFX(team, args[1]);
        sendSucces(sender, succes + ChatColor.valueOf(args[1].toUpperCase()) + args[1]);
        for (Player onlineplayer : Bukkit.getOnlinePlayers()){
            if (tHandler.getTeamMembers(team).contains(onlineplayer.getName())){
                new ChatPrefix(plugin).nameChange(onlineplayer);
            }
        }
    }
}
