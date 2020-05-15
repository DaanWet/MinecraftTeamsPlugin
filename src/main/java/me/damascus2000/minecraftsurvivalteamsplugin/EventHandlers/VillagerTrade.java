package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import net.minecraft.server.v1_15_R1.Entity;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.NBTTagList;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftVillager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.*;

import java.util.List;
import java.util.UUID;

public class VillagerTrade implements Listener {

    private TeamsYmlHandler handler;

    public VillagerTrade(Main plugin){
        this.handler = plugin.getTeamsHandler();
    }

    @EventHandler
    public void onTrade(InventoryOpenEvent e) {
        if (e.getInventory() instanceof MerchantInventory) {
            String team = handler.getTeam(e.getPlayer().getName());
            if (team != null){
                MerchantInventory inv = (MerchantInventory) e.getInventory();
                Villager v = (Villager) inv.getHolder();
                Entity villager = ((CraftVillager) inv.getHolder()).getHandle();
                NBTTagCompound tag = villager.save(new NBTTagCompound());
                NBTTagList gossips = (NBTTagList) tag.get("Gossips");
                String uuid = v.getUniqueId().toString();
                int savedValue = handler.getVillager(team, uuid);
                NBTTagCompound majorPositive = getMajorPositiveV(gossips);
                if (majorPositive != null){
                    int currentValue = majorPositive.getInt("Value");
                    if (currentValue > savedValue) {
                        handler.setVillager(team, uuid, currentValue);
                    } else if (savedValue != -1){
                        majorPositive.setInt("Value", savedValue);
                    }
                } else if (savedValue != -1){
                    majorPositive = new NBTTagCompound();
                    majorPositive.setString("Type", "major_positive");
                    majorPositive.setInt("Value", savedValue);
                    UUID id = e.getPlayer().getUniqueId();
                    majorPositive.setLong("TargetMost", id.getMostSignificantBits());
                    majorPositive.setLong("TargetLeast", id.getLeastSignificantBits());
                    gossips.add(majorPositive);
                }
                villager.f(tag);


            }
        }
    }


    public NBTTagCompound getMajorPositiveV(NBTTagList gossips) {
        NBTTagCompound value = null;
        for (int i = 0; i < gossips.size(); i++) {
             NBTTagCompound gossip =  gossips.getCompound(i);
             if (gossip.getString("Type").equals("major_positive")){
                value = gossip;
             }

        }
        return value;
    }

}
