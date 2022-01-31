package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.utils.MessageException;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChestLockCommand extends TeamSubCommand {


    public ChestLockCommand(Main plugin){
        super(plugin);
    }

    @Override
    public void doCommand(CommandSender sender, String[] args) throws MessageException{
        if (!(sender instanceof Player player)){
            throw new MessageException(playerError);
        }
        if (args.length > 2 || (args.length == 2 && !args[1].equalsIgnoreCase("false"))){
            throw new MessageException(notLegalError);
        }
        String team = tHandler.getTeam(player.getUniqueId());
        if (team == null){
            throw new MessageException(notInTeamError);
        }
        Block block = player.getTargetBlockExact(5);
        if (block != null && (block.getType() == Material.CHEST ||block.getType() == Material.TRAPPED_CHEST)){
            Location loc = block.getLocation();
            if (args.length == 2){
                tHandler.removeLocked(loc, team);
                sendSucces(sender, "Chest successfully unlocked");
            } else {
                tHandler.setLocked(loc, team);
                sendSucces(sender, "Chest successfully locked");
            }
        }

    }
}
