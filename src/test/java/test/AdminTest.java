package test;

import com.baizhi.cmfz.AppRun;
import com.baizhi.cmfz.dao.AdminDao;
import com.baizhi.cmfz.entity.Admin;
import com.baizhi.cmfz.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRun.class)
public class AdminTest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminDao adminDao;

    @Test
    public void getOneTest() {
        System.out.println(adminService.login(new Admin(-1, "xiaohei@qq.com", "123456")));
        System.out.println(adminService.login(new Admin(-1, "123@qq.com", "1234567")));
    }


    @Test
    public void getAllByLimitTest() {
        /*List<Admin> admin = adminDao.getAllByLimit(0, 999, new Admin(0, "a", null));

        System.out.println("admin = " + admin);

        for (Admin admin1 : admin) {
            System.out.println("admin1 = " + admin1);
        }*/

        Map a = adminService.getAllByLimit(1, 999, new Admin(0, "a", null));

        System.out.println(a.get("rows"));
    }
}
