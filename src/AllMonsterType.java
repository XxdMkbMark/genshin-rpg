import java.util.HashMap;

public class AllMonsterType {
    public static void defineMonsters(HashMap<String, Monster> monsterDex) {
        monsterDex.clear();
        monsterDex.put("丘丘人", new Monster(1, "丘丘人", 300, 5, 100, 125));
        monsterDex.put("水史莱姆", new Monster(2, "水史莱姆", 300, 3, 100, 125));
    }
}
