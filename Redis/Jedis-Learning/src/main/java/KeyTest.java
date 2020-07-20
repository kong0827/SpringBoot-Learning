import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @author kxj
 * @date 2020/7/20 23:44
 * @desc
 */
public class KeyTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("39.107.57.111", 6379);
        jedis.auth("root");
        System.out.println("清空数据：" + jedis.flushDB());
        System.out.println("判断某个键是否存在：" + jedis.exists("username"));
        System.out.println("新增键值对：" + jedis.set("username", "kongxiangjin"));
        Set<String> keys = jedis.keys("*");
        System.out.println("系统中所有的键：");
        System.out.println(keys);
        System.out.println("删除键password：" + jedis.del("password"));
        System.out.println("判断键password是否存在：" + jedis.exists("password"));
        System.out.println("查看键username存储的类型" + jedis.type("username"));
        System.out.println("随机返回key空间的一个:" + jedis.randomKey());
        System.out.println("重命名key："+ jedis.rename("username", "name"));
        System.out.println("取出改名的key："+jedis.get("name"));
        System.out.println("按索引查询："+jedis.select(0));
        System.out.println("删除当前选择数据库中的所有key："+jedis.flushDB());
        System.out.println("返回当前数据库中key的数目：" + jedis.dbSize());
        System.out.println("删除所有数据库的key:" + jedis.flushAll());
    }
}
