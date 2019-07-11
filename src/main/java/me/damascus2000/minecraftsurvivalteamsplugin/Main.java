package me.damascus2000.minecraftsurvivalteamsplugin;

import com.google.common.collect.Lists;
import me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers.Chat;
import me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers.ChatPrefix;
import me.damascus2000.minecraftsurvivalteamsplugin.Commands.*;
import me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers.InventoryClickHandler;
import me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers.PvpHandler;
import me.damascus2000.minecraftsurvivalteamsplugin.Sign.Signs;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.PlayerYmlHandler;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.YmlHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public final class Main extends JavaPlugin {

    private static Plugin plugin;
    private PlayerYmlHandler playerYmlHandler;
    private TeamsYmlHandler teamsYmlHandler;
    private Scoreboard scoreboard;


    @Override
    public void onEnable() {
        // Plugin startup logic
        //getCommand("tpbow").setExecutor(new TeleportBowCommands(this));
        playerYmlHandler = new PlayerYmlHandler(this);
        teamsYmlHandler = new TeamsYmlHandler(this);
        getCommand("teams").setExecutor(new TeamCommand(this));
        getCommand("teams").setTabCompleter(new TabCompletion(this));
        getCommand("teamchat").setExecutor(new TeamChatCommand(this));
        getCommand("travel").setExecutor(new TeleportCommand(this));
        getServer().getPluginManager().registerEvents(new Signs(this), this);
        getServer().getPluginManager().registerEvents(new ChatPrefix(this), this);
        getServer().getPluginManager().registerEvents(new PvpHandler(teamsYmlHandler), this);
        getServer().getPluginManager().registerEvents(new Chat(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickHandler(this), this);
        //getServer().getPluginManager().registerEvents(new TeleportBowEvent(this), this);
        plugin = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        playerYmlHandler.saveYml();
        teamsYmlHandler.saveYml();
    }

    public PlayerYmlHandler getplayerHandler(){
        return playerYmlHandler;
    }

    public TeamsYmlHandler getTeamsHandler(){
        return teamsYmlHandler;
    }
}