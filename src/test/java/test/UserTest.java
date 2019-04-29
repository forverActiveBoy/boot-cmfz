package test;

import com.baizhi.cmfz.AppRun;
import com.baizhi.cmfz.dao.UserDao;
import com.baizhi.cmfz.entity.Sex;
import com.baizhi.cmfz.entity.User;
import com.baizhi.cmfz.service.UserService;
import com.baizhi.cmfz.util.PlaceUtil;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRun.class)
public class UserTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    @Test
    public void getAllTest() {
        System.out.println(userService.showUser(1, 100));
    }

    @Test
    public void insertThousandTest() {
        System.out.println("----------导入开始----------");
        for (int i = 0;i < 2000; i++) {
            User user = PlaceUtil.getUser();

            userService.insertNew(user);
        }
        System.out.println("----------导入结束----------");
    }

    @Test
    public void getNewCreate() {
        for (User user : userDao.getNewCreate(7)) {
            System.out.println("user = " + user);
        }
    }

    @Test
    public void getCountBySexTest() {
        for (Sex sex : userDao.getCountBySex()) {
            System.out.println("sex = " + sex);
        }
    }

    // 服务器端开发
    @Test
    public void sendMsgTest() {
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-16d86c4f7ad74e19810feb541ac47189");

        /**
         * channel 通道 频道的名字
         * content 要发布的内容
         */
        goEasy.publish("cmfzChannel", "Hello, GoEasy!");
    }


}
