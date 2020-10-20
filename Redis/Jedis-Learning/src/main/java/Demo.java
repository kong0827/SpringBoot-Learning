/**
 * @author kxj
 * @date 2020/7/26 18:36
 * @desc 生产者消费者问题
 */
class Person {
    private String name;
    private String sex;
    private Boolean isimpty = Boolean.TRUE;//内存区为空！

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void set(String name, String sex) {
        synchronized (this) {
            while (!isimpty.equals(Boolean.TRUE)) {//不为空的话等待消费者消费！
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.name = name;//为空的话生产者创造！
            this.sex = sex;
            isimpty = Boolean.FALSE;//创造结束后修改属性！
            this.notifyAll();
        }
    }

    public void get() {
        synchronized (this) {
            while (!isimpty.equals(Boolean.FALSE)) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(" 姓 名 " + getName() + ", " + " 性 别 " + getSex());
            isimpty = Boolean.TRUE;
            this.notifyAll();
        }
    }
}

class Producer implements Runnable {
    private Person p;

    public Producer(Person p) {
        super();
        this.p = p;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                p.set("刘昭", "男");
            } else {
                p.set("章泽天", "女");
            }
        }
    }
}

class Consumer implements Runnable {
    private Person p;

    public Consumer(Person p) {
        super();
        this.p = p;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            p.get();
        }
    }
}

public class Demo {
    public static void main(String[] args) {
        Person p = new Person();
        new Thread(new Producer(p)).start();
        new Thread(new Consumer(p)).start();
    }
}
