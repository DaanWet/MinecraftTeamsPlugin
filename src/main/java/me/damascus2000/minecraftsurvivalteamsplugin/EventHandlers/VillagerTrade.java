package me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers;


import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import net.minecraft.world.entity.ai.gossip.GossipContainer;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.npc.Villager;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.MerchantInventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class VillagerTrade implements Listener {


    private final Main plugin;
    private final TeamsYmlHandler handler;
    private final HashMap<UUID, Integer> values;

    public VillagerTrade(Main plugin){
        this.handler = plugin.getTeamsHandler();
        this.plugin = plugin;
        values = new HashMap<>();
    }

    @EventHandler
    public void onTrade(PlayerInteractEntityEvent e){
        if (e.getRightClicked() instanceof CraftVillager){
            UUID player = e.getPlayer().getUniqueId();
            String team = handler.getTeam(player);
            if (team != null){
                Villager villager = ((CraftVillager) e.getRightClicked()).getHandle();
                GossipContainer g = villager.getGossips();
                Map<UUID, Object2IntMap<GossipType>> gossip = g.getGossipEntries();
                int newvalue = 0;
                for (UUID member : handler.getTeamMembers(team)){
                    if (!member.equals(player) && gossip.getOrDefault(member, new Object2IntOpenHashMap<>()).containsKey(GossipType.MAJOR_POSITIVE)){
                        newvalue += gossip.get(member).getInt(GossipType.MAJOR_POSITIVE);
                    }
                }
                values.put(player, newvalue);
                g.add(player, GossipType.MAJOR_POSITIVE, newvalue);
            }
        }
    }

    @EventHandler
    public void closeTrade(InventoryCloseEvent e){
        UUID player = e.getPlayer().getUniqueId();
        if (e.getInventory() instanceof MerchantInventory && values.containsKey(player)){
            Villager villager = ((CraftVillager) e.getInventory().getHolder()).getHandle();
            GossipContainer g = villager.getGossips();
            Map<UUID, Object2IntMap<GossipType>> gossip = g.getGossipEntries();
            g.add(player, GossipType.MAJOR_POSITIVE, -1 * values.get(player));
        }
    }
}
