package com.zxh.mapper;

import com.zxh.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DepartmentMapper {
    List<Department> queryAll();

    List<Department> queryByName(String deptName);

    Department queryById(Integer deptId);

    Department queryBySdeptId(Integer sdeptId);

    Department queryByMId(Integer deptMId);

    Integer add(Department department);

    Integer update(Department department);

    Integer delete(Integer deptId);
}
