package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import net.minecraft.server.v1_15_R1.Entity;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.NBTTagList;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftVillager;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.*;

import java.util.HashMap;
import java.util.UUID;

public class VillagerTrade implements Listener {


    private Main plugin;
    private TeamsYmlHandler handler;

    public VillagerTrade(Main plugin){
        this.handler = plugin.getTeamsHandler();
        this.plugin = plugin;
    }

    @EventHandler
    public void onTrade(InventoryOpenEvent e) {
        if (e.getInventory() instanceof MerchantInventory) {
            String team = handler.getTeam(e.getPlayer().getName());
            if (team != null) {
                MerchantInventory inv = (MerchantInventory) e.getInventory();
                Villager v = (Villager) inv.getHolder();
                Entity villager = ((CraftVillager) inv.getHolder()).getHandle();
                NBTTagCompound tag = villager.save(new NBTTagCompound());
                NBTTagList gossips = (NBTTagList) tag.get("Gossips");
                HashMap<UUID, Integer> majorPositiveS = new HashMap<>();
                for (int i = 0; i < gossips.size(); i++) {
                    NBTTagCompound gossip = gossips.getCompound(i);
                    if (gossip.getString("Type").equals("major_positive")) {
                        majorPositiveS.put(new UUID(gossip.getLong("TargetMost"), gossip.getLong("TargetLeast")), gossip.getInt("Value"));
                    }
                }
                int value = 0;
                UUID playerUUID = e.getPlayer().getUniqueId();
                for (UUID uid : majorPositiveS.keySet()) {
                    if (!uid.equals(playerUUID) &&
                            handler.getTeam(plugin.getServer().getOfflinePlayer(uid).getName()).equals(team)) {
                        value = Math.max(value, majorPositiveS.get(uid));
                    }
                }
                if (majorPositiveS.containsKey(playerUUID)) {
                    for (int i = 0; i < gossips.size(); i++) {
                        NBTTagCompound gossip = gossips.getCompound(i);
                        if (gossip.getString("Type").equals("major_positive") && gossip.getLong("TargetMost") == playerUUID.getMostSignificantBits()
                                && gossip.getLong("TargetLeast") == playerUUID.getLeastSignificantBits()) {
                            gossip.setInt("Value", value);
                        }
                    }
                } else {
                    NBTTagCompound majorPositive = new NBTTagCompound();
                    majorPositive.setString("Type", "major_positive");
                    majorPositive.setInt("Value", value);
                    UUID id = e.getPlayer().getUniqueId();
                    majorPositive.setLong("TargetMost", id.getMostSignificantBits());
                    majorPositive.setLong("TargetLeast", id.getLeastSignificantBits());
                    gossips.add(majorPositive);
                }
                villager.f(tag);

            }
        }
    }
}
