package com.lxx.service;

import com.lxx.entity.Article;

import java.util.List;

public interface ArticleService {
    //添加
    public void insert(Article article);
    //修改
    public void upload(Article article);
    //分页
    public List<Article> selectBypage(Integer page, Integer rows);
    //根据上师id查所有
    public List<Article> selectByGuruId(String guruId);
    //删除多个
    public void delete(String[] id);
    //删除一个
    public void deleteOne(Article article);
    //差一个
    public Article querryOne(String id);
}
