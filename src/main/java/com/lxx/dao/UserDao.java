package com.lxx.dao;


import com.lxx.entity.User;
import com.lxx.entity.UserMapDTO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {
    //拆寻最近一段时间注册的用户
    Integer queryUserByTime(@Param("sex") String sex, @Param("day") Integer day);
    List<UserMapDTO> querryBylocation(String sex);
    User querryByPhone(String phone);
    List<User> querryFriend(String id);

}
