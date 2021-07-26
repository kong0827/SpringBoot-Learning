/**
 * @author kxj
 * @date 2021/7/26 01:18
 * @desc 采用ThreadLocal
 */
public class ThreadLocal_Demo_02 {
    private static ThreadLocal<String> tl = new ThreadLocal<>();

    public String getContent() {
        return tl.get();
    }

    public void setContent(String content) {
        tl.set(content);
    }

    public static void main(String[] args) {
        ThreadLocal_Demo_02 demo_02 = new ThreadLocal_Demo_02();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                demo_02.setContent(Thread.currentThread().getName() + "的数据");
                System.out.println("---------");
                System.out.println(Thread.currentThread().getName() + "---->" + demo_02.getContent());
            });
            thread.setName("线程" + i);
            thread.start();
        }
    }


}
