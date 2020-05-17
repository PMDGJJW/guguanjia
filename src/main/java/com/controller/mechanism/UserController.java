package com.controller.mechanism;

import com.dao.SysUserMapper;
import com.entity.Result;
import com.entity.SysRole;
import com.entity.SysUser;
import com.github.pagehelper.PageInfo;
import com.service.SysRoleService;
import com.service.SysUserService;
import com.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/25 13:11
 * @Description
 */
@RestController
@RequestMapping("manager/user")
public class UserController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysUserMapper sysUserMapper;

    @RequestMapping("index")
    public ModelAndView index(){
        return new ModelAndView("/user/user-list");
    }
    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/user/detail");
    }

    @RequestMapping("toDetail")
    public ModelAndView toDetail(){
        return new ModelAndView("/user/user-detail");
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize,@RequestBody Map<String,Object>params){
        PageInfo<SysUser>pageInfo = sysUserService.selectByCondition(pageNum,pageSize,params);
        return new Result(true,"查询成功",pageInfo);
    }

    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody SysUser sysUser) {
        int i = sysUserService.updateByPrimaryKeySelective(sysUser);
        if (i>0){

            return new Result(true,"更新成功",null);
        }
        else {
            return new Result(false,"更新失败",null);
        }
    }

    @RequestMapping("doDelete")
    public Result doDelete(@RequestBody SysUser sysUser){
        int i = sysUserService.updateByPrimaryKeySelective(sysUser);
        if (i>0){
            return new Result(true,"删除成功",null);
        }
        else {
            return new Result(false,"删除失败",null);
        }
    }

    @RequestMapping("doInsert")
    public Result doInsert(@RequestBody SysUser sysUser){

        int i = sysUserService.insertSelective(sysUser);
        if (i>0){

            return new Result(true,"添加成功",null);
        }
        else {
            return new Result(false,"添加失败",null);
        }
    }

    @RequestMapping("selectRole")
    public Result selectRole(){
        return new Result(true,"查询成功",sysRoleService.selectAll());
    }

    @RequestMapping("selectDetail/{id}")
    public Result selectDetail(@PathVariable("id") int id){
        Map<String,Object>map = new HashMap<>();
        map.put("id",id);
        return  new Result(true,"查询成功",sysUserMapper.selectByCondition(map));
    }

    @RequestMapping(value = "selectByRid/{rid}")
    public Result selectByRid(@PathVariable("rid")long rid){
        return new Result(true,"查询成功",sysUserService.selectByRid(rid));//Result 统一结果响应
    }

    @RequestMapping("selectNoRole")
    public Result selectNoRole(long rid,long oid){
        return new Result(true,"操作成功",sysUserService.selectNoRole(rid,oid));
    }
}
