/**
 * @author kxj
 * @date 2021/7/26 01:08
 * @desc 从结果可以看出多个线程在访问同一变量的时候出现异常，线程间的数据没有隔离，接着采用ThreadLocal的方式来解决这个问题
 * {@link ThreadLocal_Demo_02}
 */
public class ThreadLocal_Demo_01 {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadLocal_Demo_01 demo_01 = new ThreadLocal_Demo_01();
//        for (int i = 0; i < 5; i++) {
//            Thread thread = new Thread(() -> {
//                demo_01.setContent(Thread.currentThread().getName() + "的数据");
//                System.out.println("---------");
//                System.out.println(Thread.currentThread().getName() + "---->" + demo_01.getContent());
//            });
//            thread.setName("线程" + i);
//            thread.start();
//        }

        /**
         * 使用synchronized解决线程并发问题
         */
//        Thread.sleep(1000);
        System.out.println("-----synchronized-------");
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                synchronized (ThreadLocal_Demo_01.class) {
                    demo_01.setContent(Thread.currentThread().getName() + "的数据");
                    System.out.println("---------");
                    System.out.println(Thread.currentThread().getName() + "---->" + demo_01.getContent());
                }
            });
            thread.setName("线程" + i);
            thread.start();
        }
    }
}