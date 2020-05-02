package cf.xmon.rank.command;

import cf.xmon.rank.manager.RankManager;
import cf.xmon.rank.object.Rank;
import cf.xmon.rank.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            return ChatUtils.sendMsg(sender, "Only Players!");
        }
        Player p = (Player) sender;
        if (args.length == 0){
            Rank rankUser = RankManager.createRankUser(p.getUniqueId());
            if (rankUser != null){
                return ChatUtils.sendMsg(p, "&7>> &8[ &6" + p.getName() + "&8 ] &7<<\n&7>> &8Ranking: &3" + rankUser.getRank() + "\n&7>> &8Zabójstwa: &3" + rankUser.getKills() + "\n&7>> &8Śmierci: &3" + rankUser.getDeaths() + "\n&7>> &8[ &6" + p.getName() + "&8 ] &7<<");
            }else{
                return ChatUtils.sendMsg(p, "&4&lWejdz ponownie na serwer!");
            }
        }else if (args.length == 1){
            Player arg = Bukkit.getPlayer(args[0]);
            if (arg != null){
                Rank rankUser = RankManager.createRankUser(arg.getUniqueId());
                if (rankUser != null){
                    return ChatUtils.sendMsg(p, "&7>> &8[ &6" + arg.getName() + "&8 ] &7<<\n&7>> &8Ranking: &3" + rankUser.getRank() + "\n&7>> &8Zabójstwa: &3" + rankUser.getKills() + "\n&7>> &8Śmierci: &3" + rankUser.getDeaths() + "\n&7>> &8[ &6" + arg.getName() + "&8 ] &7<<");
                }else{
                    return ChatUtils.sendMsg(p, "&3Błąd: Taki użytkownik nie istnieje!");
                }
            }else{
                return ChatUtils.sendMsg(p, "&3Błąd: Taki użytkownik nie istnieje!");
            }
        }else{
            return ChatUtils.sendMsg(p, "&3Poprawne użycie: &cranking [gracz]");
        }
    }
}
