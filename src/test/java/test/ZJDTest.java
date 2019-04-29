package test;

import com.baizhi.cmfz.dao.ArticleDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class ZJDTest {

    @Autowired
    private ArticleDao articleDao;

    public static double test1(double x) {

        return (2400 + 484.11 + x) * 0.1 + ((2400 + 484.11 + x) * 0.1 + 2400 + 484.11 + x) * 0.06 + 2400 + 484.11 + x;

    }


    public static void main(String[] args) {
        ArrayList<String> userNames = new ArrayList();
        userNames.add("张三");
        userNames.add("王二");
        userNames.add("李四");
        userNames.add("王小");

        String excluded = new String("王");
        int len = userNames.size();

        System.out.println("len = " + len);

        for (int i = 0; i < len; i++) {
            String tmp = userNames.get(i);
            if (tmp.startsWith(excluded)) {
                userNames.remove(tmp);
            }
        }

        for (String userName : userNames) {
            System.out.println("userName = " + userName);
        }

    }


}
