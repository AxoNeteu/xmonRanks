package cf.xmon.rank.events;

import cf.xmon.rank.manager.RankManager;
import cf.xmon.rank.object.Rank;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Rank rankUser = RankManager.createRankUser(e.getPlayer().getUniqueId());
        if (rankUser.isKiller()){
            e.getPlayer().setDisplayName(e.getPlayer().getName() + " ☠");
        }
    }
}
