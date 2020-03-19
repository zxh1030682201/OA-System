package com.zxh.service;

import com.zxh.mapper.DepartmentMapper;
import com.zxh.mapper.UserMapper;
import com.zxh.pojo.Department;
import com.zxh.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private UserMapper userMapper;

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
        Department dept=departmentMapper.queryByMId(department.getDeptMId());
        User user=userMapper.queryById(department.getDeptMId());
        if(dept == null){
            departmentMapper.add(department);
            if(user.getRole()!=4){
                user.setRole(3);
                userMapper.update(user);
            }
            return "添加部门成功";
        }else{
            return "该员工已经主管其他部门";
        }

    }

    public String update(Department department){
        //查询原本的部门信息
        Department oldDept=departmentMapper.queryById(department.getDeptId());
        User oldDeptMan=userMapper.queryById(oldDept.getDeptMId());
        //如果部门负责人没有改变就直接更新
        if(department.getDeptMId() == oldDept.getDeptMId()){
            departmentMapper.update(department);
            return "修改部门成功";
        }else{
            //查询新的负责人有没有已有部门
            Department dept=departmentMapper.queryByMId(department.getDeptMId());
            if(dept == null){
                User newDeptMan=userMapper.queryById(department.getDeptMId());
                //如果没有则直接更新,并将旧负责人权限降低，新负责人权限升高(权限不为3时)
                departmentMapper.update(department);
                if(oldDeptMan.getRole() != 4){
                    oldDeptMan.setRole(1);
                    userMapper.update(oldDeptMan);
                }
                if(newDeptMan.getRole() != 4){
                    newDeptMan.setRole(3);
                    userMapper.update(newDeptMan);
                }
                return "修改部门成功";
            }else{
                return "该员工已经主管其他部门";
            }

        }

    }

    public String delete(int deptId){
        List<Department> departmentTest=departmentMapper.queryBySdeptId(deptId);
        Department dept=departmentMapper.queryById(deptId);
        User user=userMapper.queryById(dept.getDeptMId());
        if(departmentTest.size() == 0){
            departmentMapper.delete(deptId);
            if(user.getRole() != 3){
                user.setRole(1);
                userMapper.update(user);
            }
            return "删除部门成功";
        }else{
            return "存在以此部门为上级部门，不能删除";
        }
    }

}
