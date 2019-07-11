package me.damascus2000.minecraftsurvivalteamsplugin.Sign;

import me.damascus2000.minecraftsurvivalteamsplugin.Main;
import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

import java.util.Arrays;
import java.util.List;

public class TeamColorPrompt extends ValidatingPrompt {
    private String player;
    private TeamsYmlHandler tHandler;
    public TeamColorPrompt(String playername, Main plugin){
        player  = playername;
        tHandler = plugin.getTeamsHandler();
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return "Please enter the color of your team";
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        List<String> colors = Arrays.asList("aqua", "green", "red", "black", "blue", "gray", "gold", "purple", "yellow", "dark_aqua", "dark_blue", "dark_gray", "dark_green", "dark_purple", "light_Purple");
        if (colors.contains(input.toLowerCase())){
            return true;
        } else {
            context.getForWhom().sendRawMessage(ChatColor.DARK_RED + "The color must be one of the following:");
            context.getForWhom().sendRawMessage(ChatColor.DARK_RED + "" + colors);
            return false;
        }
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String input) {
        context.setSessionData("Color", input);
        String[] list = {player};
        tHandler.createTeam(context.getSessionData("Name").toString(), context.getSessionData("Color").toString(), Arrays.asList(list));
        context.getForWhom().sendRawMessage("Team created");
        return END_OF_CONVERSATION;
    }
}

