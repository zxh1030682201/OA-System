package com.zxh.controller;

import com.google.gson.Gson;
import com.zxh.pojo.Department;
import com.zxh.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dept")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    private Gson gson=new Gson();

    @GetMapping("/queryAll")
    public String queryAllDept(){
        List<Department> departmentList=departmentService.queryAll();
        return gson.toJson(departmentList);
    }

    @GetMapping("/queryByName")
    public String queryByName(@RequestParam("deptName") String deptName){
        List<Department> departmentList=departmentService.queryByName(deptName);
        return gson.toJson(departmentList);
    }

    @GetMapping("/queryById/{deptId}")
    public String queryDeptById(@PathVariable("deptId") Integer deptId){
        Department department = departmentService.queryById(deptId);
        return gson.toJson(department);
    }

    @PostMapping("/add")
    public String addDept(@RequestBody Map<String,Object> reqMap){

        return departmentService.add(getDept(reqMap));

    }

    @PutMapping("/update")
    public String updateUser(@RequestBody Map<String,Object> reqMap){
        Integer deptId=(int)reqMap.get("deptId");
        Department department = getDept(reqMap);
        department.setDeptId(deptId);
        return departmentService.update(department);

    }

    @DeleteMapping("/delete/{deptId}")
    public String deleteUser(@PathVariable("deptId") Integer deptId){

        return departmentService.delete(deptId);

    }

    private Department getDept(Map<String,Object> map){

        System.out.println("获取的数据   "+map);

        String deptName=map.get("deptName").toString();
        Integer sdeptId=(int)map.get("sdeptId");
        Integer deptMId=(int)map.get("deptMId");
        Department department = new Department(0,deptName,sdeptId,null,deptMId,null);

        System.out.println("生成的信息  "+department);

        return department;
    }

}
