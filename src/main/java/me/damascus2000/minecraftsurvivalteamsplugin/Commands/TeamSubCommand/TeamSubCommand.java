package me.damascus2000.minecraftsurvivalteamsplugin.Commands.TeamSubCommand;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import me.damascus2000.minecraftsurvivalteamsplugin.utils.MessageException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public abstract class TeamSubCommand {

    protected final String inTeamError = "You are already part of a team";
    protected final String colorError = "You have to pick a (valid) color";
    protected final List<String> colors = Arrays.asList("aqua", "green", "red", "black", "blue", "gray", "gold", "yellow", "dark_aqua", "dark_blue", "dark_gray", "dark_green", "dark_purple", "light_purple", "magic");
    protected final String playerError = "You have to be a player to perform this command";
    protected final String notLegalError = "That is not a legal command, go /teams help for more info";
    protected final String notInTeamError = "You currently aren't part of a team";
    protected final String teamNameError = "You have to give a valid (existing) teamname";
    protected final String existingNameError = "You have to pick a (valid), non existing name";
    protected String succes;
    protected TeamsYmlHandler tHandler;
    protected Main plugin;


    public TeamSubCommand(Main plugin){
        tHandler = plugin.getTeamsHandler();
        this.plugin = plugin;
    }


    public abstract void doCommand(CommandSender sender, String[] args) throws MessageException;

    public void sendSucces(CommandSender sender, String message){
        sender.sendMessage(ChatColor.GREEN + message);
    }

    public void sendMessage(CommandSender sender, String message){
        sender.sendMessage(ChatColor.AQUA + message);
    }
}
