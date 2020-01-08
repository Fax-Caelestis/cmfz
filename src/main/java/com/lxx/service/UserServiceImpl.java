package com.lxx.service;

import com.lxx.dao.UserDao;
import com.lxx.entity.User;
import com.lxx.entity.UserMapDTO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public List<User> querryBypage(Integer page, Integer rows) {
        return userDao.selectByRowBounds(null,new RowBounds((page-1)*rows,rows));
    }

    @Override
    public List<UserMapDTO> querryByLocation(String sex) {

        return userDao.querryBylocation(sex);
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

    @Override
    public void update(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public void delete(User user) {
        userDao.deleteByPrimaryKey(user.getId());
    }

    @Override
    public User querryOne(User user) {
        return userDao.selectOne(user);
    }
}
