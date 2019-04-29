package test;

import com.baizhi.cmfz.dao.AdminLogDao;
import com.baizhi.cmfz.entity.Article;
import com.baizhi.cmfz.lucene.LunceneDaoImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminLogTest extends AppTest{
    @Autowired
    AdminLogDao adminLogDao;
    @Autowired
    private LunceneDaoImpl lunceneDao;

    @Test
    public void test1 () {
        List<Article> allByLimit = new ArrayList<Article>();
        allByLimit.add(new Article(1, "123", "123", "123", 1, new Date()));
        System.out.println(lunceneDao);
        lunceneDao.createIndex(allByLimit);
    }

}
