import java.util.*;

public class Main {
    static String[] availableItems = {"史莱姆凝胶", "树莓", "枫达", "摩拉", "苹果", "杏仁豆腐", "甜甜花酿鸡"};
    static String[] possibleMonsters = {"丘丘人","水史莱姆"};
    static String[] atkMenuItems = {"普攻", "重击", "取消"};
    static String[] moveMenuItems = {"攻击", "物品", "角色属性", "逃跑"};

    static HashMap<String, Monster> monsterDex = new HashMap<>();
    static HashMap<String, Item> itemDex = new HashMap<>();
    static HashMap<String, Integer> backpack = new HashMap<>();

    static Random rand = new Random();
    static Player myself = new Player();

    public record returnMonster(String monsterName, int monsterHP) {};

    // Write util methods under this line
    public static void goGetSomeSleep(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // Ends

    // Write 世界交互相关 methods under this line
    public static void pickUpItems() {
        String item1 = availableItems[rand.nextInt(availableItems.length)];
        String item2 = availableItems[rand.nextInt(availableItems.length)];
        System.out.println("获得物品: "+item1+", "+item2);
        if (backpack.containsKey(item1)) {
            backpack.put(item1, backpack.get(item1)+1);
        }
        else {
            backpack.put(item1, 1);
        }
        if (backpack.containsKey(item2)) {
            backpack.put(item2, backpack.get(item2)+1);
        }
        else {
            backpack.put(item2, 1);
        }
    }
    public static void consumeItem(String chosenItem) {
        Item currentItem = itemDex.get(chosenItem);
        if (currentItem.effect.equals("HEAL")) {
            myself.heal(currentItem.regenerateHP);
            System.out.println("你使用了 "+currentItem.name);
            System.out.println("你恢复了 "+currentItem.regenerateHP+"点 体力");
            System.out.println("当前血量: "+myself.HP);
            backpack.replace(chosenItem, backpack.get(chosenItem)-1);
            if (backpack.get(chosenItem) <= 0) {
                backpack.remove(chosenItem);
            }
        } else if (currentItem.effect.equals("ATK")) {
            myself.baseDamage += currentItem.addAttack;
            System.out.println("你使用了 "+currentItem.name);
            System.out.println("你增加了 "+currentItem.addAttack+"点 攻击力");
            backpack.replace(chosenItem, backpack.get(chosenItem)-1);
            if (backpack.get(chosenItem) <= 0) {
                backpack.remove(chosenItem);
            }
        }
        if (currentItem.effect.equals("NONE")) {
            System.out.println("你使用了 "+currentItem.name+",但似乎并没什么用");
            backpack.replace(chosenItem, backpack.get(chosenItem)-1);
            if (backpack.get(chosenItem) <= 0) {
                backpack.remove(chosenItem);
            }
        }
    }
    public static String encounterMonsters(String usr) {
        String monster =  possibleMonsters[rand.nextInt(possibleMonsters.length)];
        goGetSomeSleep(rand.nextInt(100));
        System.out.println();
        System.out.println(usr+" 遇到了 "+monster);
        return monster;
    }
    // Ends

    // 在此行以下封装 重复性打印 相关的方法
    public static void printMoveMenu() {
        System.out.println("------ 行动菜单 ------");
        for (int menuItemIndex = 0; menuItemIndex < moveMenuItems.length; menuItemIndex++) {
            System.out.println((menuItemIndex+1)+". "+moveMenuItems[menuItemIndex]);
        }
        System.out.println("---------------------");
        System.out.print("选择行动: ");
    }
    public static void printAtkMenu() {
        System.out.println("------ 攻击方式 ------");
        for (int menuItemIndex = 0; menuItemIndex < atkMenuItems.length; menuItemIndex++) {
            System.out.println((menuItemIndex+1)+". "+atkMenuItems[menuItemIndex]);
        }
        System.out.println("---------------------");
        System.out.print("选择攻击方式: ");
    }
    public static ArrayList<String> printBackpackMenu() {
        System.out.println("-------- 背包 --------");
        System.out.println("0. 返回");
        int itemIndex = 1;
        ArrayList<String> currentItemsInBackpack = new ArrayList<>();
        for (Map.Entry<String, Integer> currentlyProcessingItem : backpack.entrySet()) {
            currentItemsInBackpack.add(currentlyProcessingItem.getKey());
            System.out.println(itemIndex + ". " + currentlyProcessingItem.getKey() + " x" + currentlyProcessingItem.getValue());
            itemIndex++;
        }
        System.out.println("---------------------");
        System.out.print("选择物品序号: ");
        return currentItemsInBackpack;
    }
    // Ends

    // 伤害计算方法↓
    public static int calculateTotalDamage(int baseDamage, int attackerLvl, int targetLvl) {
        double defMultiplier = (attackerLvl + 100.0) / ((attackerLvl + 100.0) + (targetLvl + 100.0));
        return (int) (baseDamage * defMultiplier);
    }
    public static double getDefMultiplier(int attackerLvl, int targetDef) {
        return (attackerLvl + 100.0) / ((attackerLvl + 100.0) + targetDef);
    }
    // Ends

    // 初始化の方法↓
    // 可以说奇怪的话吗
    // 请加入存档功能吧!!!
    // 追加功能??
    // 存档??
    // 怎么想都不可能的吧?????
    public static void init(String usr) {
        myself.username = usr;
        myself.HP = 5000;
        myself.maxHP = 5000;
        myself.level = 1;
        myself.baseDamage = 150;
        myself.criticalDamage = (int) (myself.baseDamage*1.5);
        myself.defence = 100;
        myself.energy = 100;
        AllMonsterType.defineMonsters(monsterDex);
        AllItemType.defineItems(itemDex);
    }

    // 游戏逻辑
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        returnMonster monsters = null;
        String items, usr = null;
        int choice = 0;

        System.out.print("登录米哈游通行证\n用户名: ");
        usr = sc.nextLine();
        System.out.println("欢迎, "+usr+"!");
        init(usr);
        goGetSomeSleep(1000);

        while (myself.HP > 0) {
            boolean isEscaped = false;
            Monster template = monsterDex.get(encounterMonsters(usr));
            Monster currentMonster = new Monster(template);
            System.out.println(currentMonster.name + " 当前血量: " + currentMonster.HP);
            System.out.println(usr + " 当前血量: " + myself.HP);
            while (currentMonster.HP > 0 && myself.HP > 0) {
                while (true) {
                    printMoveMenu();
                    choice = sc.nextInt();

                    if (choice == 1) {
                        printAtkMenu();
                        choice = sc.nextInt();
                        if (choice == 1) {
                            System.out.println(usr + " 使用了 " + atkMenuItems[choice - 1]);
                            double defenseMultiplier = getDefMultiplier(myself.level, currentMonster.defence);
                            int finalDamage = (int) (myself.baseDamage*defenseMultiplier);
                            currentMonster.damage(finalDamage);
                            System.out.println(usr + " 对 " + currentMonster.name + " 造成了 "+finalDamage+"点 伤害");
                            System.out.println(currentMonster.name + " 当前血量: " + currentMonster.HP);
                            break;
                        }
                        if (choice == 2) {
                            if (myself.energy <=0) {
                                System.out.println("体力不足! 无法使用 重击!");
                                continue;
                            }
                            System.out.println(usr + " 使用了 " + atkMenuItems[choice - 1]);
                            currentMonster.damage(myself.criticalDamage);
                            myself.energy -= 25;
                            System.out.println("消耗 25点 体力");
                            System.out.println("剩余体力: "+myself.energy);
                            System.out.println(usr + " 对 " + currentMonster.name + " 造成了 "+myself.criticalDamage+"点 伤害");
                            System.out.println(currentMonster.name + " 当前血量: " + currentMonster.HP);
                            break;
                        }
                        else {
                            continue;
                        }
                    }
                    else if (choice == 2) {
                        if (backpack.isEmpty()) {
                            System.out.println("背包空空如也! 打怪来获取更多物品吧!");
                            continue;
                        }
                        else {
                            ArrayList<String> currentItemsInBackpack = printBackpackMenu();;
                            int itemIndex = 1;
                            itemIndex = sc.nextInt();
                            if (itemIndex == 0) {
                                continue;
                            }
                            else if (itemIndex > backpack.size() || itemIndex < 0) {
                                System.out.println("没有这个物品! 请重新选择");
                                continue;
                            }
                            String chosenItem = currentItemsInBackpack.get(itemIndex-1);
                            consumeItem(chosenItem);
                            break;
                        }
                    } else if (choice == 3) {
                        myself.showStatus();
                    } else if (choice == 4) {
                        System.out.println(usr + " 逃跑了" + "\n");
                        int damageWouldBeMaking = currentMonster.getAttackDamage();
                        myself.damage(damageWouldBeMaking);
                        System.out.println(usr+" 在逃跑过程中受到了 "+damageWouldBeMaking+"点 伤害");
                        System.out.println("剩余血量: " + myself.HP);
                        currentMonster.HP = 0;
                        isEscaped = true;
                        break;
                    }
                }
                myself.energy += 10;
                System.out.println("恢复了 10点 体力");

                if (currentMonster.HP > 0) {
                    System.out.println();
                    System.out.println(currentMonster.name + " 使用了 普攻");
                    double defenseMultiplier = getDefMultiplier(currentMonster.level, myself.defence);
                    int finalDamage = (int) (currentMonster.getAttackDamage()*defenseMultiplier);
                    myself.damage(finalDamage);
                    System.out.println(currentMonster.name + " 对 " + usr + " 造成了 " + finalDamage + "点 伤害");
                    System.out.println(usr + " 当前血量: " + myself.HP);
                }
            }

            if (!isEscaped) {
                System.out.println(usr + " 打败了 " + currentMonster.name + "\n");
                pickUpItems();
            }
        }
        System.out.println(usr+" 被打败了");
        sc.close();
    }
}