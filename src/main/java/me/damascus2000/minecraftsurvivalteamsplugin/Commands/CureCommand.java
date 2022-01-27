package me.damascus2000.minecraftsurvivalteamsplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CureCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){
        /*if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            if (player.isOp()){
                int x = Integer.parseInt(strings[0]);
                int y = Integer.parseInt(strings[1]);
                int z = Integer.parseInt(strings[2]);
                Location l = new Location(Bukkit.getWorld("world"), x, y, z);
                Collection<Entity> entities = Bukkit.getWorld("world").getNearbyEntities(l, 1.0, 1.0, 1.0);
                for (Entity entity : entities){
                    if (entity instanceof Villager){
                        net.minecraft.server.v1_16_R2.Entity e = ((CraftVillager) entity).getHandle();
                        NBTTagCompound tag = e.save(new NBTTagCompound());
                        NBTTagList gossips = (NBTTagList) tag.get("Gossips");
                        UUID playerUUID = player.getUniqueId();
                        boolean contains = false;
                        for (int i = 0; i < gossips.size(); i++) {
                            NBTTagCompound gossip = gossips.getCompound(i);
                            int[] uuid = gossip.getIntArray("Target");
                            long most = (long)uuid[0] << 32 | uuid[1] & 0xFFFFFFFFL;
                            long least = (long)uuid[2] << 32 | uuid[3] & 0xFFFFFFFFL;
                            if (gossip.getString("Type").equals("major_positive") && new UUID(most, least).equals(playerUUID)) {
                                gossip.setInt("Value",  20);
                                contains = true;
                            }
                        }
                        if (!contains){
                            NBTTagCompound majorPositive = new NBTTagCompound();
                            majorPositive.setString("Type", "major_positive");
                            majorPositive.setInt("Value", 20);
                            int[] array = new int[4];
                            array[0] = (int) (playerUUID.getMostSignificantBits() >> 32);
                            array[1] = (int) (playerUUID.getMostSignificantBits());
                            array[2] = (int) (playerUUID.getLeastSignificantBits() >> 32);
                            array[3] = (int) (playerUUID.getLeastSignificantBits());
                            majorPositive.setIntArray("Target", array);
                            gossips.add(majorPositive);
                        }
                        e.load(tag);
                    }

                }
            }
        }
*/
        return false;
    }
}
