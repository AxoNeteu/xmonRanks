package cf.xmon.rank.events;

import cf.xmon.rank.Plugin;
import cf.xmon.rank.manager.RankManager;
import cf.xmon.rank.object.Rank;
import cf.xmon.rank.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Random;

public class PlayerDeadEvent implements Listener {

    @EventHandler
    public void onPlayerDead(PlayerDeathEvent e){
        Player p = e.getEntity();
        Rank rankUser = RankManager.createRankUser(p.getUniqueId());
        if (rankUser != null){
            rankUser.setDeaths((rankUser.getDeaths() + 1));
            if (p.getKiller() != null){
                Player killer = p.getKiller();
                Rank killerRankUser = RankManager.createRankUser(killer.getUniqueId());
                if (killerRankUser != null){
                    killerRankUser.setKills((killerRankUser.getKills() + 1));
                    int victim$rank = rankUser.getRank();
                    int attacker$rank = killerRankUser.getRank();
                    int attacker$set = (int) (64.0D + (double) (attacker$rank - victim$rank) * -0.505D);
                    if (attacker$set <= 0) {
                        attacker$set = 0;
                    }
                    rankUser.setRank(victim$rank - (attacker$set / 4 * 3));
                    killerRankUser.setRank(attacker$rank + attacker$set);
                    if (rankUser.isKiller()){
                        rankUser.setKiller(false);
                        p.setDisplayName(p.getName());
                        Plugin.getEconomy().depositPlayer(killer, Plugin.getInstance().getConfig().getInt("kwadratowa.money"));
                        ChatUtils.sendMsg(killer, "&aZostało dodane Ci do konta " + Plugin.getInstance().getConfig().getInt("kwadratowa.money") + "$ za zabójstwo gracza z Czaszką!");
                        killer.sendTitle(ChatUtils.fixColor("&a☠"), "", 10, 30, 10);
                        Bukkit.broadcastMessage(ChatUtils.fixColor("&aGracz " + killer.getName() + " zabił " + p.getName() + " z ☠!"));
                    }else {
                        killerRankUser.setKiller(true);
                        killer.setDisplayName(killer.getName() + " ☠");
                        Plugin.getEconomy().depositPlayer(killer, -Plugin.getInstance().getConfig().getInt("kwadratowa.money"));
                        ChatUtils.sendMsg(killer, "&4Zostało Ci potrącone z konta " + Plugin.getInstance().getConfig().getInt("kwadratowa.money") + "$ za zabójstwo gracza!");
                        killer.sendTitle(ChatUtils.fixColor("&8☠"), "", 10, 30, 10);
                        Bukkit.broadcastMessage(ChatUtils.fixColor("&aGracz " + killer.getName() + " zabił " + p.getName() + ", otrzymał status zabójcy, na jego/jej konto zostaje przypisana ☠!"));
                    }
                    e.setDeathMessage(ChatUtils.fixColor("&7>> &3Gracz &6" + p.getName() + "&8(&3-" + attacker$set + "&8)&3 został zabity przez &6" + killer.getName() + "&8(&3+" + attacker$set + "&8)"));
                }
            }
        }
    }
}
