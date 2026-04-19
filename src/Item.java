public class Item {
    int id;
    String name;
    String effect;
    int regenerateHP;
    int addAttack;

    public Item(int id, String name, String effect, int regenerateHP, int addAttack) {
        this.id = id;
        this.name = name;
        this.effect = effect;
        this.regenerateHP = regenerateHP;
        this.addAttack = addAttack;
    }
}
