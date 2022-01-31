package me.damascus2000.minecraftsurvivalteamsplugin.Commands;

import com.google.common.collect.Lists;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class TabCompletion implements TabCompleter {

    private final Main plugin;

    public TabCompletion(Main plugin){
        this.plugin = plugin;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        List<String> s1 = Arrays.asList("help", "create", "join", "leave", "info", "kick", "remove", "rename", "changecolor", "list", "changeFX", "lock");
        List<String> teams = plugin.getTeamsHandler().getTeams();
        List<String> colors = Arrays.asList("Aqua", "Green", "Red", "Black", "Blue", "Gray", "Gold", "Yellow", "Dark_Aqua", "Dark_Blue", "Dark_gray", "Dark_Green", "Dark_Purple", "Light_Purple");
        List<String> effects = Arrays.asList("Magic", "Bold", "Italic", "Strikethrough", "Underline");
        List<String> Flist = Lists.newArrayList();
        List<String> players = Lists.newArrayList();
        for (Player player : Bukkit.getOnlinePlayers()){
            players.add(player.getName());
        }
        if (args.length == 1){
            for (String s : s1){
                if (s.startsWith(args[0])){
                    Flist.add(s);
                }
            }
            return Flist;
        }
        if (args.length == 2){
            if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("remove")){
                for (String s : teams){
                    if (s.startsWith(args[1])){
                        Flist.add(s);
                    }
                }
                return Flist;
            } else if (args[0].equalsIgnoreCase("changecolor")){
                for (String s : colors){
                    if (s.startsWith(args[1])){
                        Flist.add(s);
                    }
                }
                return Flist;
            } else if (args[0].equalsIgnoreCase("kick")){
                for (String s : players){
                    if (s.startsWith(args[1])){
                        Flist.add(s);
                    }
                }
                return Flist;
            } else if (args[0].equalsIgnoreCase("changeFX")){
                for (String s : effects){
                    if (s.startsWith(args[1])){
                        Flist.add(s);
                    }
                }
                return Flist;
            }
        }
        if (args.length == 3)
            if (args[0].equalsIgnoreCase("create")){
                for (String s : colors){
                    if (s.toLowerCase().startsWith(args[2].toLowerCase())){
                        Flist.add(s);
                    }
                }
                return Flist;
            }

        return null;
    }
}
