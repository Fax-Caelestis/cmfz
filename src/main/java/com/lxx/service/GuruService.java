package com.lxx.service;

import com.lxx.entity.Guru;

import java.util.List;

public interface GuruService {
    //查所有
    public List<Guru> querryAll();
    //添加
    public void insert(Guru guru);
    //修改
    public void upload(Guru guru);
    //删除
    public void delete(Guru guru);
    //差一个
    public Guru selectOne(Guru guru);
}
