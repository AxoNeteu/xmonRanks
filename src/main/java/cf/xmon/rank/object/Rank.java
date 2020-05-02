package cf.xmon.rank.object;

import java.io.Serializable;
import java.util.UUID;

public class Rank implements Serializable {
    private UUID uuid;
    private int rank;
    private int kills;
    private int deaths;
    private boolean killer;

    public Rank (UUID uuid){
        this.uuid = uuid;
        this.rank = 1000;
        this.kills = 0;
        this.deaths = 0;
        this.killer = false;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public boolean isKiller() {
        return killer;
    }

    public int getKills() {
        return kills;
    }

    public int getRank() {
        return rank;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setKiller(boolean killer) {
        this.killer = killer;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
