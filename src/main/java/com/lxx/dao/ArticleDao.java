package com.lxx.dao;

import com.lxx.entity.Article;

import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleDao extends Mapper<Article>, DeleteByIdListMapper<Article,String> {
    public List<Article> querryAll();
}
