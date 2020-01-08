package com.lxx.dao;


import com.lxx.entity.Course;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CourseDao extends Mapper<Course> {
    public List<Course> querryByUserId(String uid);
}
