package com.controller.Business;

import com.entity.AppVersion;
import com.entity.Result;
import com.github.pagehelper.PageInfo;
import com.service.ApperVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/9 20:46
 * @Description
 */
@RestController
@RequestMapping("manager/app")
public class AppVersioncontroller {

    @Autowired
    ApperVersionService service;

    /*
     * @Description app首页
     * @author jian j w
     * @date 2020/4/10
     */
    @RequestMapping("index")
    public ModelAndView index() {
        return new ModelAndView("/app/index");
    }

    /*
     * @Description app查询
     * @author jian j w
     * @date 2020/4/10
     */
    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        PageInfo<AppVersion> pageInfo = service.selectPage(pageNum, pageSize);
        return new Result(true, "200", pageInfo);
    }
    /*
     * @Description app更新页
     * @author jian j w
     * @date 2020/4/10
     */
    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/app/update");
    }

    /*
     * @Description 跳转详情页
     * @author jian j w
     * @date 2020/4/10
     */
    @RequestMapping("toDetail")
    public ModelAndView toDetail(){
        return new ModelAndView("/app/detail");
    }

    /*
     * @Description 更新App
     * @author jian j w
     * @date 2020/4/10
     */
    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody AppVersion appVersion){
        int i = service.updateByPrimaryKeySelective(appVersion);
        if (i>0){
            return new Result(true,"更新成功",null);
        }
        else {
            return new Result(false,"更新失败",null);
        }
    }

    /*
     * @Description 插入App数据
     * @author jian j w
     * @date 2020/4/10
     */
    @RequestMapping("doInsert")
    public Result doInsert(@RequestBody AppVersion appVersion){
        appVersion.setCreateDate(new Date());
        appVersion.setUpdateDate(new Date());
        appVersion.setDelFlag("0");//设置为正常状态
        //TODO 更新创建人   从状态对象中获取user名
        int i = service.insertSelective(appVersion);
        if (i>0){
            return new Result(true,"保存成功",null);
        }
        else {
            return new Result(false,"保存失败",null);
        }
    }
    @RequestMapping("doDetail")
    public Result doDeatil(@RequestBody Map<String,Object> map){
        Object id = map.get("id");
        AppVersion appVersion = service.selectByPrimaryKey(id);
        return new Result(true,"App详情",appVersion);
    }
}
