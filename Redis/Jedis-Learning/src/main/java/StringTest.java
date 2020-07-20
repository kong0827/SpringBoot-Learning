import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author kxj
 * @date 2020/7/20 23:53
 * @desc
 */
public class StringTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("39.107.57.111", 6379);
        jedis.auth("root");
        System.out.println("清空数据：" + jedis.flushDB());
        jedis.set("k1", "v1");
        jedis.set("k2", "v2");
        jedis.set("k3", "v3");
        System.out.println("删除键k2:" + jedis.del("k2"));
        System.out.println("获取键k2:" + jedis.get("k2"));
        System.out.println("修改k1:" + jedis.set("k1", "new k1"));
        System.out.println("获取键k1" + jedis.get("k1"));
        System.out.println("在k3后加入值：" + jedis.append("k3", " end"));
        System.out.println("获取键k3" + jedis.get("k3"));
        System.out.println("增加多个键值对：" + jedis.mset("k4", "v4", "k5", "v5"));
        System.out.println("获取多个键值对：" + jedis.mget("k4", "k5"));
        System.out.println("删除多个键值对：" + jedis.del("k4", "k5"));
        System.out.println("获取多个键值对：" + jedis.mget("k4", "k5"));

        jedis.flushDB();
        System.out.println("----------------新增键值对防止覆盖原先值 分布式锁------------");
        System.out.println(jedis.setnx("k1", "v1"));
        System.out.println(jedis.setnx("k2", "v2"));
        System.out.println(jedis.setnx("k2", "new v2"));
        System.out.println("k1的值:"+jedis.get("k1"));
        System.out.println("k2的值:"+jedis.get("k2"));

        System.out.println("----------新增键值对并设置有效时间--------------");
        System.out.println(jedis.setex("k3", 2, "v3"));
        System.out.println(jedis.get("k3"));

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程暂停三秒后获取k3的值："+jedis.get("k3"));

        System.out.println("-----获取原值，更新为新值------");
        System.out.println(jedis.getSet("k2", "k2GetSet"));
        System.out.println("k2的值:"+jedis.get("k2"));

        System.out.println("获取k2的字符串：" + jedis.getrange("k2", 2, 4));
    }
}
