package com.lxx.service;

import com.lxx.entity.User;
import com.lxx.entity.UserMapDTO;

import java.util.List;

public interface UserService {
    //分页查所有
    public List<User> querryBypage(Integer page, Integer rows);
    //查询用户分布
    public List<UserMapDTO> querryByLocation(String sex);
    //添加
    public void insert(User user);
    //修改
    public void update(User user);
    //删除
    public void delete(User user);
    //差一个
    public User querryOne(User user);
}
