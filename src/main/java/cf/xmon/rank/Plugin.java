package cf.xmon.rank;

import cf.xmon.rank.command.RankCommand;
import cf.xmon.rank.events.PlayerDeadEvent;
import cf.xmon.rank.events.PlayerJoin;
import cf.xmon.rank.manager.RankManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Plugin extends JavaPlugin {
    private static Plugin instance;
    private static Economy econ = null;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        if (!setupEconomy() ) {
            System.err.println("Nie znaleziono pluginu vault!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        instance = this;
        if (new File(Plugin.getInstance().getDataFolder().getAbsolutePath() + "/data.db").exists()){
            try {
                RankManager.load();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        Bukkit.getPluginManager().registerEvents(new PlayerDeadEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        this.getCommand("gracz").setExecutor(new RankCommand());
        this.getServer().getScheduler().runTaskTimer(this, () ->{
            System.out.println("Zapisuje ranking...");
            try {
                RankManager.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Zapisano Ranking" + RankManager.size());
        }, 72000, 72000);
    }

    @Override
    public void onDisable() {
        try {
            RankManager.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Plugin getInstance() {
        return instance;
    }
    public static Economy getEconomy() {
        return econ;
    }
    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
