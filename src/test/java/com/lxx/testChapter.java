package com.lxx;

import com.lxx.dao.AlbumDao;
import com.lxx.dao.ChapterDao;
import com.lxx.entity.Album;
import com.lxx.entity.Chapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class testChapter {
    @Autowired
    AlbumDao albumDao;
    @Autowired
    ChapterDao chapterDao;

    @Test
    public void name() {
        List<Chapter> chapters = chapterDao.querryAllByAlbumId("1");
        for (Chapter chapter : chapters) {
            System.out.println(chapter);
        }
    }

    @Test
    public void name1() {
        Album album = new Album();
        album.setId("1");
        Album album1 = albumDao.selectOne(album);
        System.out.println(album1);
        /*List<Album> list = albumDao.selectAll();
        for (Album album : list) {
            System.out.println(album);
        }*/

    }

    @Test
    public void dsa() {
        Example example = new Example(Chapter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("albumId","1");
        List<Chapter> chapters = chapterDao.selectByExample(example);
        System.out.println(chapters);
    }
}
