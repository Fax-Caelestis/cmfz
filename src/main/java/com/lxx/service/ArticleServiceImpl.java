package com.lxx.service;

import com.lxx.dao.ArticleDao;
import com.lxx.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;
    //添加
    @Override
    public void insert(Article article) {
        articleDao.insertSelective(article);
    }
    //修改
    @Override
    public void upload(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    //分页
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Article> selectBypage(Integer page, Integer rows) {

        return articleDao.selectByRowBounds(null,new RowBounds((page-1)*rows,rows));
    }
    //根据上师id查所有
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Article> selectByGuruId(String guruId) {
        return null;
    }
    //添加删除
    @Override
    public void delete(String[] id) {
        articleDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public void deleteOne(Article article) {
        articleDao.delete(article);
    }

    //差一个
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Article querryOne(String id) {
        return articleDao.selectByPrimaryKey(id);
    }
}
