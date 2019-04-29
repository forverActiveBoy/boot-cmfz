package test;

import com.baizhi.cmfz.AppRun;
import com.baizhi.cmfz.dao.MenuDao;
import com.baizhi.cmfz.entity.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRun.class)
public class MenuTest {
    @Autowired
    private MenuDao menuDao;

    @Test
    public void getAllMenuTest() {
        for (Menu allMenu : menuDao.getAll()) {
            System.out.println("allMenu = " + allMenu);
        }
    }
}
