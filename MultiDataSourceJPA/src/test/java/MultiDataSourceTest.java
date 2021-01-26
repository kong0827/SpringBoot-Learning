import cn.hutool.core.lang.Snowflake;
import com.kxj.MultiDataSourceJpaApplication;
import com.kxj.entity.primary.PrimaryMultiTable;
import com.kxj.entity.second.SecondMultiTable;
import com.kxj.repository.primary.PrimaryMultiTableRepository;
import com.kxj.repository.second.SecondMultiTableRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiangjin.kong
 * @date 2021/1/26 14:48
 */
@SpringBootTest(classes = MultiDataSourceJpaApplication.class)
@RunWith(SpringRunner.class)
public class MultiDataSourceTest {

    @Autowired
    private PrimaryMultiTableRepository primaryRepo;
    @Autowired
    private SecondMultiTableRepository secondRepo;
    @Autowired
    private Snowflake snowflake;

    @Test
    public void insertTest() {
        PrimaryMultiTable primaryMultiTable = new PrimaryMultiTable(snowflake.nextId(), "测试名称-1");
        primaryRepo.save(primaryMultiTable);

        SecondMultiTable secondMultiTable = new SecondMultiTable(snowflake.nextId(), "测试名称-2");
        secondRepo.save(secondMultiTable);
    }

}
