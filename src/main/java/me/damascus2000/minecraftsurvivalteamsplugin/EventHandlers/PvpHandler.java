package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PvpHandler implements Listener {

    private final TeamsYmlHandler tHandler;

    public PvpHandler(TeamsYmlHandler tHandler){
        this.tHandler = tHandler;
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player){
            Player victim = (Player) e.getEntity();
            if (e.getDamager() instanceof Player){
                Player damager = (Player) e.getDamager();
                if (tHandler.getTeam(victim.getUniqueId()) != null && tHandler.getTeam(damager.getUniqueId()) != null && tHandler.getTeam(victim.getUniqueId()).equalsIgnoreCase(tHandler.getTeam(damager.getUniqueId()))){
                    e.setCancelled(true);
                }
            }
        }
    }
}
