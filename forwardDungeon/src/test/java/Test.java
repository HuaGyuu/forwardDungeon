import java.util.ArrayList;
import java.util.Random;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-25
 * @Version: 1.0
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        double num = 10.0;
        int n = 1;
        while (true) {
            System.out.println(n + " " + num);
            num += n++;
            Thread.sleep(50);
        }

    }
}
