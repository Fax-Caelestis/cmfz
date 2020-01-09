package com.lxx.dao;

import com.lxx.cache.MybatisClass;
import com.lxx.entity.Banner;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
@CacheNamespace(implementation = MybatisClass.class)
public interface BannerDao extends Mapper<Banner> , InsertListMapper <Banner>{
    //修改状态
    public void updateStatus(Banner banner);
    List<Banner> queryBannersByTime();
}
