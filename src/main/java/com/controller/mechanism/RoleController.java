package com.controller.mechanism;

import com.entity.Result;
import com.entity.SysRole;
import com.service.SysResourceService;
import com.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/26 11:08
 * @Description
 */
@RestController
@RequestMapping("manager/role")
public class RoleController {

    @Autowired
    SysRoleService service;

    @Autowired
    SysResourceService sysResourceService;

    @RequestMapping("index")
    public ModelAndView index(){
        return new ModelAndView("/role/role");
    }

    @RequestMapping("list")
    public Result list(){
        return new Result(true,"操作成功",service.selectAll());
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result listPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @RequestBody Map<String,Object> params){
        return new Result(true,"操作成功",service.selectByCondition(params,pageNum,pageSize));
    }

    @RequestMapping("selectAll")
    public Result selectAll(){
        return new Result(true,"查询成功",sysResourceService.selectAll());
    }

    @RequestMapping("selectResource/{rid}")
    public Result selectResource(@PathVariable("rid") Long rid){
        return new Result(true,"查询成功",sysResourceService.selectCheck(rid));
    }
    /**
     * 跳转更新页
     */
    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/role/role-save");
    }

    @RequestMapping("toDetail")
    public ModelAndView toDetailPage(){
        return new ModelAndView("/role/role-detail");
    }

    @RequestMapping("toRoleUser")
    public ModelAndView toRoleUser(){
        return new ModelAndView("/role/role-user");
    }

    @RequestMapping("toRoleSave")
    public ModelAndView toRoleSave(){
        return new ModelAndView("/role/role-save");
    }

    //批量删除角色的已选用户
    @RequestMapping("deleteBatch")
    public Result deleteBatch(long rid,long[] ids){

        return new Result(true,"操作成功",service.deleteBatch(rid, ids));
    }

    //默认类型转换是List<Integer>
    @RequestMapping("insertBatch")
    public Result insertBatch(long rid,Long[] cids){
        List<Long> list = new ArrayList<Long>();
        //Arrays.asList(cids):   将传入的数组转换成list集合  只支持非基本类型数组转换集合list
        //所以接收方法参数需要设置成包装类的数组Long[]
        int i = service.insertBatch( rid, Arrays.asList(cids));
        Result result = new Result();
        if(i>0){
            result.setMsg("更新成功");
            result.setSuccess(true);
        }
        return result;
    }


    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody SysRole sysRole){
        Result result = new Result();
        int i = service.updateByPrimaryKeySelective(sysRole);
        if(i>0){
            result.setSuccess(true);
            result.setMsg("更新成功");
        }
        return result;
    }

    @RequestMapping("doRoleUpdate")
    public Result doRoleUpdate(@RequestBody SysRole sysRole) {
        int i = service.updateByPrimaryKeySelective(sysRole);
        if (i>0){
            return new Result(true, "更新成功", null);
        }else {
            return new Result(false, "更新失败", null);
        }

    }
}
