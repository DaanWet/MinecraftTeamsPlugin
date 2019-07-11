package me.damascus2000.minecraftsurvivalteamsplugin.Sign;

import me.damascus2000.minecraftsurvivalteamsplugin.EventHandlers.ChatPrefix;
import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Signs implements Listener {

    private TeamsYmlHandler tHandler;
    private Main plugin;

    public Signs(Main plugin){
        tHandler = plugin.getTeamsHandler();
        this.plugin = plugin;
    }

    public void makeJoinBlocks(Block blunder, Block blright, Block blrightunder, String playername, String side){
        String teamname = tHandler.getTeam(playername);
        String teamcolor = tHandler.getTeamColor(teamname);
        blunder.setType(Material.WALL_SIGN);
        blright.setType(Material.WALL_SIGN);
        blrightunder.setType(Material.WALL_SIGN);
        Sign sunder = (Sign) blunder.getState();
        Sign sright = (Sign) blright.getState();
        Sign srightunder = (Sign) blrightunder.getState();
        org.bukkit.material.Sign matSign =  new org.bukkit.material.Sign(Material.WALL_SIGN);
        matSign.setFacingDirection(BlockFace.valueOf(side));
        sunder.setData(matSign);
        sright.setData(matSign);
        srightunder.setData(matSign);
        sright.setLine(0, ChatColor.AQUA + "Member 1:");
        sright.setLine(2, ChatColor.valueOf(teamcolor) + playername );
        sunder.setLine(0,ChatColor.GREEN + "Join");
        sunder.setLine(2, ChatColor.valueOf(teamcolor) + teamname);
        srightunder.setLine(0, ChatColor.GREEN + "Join");
        srightunder.setLine(2,ChatColor.valueOf(teamcolor) + teamname);
        sright.update();
        srightunder.update();
        sunder.update();
    }
    public void afterConversation(PlayerInteractEvent e){
        Sign sign = (Sign) e.getClickedBlock().getState();
        String temp = e.getPlayer().getName();
        //String world = e.getClickedBlock().getWorld().getName();
        String team = tHandler.getTeam(temp);
        sign.setLine(0, ChatColor.valueOf(tHandler.getTeamColor(team)) + team);
        sign.setLine(1, temp);
        sign.setLine(2, ChatColor.AQUA + "Join");
        sign.update(true);
        new ChatPrefix(plugin).nameChange(e.getPlayer());
        /*int xco = (int) e.getClickedBlock().getLocation().getX();
        int yco = (int) e.getClickedBlock().getLocation().getY();
        int zco = (int) e.getClickedBlock().getLocation().getZ();
        org.bukkit.material.Sign signdata = (org.bukkit.material.Sign) sign.getData();
        String facing = signdata.getFacing().name();
        if (facing.equalsIgnoreCase("EAST")){
            Block blunder = Bukkit.getServer().getWorld(world).getBlockAt(xco, (yco--), zco);
            Block blright = Bukkit.getServer().getWorld(world).getBlockAt(xco, yco, (zco--));
            Block blrightunder = Bukkit.getServer().getWorld(world).getBlockAt(xco, (yco--),(zco--));
            //if (blunder.getType() != AIR && blright.getType() != AIR && blrightunder.getType() != AIR) {
            sign.setLine(0, ChatColor.AQUA + "Team");
            sign.update();
            e.getPlayer().sendMessage(facing);
            //sign.setLine(2, ChatColor.valueOf(Main.getTeamColor(Main.getTeam(temp))) + Main.getTeam(temp));
            //makeJoinBlocks(blunder,blright,blrightunder, e.getPlayer().getDisplayName(), "EAST");
            //} else e.getPlayer().sendMessage(ChatColor.DARK_RED + "You need to have free blocks, right, under & rightunder your sign");
        }else if (facing.equalsIgnoreCase("north")) {
            e.getPlayer().sendMessage("nope");
        }else if (facing.equalsIgnoreCase("south")){
            e.getPlayer().sendMessage("nope");
        }else if (facing.equalsIgnoreCase("west")){
            e.getPlayer().sendMessage("nope");
        } else e.getPlayer().sendMessage("You have to place the sign on the side of a block");
        //Bukkit.getServer().getWorld(world).getBlockAt();*/
    }
    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        if ((e.getLine(0)).equalsIgnoreCase("[Create Team]")){
            e.setLine(0, ChatColor.AQUA + "Create Team");
            e.setLine(2, "Click me!");
        } if ((e.getLine(0)).equalsIgnoreCase("[Leave Team]")){
            e.setLine(0, ChatColor.RED + "Leave Team");
            e.setLine(2, "Click me!");
        }
        int i = 0;
        while (i < 4) {
            if ((e.getLine(i).contains("NOT OK"))) {
                e.setLine(i, ChatColor.RED + e.getLine(i));
            } else if ((e.getLine(i)).contains("OK")){
                e.setLine(i, ChatColor.GREEN + e.getLine(i));
            }
            i++;
        }

    }

    @EventHandler
    public void onSignClick(PlayerInteractEvent e) throws InterruptedException {
        if ((e.getAction() == Action.RIGHT_CLICK_BLOCK)){
           if (e.getClickedBlock().getState() instanceof Sign) {
                Sign sign = (Sign) e.getClickedBlock().getState();
                if (sign.getLine(0).equalsIgnoreCase(ChatColor.AQUA + "Create Team")){
                    if (tHandler.getTeam(e.getPlayer().getName()) == null) {
                        ConversationFactory cf = new ConversationFactory(plugin).withFirstPrompt(new TeamNamePrompt(e.getPlayer().getName(), plugin)).withLocalEcho(false).addConversationAbandonedListener(new ConversationAbondon(e, plugin));
                        Conversation conv = cf.buildConversation(e.getPlayer());
                        conv.begin();
                    } else {
                        e.getPlayer().sendMessage(ChatColor.DARK_RED + "You are already part of a team");
                    }
                } else if (sign.getLine(2).equalsIgnoreCase(ChatColor.AQUA + "Join")){
                    if (tHandler.getTeam(e.getPlayer().getName()) == null) {
                        tHandler.joinTeam(sign.getLine(0).substring(2), e.getPlayer().getName());
                        sign.setLine(2, e.getPlayer().getName());
                        sign.setLine(3, ChatColor.AQUA + "Join");
                        sign.update();
                        new ChatPrefix(plugin).nameChange(e.getPlayer());
                    } else {
                        e.getPlayer().sendMessage(ChatColor.DARK_RED + "You are already part of a team");
                    }
                } else if (sign.getLine(3).equalsIgnoreCase(ChatColor.AQUA + "Join")){
                    if (tHandler.getTeam(e.getPlayer().getName()) == null) {
                        tHandler.joinTeam(sign.getLine(0).substring(2), e.getPlayer().getName());
                        sign.setLine(3, e.getPlayer().getName());
                        sign.update();
                        new ChatPrefix(plugin).nameChange(e.getPlayer());
                    } else {
                        e.getPlayer().sendMessage(ChatColor.DARK_RED + "You are already part of a team");
                    }
                } else if (sign.getLine(0).equals(ChatColor.RED + "Leave Team")){
                    tHandler.exitTeam(e.getPlayer().getName());
                }
            }
        }
    }



}
