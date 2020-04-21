package me.damascus2000.minecraftsurvivalteamsplugin;

import me.damascus2000.minecraftsurvivalteamsplugin.YmlHandlers.TeamsYmlHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.*;

import static org.bukkit.scoreboard.Criterias.DEATHS;

public class TeamsScoreboard implements Listener {

    private TeamsYmlHandler tHandler;
    private Scoreboard board;
    private Main plugin;

    public TeamsScoreboard(Main plugin){
        this.plugin = plugin;
        board = plugin.getServer().getScoreboardManager().getMainScoreboard();
        Objective objective = board.getObjective("TeamDeaths");
        if (objective == null){
            objective = board.registerNewObjective("TeamDeaths", "dummy", "Team Deaths");
        }
        tHandler = plugin.getTeamsHandler();

        for (String teamname : tHandler.getTeams()){
            Score score = objective.getScore(teamname);
            int deaths = 0;
            for (String player : tHandler.getTeamMembers(teamname)){
                Objective obj = board.getObjective("ts_Deaths");;
                if (obj != null){
                    deaths += obj.getScore(player).getScore();
                }

            }
            score.setScore(deaths);
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        String team = tHandler.getTeam(e.getEntity().getName());
        if (team != null){
            Objective objective = board.getObjective("TeamDeaths");
            Score score = objective.getScore(team);
            score.setScore(score.getScore() + 1);
        }
    }

}
