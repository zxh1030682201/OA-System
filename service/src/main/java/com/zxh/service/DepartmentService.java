package com.zxh.service;

import com.zxh.mapper.DepartmentMapper;
import com.zxh.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    public List<Department> queryAll(){
        List<Department> departmentList=departmentMapper.queryAll();
        return departmentList;
    }

    public List<Department> queryByName(String deptName){
        List<Department> departmentList=departmentMapper.queryByName(deptName);
        return departmentList;
    }

    public Department queryById(Integer deptId){
        Department department=departmentMapper.queryById(deptId);
        return department;
    }

    public String add(Department department){
        departmentMapper.add(department);
        return "添加部门成功";
    }

    public String update(Department department){
        departmentMapper.update(department);
        return "修改部门成功";
    }

    public String delete(int deptId){

        Department departmentTest=departmentMapper.queryBySdeptId(deptId);
        if(departmentTest == null){
            departmentMapper.delete(deptId);
            return "删除部门成功";
        }else{
            return "存在以此部门为上级部门，不能删除";
        }
    }

}
