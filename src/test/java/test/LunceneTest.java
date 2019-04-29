package test;

import com.baizhi.cmfz.dao.ArticleDao;
import com.baizhi.cmfz.entity.Article;
import com.baizhi.cmfz.lucene.LunceneDaoImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LunceneTest extends AppTest {
    @Autowired
    private LunceneDaoImpl lunceneDao;
    @Autowired
    private ArticleDao articleDao;

    @Test
    public void deleteAndCreateTest() {
        List<Article> allByLimit = articleDao.getAllByLimit(0, 9999, new Article());
        lunceneDao.resettingIndex(allByLimit);
    }
}
