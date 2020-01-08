package com.lxx.service;

import com.lxx.entity.Chapter;

import java.util.List;

public interface ChapterService
{
    //查询所有属于此专辑的数据
    public List<Chapter> querryAllByAlbumId(String albumId);
    //分页
    public List<Chapter> querryPage(Integer page, Integer rows,String albumId);
    //差一个
    public Chapter querryOne(Chapter chapter);
    //修改
    public void update(Chapter chapter);
    //删除
    public void delete(Chapter chapter);
    //添加
    public void insert(Chapter chapter);
}
