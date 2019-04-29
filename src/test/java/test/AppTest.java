package test;

import com.baizhi.cmfz.AppRun;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRun.class)
public class AppTest {

    @Test
    public void test1() {
        String s1 = new String("123ASDzxc");
        String s2 = s1;

        System.out.println(s2.substring(0, 5));

        System.out.println(s1);
        System.out.println("s2 = " + s2);
    }
}
