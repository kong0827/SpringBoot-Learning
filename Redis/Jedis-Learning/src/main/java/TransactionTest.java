import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @author kxj
 * @date 2020/7/21 0:13
 * @desc 事务的测试
 */
public class TransactionTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("39.107.57.111", 6379);
        jedis.auth("root");
        jedis.flushDB();



        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello", "world");
        jsonObject.put("name", "kongxiangjin");
        String jsonString = jsonObject.toJSONString();

        Transaction transaction = jedis.multi(); // 开启事务
        try {
            transaction.set("user1", jsonString);
            int i = 1 / 0; // 抛出异常
            transaction.set("user2", jsonString);

            transaction.exec(); //执行
        } catch (Exception e) {
            transaction.discard(); // 放弃事务
            e.printStackTrace();
        } finally {
            System.out.println("user1："+jedis.get("user1"));
            System.out.println("user2："+jedis.get("user2"));
            jedis.close();
        }


    }
}
