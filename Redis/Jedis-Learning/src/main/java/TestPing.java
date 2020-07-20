import redis.clients.jedis.Jedis;

/**
 * @author kxj
 * @date 2020/7/20 23:30
 * @desc
 */
public class TestPing {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("39.107.57.111", 6379);
        jedis.auth("root");
        System.out.println(jedis.ping());
    }
}
