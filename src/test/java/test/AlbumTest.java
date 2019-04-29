package test;

import com.baizhi.cmfz.AppRun;
import com.baizhi.cmfz.entity.Album;
import com.baizhi.cmfz.service.AlbumService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRun.class)
public class AlbumTest {
    @Autowired
    private AlbumService albumService;

    @Test
    public void getAllAlbumTest() {
        for (Album album : albumService.getAllAlbum()) {
            System.out.println("album = " + album);
        }
    }
}
