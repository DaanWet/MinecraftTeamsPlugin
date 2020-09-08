package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import net.minecraft.server.v1_16_R2.Entity;
import net.minecraft.server.v1_16_R2.NBTTagCompound;
import net.minecraft.server.v1_16_R2.NBTTagList;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftVillager;
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
                        int[] uuid = gossip.getIntArray("Target");
                        long most = (long)uuid[0] << 32 | uuid[1] & 0xFFFFFFFFL;
                        long least = (long)uuid[2] << 32 | uuid[3] & 0xFFFFFFFFL;
                        majorPositiveS.put(new UUID(most, least), gossip.getInt("Value"));
                    }
                }
                int value = 0;
                UUID playerUUID = e.getPlayer().getUniqueId();
                for (UUID uid : majorPositiveS.keySet()) {
                    if (handler.getTeam(plugin.getServer().getOfflinePlayer(uid).getName()).equals(team)) {
                        value = Math.max(value, majorPositiveS.get(uid));
                    }
                }
                if (majorPositiveS.containsKey(playerUUID)) {
                    for (int i = 0; i < gossips.size(); i++) {
                        NBTTagCompound gossip = gossips.getCompound(i);
                        int[] uuid = gossip.getIntArray("Target");
                        long most = (long)uuid[0] << 32 | uuid[1] & 0xFFFFFFFFL;
                        long least = (long)uuid[2] << 32 | uuid[3] & 0xFFFFFFFFL;
                        if (gossip.getString("Type").equals("major_positive") && new UUID(most, least).equals(playerUUID)) {
                            gossip.setInt("Value", value);
                        }
                    }
                } else {
                    NBTTagCompound majorPositive = new NBTTagCompound();
                    majorPositive.setString("Type", "major_positive");
                    majorPositive.setInt("Value", value);
                    int[] array = new int[4];
                    array[0] = (int) (playerUUID.getMostSignificantBits() >> 32);
                    array[1] = (int) (playerUUID.getMostSignificantBits());
                    array[2] = (int) (playerUUID.getLeastSignificantBits() >> 32);
                    array[3] = (int) (playerUUID.getLeastSignificantBits());
                    majorPositive.setIntArray("Target", array);
                    gossips.add(majorPositive);
                }
                villager.load(tag);

            }
        }
    }
}
