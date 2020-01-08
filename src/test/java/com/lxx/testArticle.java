package com.lxx;

import com.lxx.dao.ArticleDao;
import com.lxx.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class testArticle {
    @Autowired
    ArticleDao articleDao;

    @Test
    public void name() {
        List<Article> articles = articleDao.querryAll();
        for (Article article : articles) {
            System.out.println(article);
        }
    }

    @Test
    public void yeys() {
        Example example = new Example(Article.class);

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id","3");
        criteria.andEqualTo("guruId","1");
        Article article = articleDao.selectOneByExample(example);
        System.out.println(article);
    }
}
