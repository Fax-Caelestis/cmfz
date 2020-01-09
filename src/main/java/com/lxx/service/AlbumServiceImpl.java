package com.lxx.service;

import com.lxx.aspect.Mylog;
import com.lxx.cache.AddOrSelectCache;
import com.lxx.cache.ClearCache;
import com.lxx.dao.AlbumDao;
import com.lxx.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumDao albumDao;
    //分页
    @AddOrSelectCache
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public List<Album> querrypage(Integer page, Integer rows) {
        return albumDao.selectByRowBounds(null,new RowBounds((page-1)*rows,rows));
    }
    @Mylog(name = "修改专辑信息")
    //修改
    @ClearCache
    @Override
    public void update(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }
    @Mylog(name = "删除专辑")
    //删除
    @ClearCache
    @Override
    public void delete(Album album) {
        albumDao.deleteByPrimaryKey(album.getId());

    }
    @AddOrSelectCache
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    //差一个
    @Override
    public Album querryOne(Album album) {

        return albumDao.selectOne(album);
    }
    @ClearCache
    @Mylog(name = "添加专辑")
    //添加
    @Override
    public void insert(Album album) {
        album.setId(UUID.randomUUID().toString());
        albumDao.insert(album);
    }
}
