package test;

import com.baizhi.cmfz.AppRun;
import com.baizhi.cmfz.dao.LessonDao;
import com.baizhi.cmfz.entity.Lesson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRun.class)
public class LessonTest {
    @Autowired
    private LessonDao lessonDao;

    @Test
    public void getAllLessonTest() {
        System.out.println(lessonDao.getAllLesson(null, null));
        System.out.println(lessonDao.getAllLesson("佛", null));
        System.out.println(lessonDao.getAllLesson(null, "修"));
    }

    @Test
    public void getOneTest() {
        System.out.println(lessonDao.selectById(3));
    }

    @Test
    public void insertTest() {
        System.out.println(lessonDao.insert(new Lesson(null, "insert test", 1, 1, null, null)));
    }
}
