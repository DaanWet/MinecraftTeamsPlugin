package me.damascus2000.minecraftsurvivalteamsplugin.gui;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MenuData {

    private final Player owner;
    private Location warp;
    private int cost;
    private String team;


    public MenuData(Player p){
        this.owner = p;
    }

    public Player getOwner(){
        return owner;
    }

    public Location getWarp(){
        return warp;
    }

    public void setWarp(Location warp){
        this.warp = warp;
    }

    public int getCost(){
        return cost;
    }

    public void setCost(int cost){
        this.cost = cost;
    }

    public String getTeam(){
        return team;
    }

    public void setTeam(String team){
        this.team = team;
    }
}
