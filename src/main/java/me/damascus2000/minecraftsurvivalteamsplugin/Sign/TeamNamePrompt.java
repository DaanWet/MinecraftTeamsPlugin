package me.damascus2000.minecraftsurvivalteamsplugin.Sign;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

import java.util.UUID;

public class TeamNamePrompt extends ValidatingPrompt {

    private final UUID playername;
    private final TeamsYmlHandler tHandler;
    private final Main plugin;

    public TeamNamePrompt(UUID player, Main plugin){
        playername = player;
        tHandler = plugin.getTeamsHandler();
        this.plugin = plugin;
    }

    @Override
    public String getPromptText(ConversationContext context){
        return "Please enter the name for your team";
    }


    @Override
    protected boolean isInputValid(ConversationContext context, String input){
        if (tHandler.checkTeam(input)){
            context.getForWhom().sendRawMessage(ChatColor.DARK_RED + "That team already exists");
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String input){
        context.getForWhom().sendRawMessage("Teamname: " + input);
        context.setSessionData("Name", input);
        return new TeamColorPrompt(playername, plugin);
    }
}
