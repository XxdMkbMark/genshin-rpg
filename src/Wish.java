import java.util.Scanner;
import java.util.Random;

public class Wish {
    private static final double BASE_RATE_5 = 0.006;
    private static final double BASE_RATE_4 = 0.051;

    private static int pity5 = 0;
    private static int pity4 = 0;
    private static boolean isGuaranteed = false;

    private static Random rand = new Random();
    private static Scanner sc = new Scanner(System.in);

    public static int draw() {
        pity4++;
        pity5++;

        double currentRate5 = BASE_RATE_5;

        if (pity5 >=74 || pity5 <90) {
            currentRate5 = BASE_RATE_5 + (pity5 - 73) * 0.06;
        } else if (pity5 >= 90) {
            currentRate5 = 1.0;
        }

        double roll = rand.nextDouble();
        if (roll < currentRate5) {
            handleWin5();
            return 5;
        }
        if (pity4 >= 10 || roll < (currentRate5 + BASE_RATE_4)) {
            pity4 = 0;
            return 4;
        }
        return 3;
    }

    private static void handleWin5() {
        System.out.print("✨【金光闪烁】✨ ");

        if (isGuaranteed) {
            System.out.println("成功触发大保底，获得了当期限定角色！");
            isGuaranteed = false;
        } else {
            if (rand.nextBoolean()) {
                System.out.println("运气真好，50% 概率抽中了限定角色！");
            } else {
                System.out.println("哎呀，歪到了常驻角色...下次必定是限定！");
                isGuaranteed = true;
            }
        }

        pity5 = 0;
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. 祈愿1次 2.祈愿10次");
            int choice = sc.nextInt();
            if (choice == 1) {
                int result = draw();
                System.out.println(result);
            } else if (choice == 2) {
                for (int i = 1; i <= 10; i++) {
                    System.out.println(draw());
                }
            }
        }
    }
}
