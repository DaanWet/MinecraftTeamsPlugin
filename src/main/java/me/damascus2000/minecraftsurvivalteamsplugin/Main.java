package me.damascus2000.minecraftsurvivalteamsplugin;


import me.damascus2000.minecraftsurvivalteamsplugin.Commands.*;
import me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers.*;
import me.damascus2000.minecraftsurvivalteamsplugin.Sign.Signs;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.PlayerYmlHandler;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin {

    private PlayerYmlHandler playerYmlHandler;
    private TeamsYmlHandler teamsYmlHandler;
    private AFKHandler afkHandler;


    @Override
    public void onEnable(){

        // Plugin startup logic
        //getCommand("tpbow").setExecutor(new TeleportBowCommands(this));
        playerYmlHandler = new PlayerYmlHandler(this);
        teamsYmlHandler = new TeamsYmlHandler(this);
        afkHandler = new AFKHandler(this);
        for (Player p : Bukkit.getOnlinePlayers()){
            afkHandler.addPlayer(p);
        }
        getCommand("teams").setExecutor(new TeamCommand(this));
        getCommand("teams").setTabCompleter(new TabCompletion(this));
        getCommand("teamchat").setExecutor(new TeamChatCommand(this));
        getCommand("travel").setExecutor(new TeleportCommand(this));
        getCommand("claimPerms").setExecutor(new ClaimPerm(this));
        getCommand("cure").setExecutor(new CureCommand());
        PluginManager plm = getServer().getPluginManager();
        plm.registerEvents(new Signs(this), this);
        plm.registerEvents(new PvpHandler(teamsYmlHandler), this);
        plm.registerEvents(new Chat(this), this);
        plm.registerEvents(new InventoryClickHandler(this), this);
        plm.registerEvents(afkHandler, this);
        plm.registerEvents(new Sleep(this), this);
        plm.registerEvents(new TeamsScoreboard(this), this);
        plm.registerEvents(new VillagerTrade(this), this);
        //getServer().getPluginManager().registerEvents(new TeleportBowEvent(this), this);
    }

    @Override
    public void onDisable(){
        //Plugin shutdown logic
        for (Player player : getServer().getOnlinePlayers()){
            afkHandler.removePlayer(player);
        }
        playerYmlHandler.saveYml();
        teamsYmlHandler.saveYml();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        return false;
    }

    public PlayerYmlHandler getPlayerHandler(){
        return playerYmlHandler;
    }

    public TeamsYmlHandler getTeamsHandler(){
        return teamsYmlHandler;
    }

    public AFKHandler getAfkHandler(){return afkHandler;}
}