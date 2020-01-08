package com.lxx.service;

import com.lxx.entity.Album;

import java.util.List;

public interface AlbumService {
    //分页
    public List<Album> querrypage(Integer page, Integer rows);
    //修改
    public void update(Album album);
    //删除
    public void delete(Album album);
    //差一个
    public Album querryOne(Album album);
    //添加
    public void insert(Album album);

}
