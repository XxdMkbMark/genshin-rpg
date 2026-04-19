import java.util.Random;

public class Monster {
    int id;
    String name;
    int HP;
    int maxHP;
    int level;
    int normalAttackDamageMin;
    int normalAttackDamageMax;
    int defence;
    Random rand = new Random();

    public Monster(int id, String name, int HP, int level, int normalAttackDamageMin, int normalAttackDamageMax) {
        this.id = id;
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.level = level;
        this.normalAttackDamageMin = normalAttackDamageMin;
        this.normalAttackDamageMax = normalAttackDamageMax;
        this.defence = level+100;
    }

    public Monster(Monster template) {
        this.id = template.id;
        this.name = template.name;
        this.HP = template.maxHP;
        this.maxHP = template.maxHP;
        this.level = template.level;
        this.normalAttackDamageMin = template.normalAttackDamageMin;
        this.normalAttackDamageMax = template.normalAttackDamageMax;
        this.defence = template.defence;
    }

    public void damage(int amount) {
        this.HP -= amount;
    }

    public int getAttackDamage() {
        // 健壮性检查：如果最小大于最大，自动交换它们（防止填错数据）
        int min = Math.min(normalAttackDamageMin, normalAttackDamageMax);
        int max = Math.max(normalAttackDamageMin, normalAttackDamageMax);

        // 如果相等，直接返回，不再调用 nextInt 防止报错
        if (min == max) {
            return min;
        }

        // nextInt(min, max + 1) 确保能随机到最大值本身
        return rand.nextInt(min, max + 1);
    }
}
