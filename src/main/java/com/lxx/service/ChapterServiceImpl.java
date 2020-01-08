package com.lxx.service;

import com.lxx.aspect.Mylog;
import com.lxx.dao.ChapterDao;
import com.lxx.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;
    //查所有
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public List<Chapter> querryAllByAlbumId(String albumId) {
        return chapterDao.querryAllByAlbumId(albumId);
    }

    @Override
    public List<Chapter> querryPage(Integer page, Integer rows, String albumId) {
        Example example = new Example(Chapter.class);
        example.createCriteria().andEqualTo("albumId",albumId);
        return chapterDao.selectByExampleAndRowBounds(example,new RowBounds((page-1)*rows,rows));
    }

    //差一个
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public Chapter querryOne(Chapter chapter) {
        return chapterDao.selectOne(chapter);
    }
    @Mylog(name = "修改章节信息")
    //x修改
    @Override
    public void update(Chapter chapter) {
        chapterDao.updateByPrimaryKeySelective(chapter);
    }

    @Mylog(name = "删除一个章节")
    //删除
    @Override
    public void delete(Chapter chapter) {
        chapterDao.deleteByPrimaryKey(chapter.getId());
    }

    @Mylog(name = "添加一个章节")
    //添加
    @Override
    public void insert(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString());
        chapterDao.insert(chapter);
    }

}
