package com.lxx.dao;

import com.lxx.entity.Chapter;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ChapterDao extends Mapper<Chapter> {
    //查询所有属于此专辑的数据
    public List<Chapter> querryAllByAlbumId(String albumId);
}
