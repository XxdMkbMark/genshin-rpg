import java.util.HashMap;

public class AllItemType {
    public static void defineItems(HashMap<String, Item> itemDex) {
        itemDex.clear();
        itemDex.put("史莱姆凝胶", new Item(1, "史莱姆凝胶", "NONE", 0, 0));
        itemDex.put("树莓", new Item(2, "树莓", "NONE", 0, 0));
        itemDex.put("枫达", new Item(3, "枫达", "NONE", 0, 0));
        itemDex.put("摩拉", new Item(4, "摩拉", "MONEY", 0, 0));
        itemDex.put("苹果", new Item(5, "苹果", "HEAL", 100, 0));
        itemDex.put("杏仁豆腐", new Item(6, "杏仁豆腐", "ATK", 0, 60));
        itemDex.put("甜甜花酿鸡", new Item(7, "甜甜花酿鸡", "HEAL", 100, 0));
        itemDex.put("大英雄的经验", new Item(8, "大英雄的经验", "UNUSABLE", 0 ,0));
    }
}