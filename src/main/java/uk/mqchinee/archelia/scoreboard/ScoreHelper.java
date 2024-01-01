package uk.mqchinee.archelia.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ScoreHelper {

    private static final HashMap<UUID, ScoreHelper> players = new HashMap<>();

    public static boolean hasScore(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    public static ScoreHelper createScore(Player player) {
        return new ScoreHelper(player);
    }

    public static ScoreHelper getByPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public static ScoreHelper removeScore(Player player) {
        return players.remove(player.getUniqueId());
    }

    private final Scoreboard scoreboard;
    private final Objective sidebar;
    private final Player player;

    private ScoreHelper(Player player) {
        this.player = player;

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        sidebar = scoreboard.registerNewObjective("sidebar", "dummy");
        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);

        for(int i=1; i<=15; i++) {
            Team team = scoreboard.registerNewTeam("SLOT_" + i);
            team.addEntry(genEntry(i));
        }
        player.setScoreboard(scoreboard);
        players.put(player.getUniqueId(), this);
    }

    public void setTitle(String title) {
        title = ChatColor.translateAlternateColorCodes('&', title);
        sidebar.setDisplayName(title.length()>32 ? title.substring(0, 32) : title);
    }

    public void setSlot(int slot, String text) {
        Team team = scoreboard.getTeam("SLOT_" + slot);
        String entry = genEntry(slot);
        if(!scoreboard.getEntries().contains(entry)) {
            sidebar.getScore(entry).setScore(slot);
        }

        text = ChatColor.translateAlternateColorCodes('&', text);
        String pre = getFirstSplit(text);
        String suf = getFirstSplit(ChatColor.getLastColors(pre) + getSecondSplit(text));
        team.setPrefix(pre);
        team.setSuffix(suf);
    }

    public void setTabPrefix(String prefix) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ScoreHelper score = null;
            if (hasScore(player))
                score = getByPlayer(player);
            else
                score = createScore(player);
            score.setTabPrefixInternal(this.player, prefix);
        }
    }

    private void setTabPrefixInternal(Player player, String prefix) {
        Team team = scoreboard.getTeam(player.getName());
        if (team == null) {
            team = scoreboard.registerNewTeam(player.getName());
        }

        team.setPrefix(prefix);

        team.addPlayer((OfflinePlayer) player);
    }

    public void removeTabPrefix() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ScoreHelper score = null;
            if (hasScore(player)) {
                score = getByPlayer(player);
                score.removeTabPrefixInternal(this.player);
            }
        }
    }

    private void removeTabPrefixInternal(Player player) {
        Team team = scoreboard.getTeam(player.getName());
        if (team == null)
            return;

        team.setPrefix("");

        if (team.getSuffix().equals("")) {
            team.removePlayer((OfflinePlayer) player);
            team.unregister();
        }
    }

    public void setTabSuffix(String suffix) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ScoreHelper score = null;
            if (hasScore(player))
                score = getByPlayer(player);
            else
                score = createScore(player);
            score.setTabSuffixInternal(this.player, suffix);
        }
    }

    private void setTabSuffixInternal(Player player, String suffix) {
        Team team = scoreboard.getTeam(player.getName());
        if (team == null) {
            team = scoreboard.registerNewTeam(player.getName());
        }

        team.setSuffix(suffix);

        team.addPlayer((OfflinePlayer) player);
    }

    public void removeTabSuffix() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ScoreHelper score = null;
            if (hasScore(player)) {
                score = getByPlayer(player);
                score.removeTabSuffixInternal(this.player);
            }
        }
    }

    private void removeTabSuffixInternal(Player player) {
        Team team = scoreboard.getTeam(player.getName());
        if (team == null)
            return;

        team.setSuffix("");
        if (team.getPrefix().equals("")) {
            team.removePlayer((OfflinePlayer) player);
            team.unregister();
        }
    }

    public void removeSlot(int slot) {
        String entry = genEntry(slot);
        if(scoreboard.getEntries().contains(entry)) {
            scoreboard.resetScores(entry);
        }
    }

    public void setSlotsFromList(List<String> list) {
        while(list.size()>15) {
            list.remove(list.size()-1);
        }

        int slot = list.size();

        if(slot<15) {
            for(int i=(slot +1); i<=15; i++) {
                removeSlot(i);
            }
        }

        for(String line : list) {
            setSlot(slot, line);
            slot--;
        }
    }

    private String genEntry(int slot) {
        return ChatColor.values()[slot].toString();
    }

    private String getFirstSplit(String s) {
        return s.length()>16 ? s.substring(0, 16) : s;
    }

    private String getSecondSplit(String s) {
        if(s.length()>32) {
            s = s.substring(0, 32);
        }
        return s.length()>16 ? s.substring(16) : "";
    }

}