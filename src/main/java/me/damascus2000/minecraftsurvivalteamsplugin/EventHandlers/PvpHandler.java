package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class PvpHandler implements Listener {

    private TeamsYmlHandler tHandler;

    public PvpHandler(TeamsYmlHandler tHandler){
        this.tHandler = tHandler;
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player){
            Player victim = (Player) e.getEntity();
            if (e.getDamager() instanceof Player){
                Player damager = (Player) e.getDamager();
                if(tHandler.getTeam(victim.getName()) != null && tHandler.getTeam(damager.getName()) != null && tHandler.getTeam(victim.getName()).equalsIgnoreCase(tHandler.getTeam(damager.getName()))) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
