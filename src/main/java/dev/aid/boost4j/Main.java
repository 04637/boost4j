package dev.aid.boost4j;

/**
 * todo
 *
 * @author 04637@163.com
 * @date 2020/11/3
 */
public class Main {
    public static void main(String[] args) {
        long sum = 0;
        int N = 100000000;
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < N; i++)
            System.currentTimeMillis();
        long t2 = System.currentTimeMillis();
        System.out.println("Sum = " + sum + "; time = " + (t2 - t1) +
                "; or " + (t2 - t1) * 1.0E6 / N + " ns / iter");
    }
}
