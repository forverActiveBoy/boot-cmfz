package test;

import com.baizhi.cmfz.AppRun;
import com.baizhi.cmfz.entity.Banner;
import com.baizhi.cmfz.service.BannerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRun.class)
public class BannerTest {
    @Autowired
    private BannerService bannerService;

    @Test
    public void getAllBannerTest() {
        System.out.println(bannerService.getAllBanner(2, 2));
    }

    @Test
    public void insertBannerTest() {
        bannerService.insertNew(new Banner(0, "test", "test", 1, new Date(), "test"));
    }

    @Test
    public void DeleteBannerTest() {
        int[] ids = {-1, 7};
        System.out.println(bannerService.multiDelete(ids));
    }
}
