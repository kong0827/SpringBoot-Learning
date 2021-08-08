/**
 * @author kxj
 * @date 2021/8/9 01:17
 * @desc
 */
public class ThreadLocal_Demo_03 {

    public static void main(String[] args) {
        final ThreadLocal<Integer> MAIN = ThreadLocal.withInitial(() -> 100);

        MAIN.set(200);

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + " MAIN:" + MAIN.get());
        }).start();

        System.out.println("MAIN:" + MAIN.get());

        //一定要在不再使用ThreadLocal时调用remove方法，避免内存泄漏
        MAIN.remove();

        System.out.println("MAIN:" + MAIN.get());

    }
}
