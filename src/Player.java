public class Player {
    String username;
    int HP;
    int maxHP;
    int energy;
    int level;
    int baseDamage;
    int criticalDamage;
    int defence;

    public void heal(int amount) {
        this.HP += amount;
    }

    public void damage(int amount) {
        this.HP -= amount;
    }

    public void showStatus() {
        System.out.println("------ 角色属性 ------");
        System.out.println("旅行者: " + username);
        System.out.println("等级: " + level);
        System.out.println("生命值: " + HP + "/" + maxHP);
        System.out.println("攻击力: " + baseDamage);
        System.out.println("防御力: " + defence);
        System.out.println("体力: " + energy);
        System.out.println("---------------------");
    }
}
