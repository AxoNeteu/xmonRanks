package cf.xmon.rank.manager;

import cf.xmon.rank.Plugin;
import cf.xmon.rank.object.Rank;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RankManager {
    private static Map<UUID, Rank> RankMap = new HashMap<UUID, Rank>();

    public static Rank createRankUser(UUID uuid){
        Rank RankUser = RankMap.get(uuid);
        if (RankUser == null){
            RankMap.put(uuid, RankUser = new Rank(uuid));
        }
        return RankUser;
    }

    public static void removeRankUser(UUID uuid){
        final Rank RankUser = RankMap.get(uuid);
        RankMap.remove(uuid);
    }

    public static void save() throws IOException {
        FileOutputStream fos = new FileOutputStream(Plugin.getInstance().getDataFolder().getAbsolutePath() + "/data.db");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(RankMap);
        oos.close();
        fos.close();
    }

    public static void load() throws IOException, ClassNotFoundException {
        if (new File(Plugin.getInstance().getDataFolder().getAbsolutePath() + "/data.db").exists()) {
            FileInputStream fis = new FileInputStream(Plugin.getInstance().getDataFolder().getAbsolutePath() + "/data.db");
            ObjectInputStream ois = new ObjectInputStream(fis);
            RankMap = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        }
    }

    public static int size(){
        return RankMap.size();
    }
}
