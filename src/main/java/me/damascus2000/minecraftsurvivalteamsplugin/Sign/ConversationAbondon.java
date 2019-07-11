package me.damascus2000.minecraftsurvivalteamsplugin.Sign;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ConversationAbondon implements ConversationAbandonedListener {

    private PlayerInteractEvent e;
    private Main plugin;
    public ConversationAbondon(PlayerInteractEvent event, Main plugin){
        e = event;
        this.plugin = plugin;
    }
    @Override
    public void conversationAbandoned(ConversationAbandonedEvent event) {
        if (event.gracefulExit()){
            new Signs(plugin).afterConversation(e);
        }
    }
}
