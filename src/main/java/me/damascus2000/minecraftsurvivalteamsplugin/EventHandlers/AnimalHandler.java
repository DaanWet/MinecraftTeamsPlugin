package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class AnimalHandler implements Listener {

    private TeamsYmlHandler tHandler;

    public AnimalHandler(Main plugin){
        tHandler = plugin.getTeamsHandler();
    };


    @EventHandler
    public void onClick(PlayerInteractEntityEvent e){
        if (e.getRightClicked() instanceof Tameable){
            Tameable pet = (Tameable) e.getRightClicked();
            if (pet.isTamed() && pet.getOwner().getUniqueId() != e.getPlayer().getUniqueId()){
                String team = tHandler.getTeam(e.getPlayer().getUniqueId());
                if (team != null && tHandler.getTeamMembers(team).contains(pet.getOwner().getUniqueId())){
                    pet.setOwner(e.getPlayer());
                }
            }
        }
    }
}
