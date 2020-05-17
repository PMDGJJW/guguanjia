package com.controller.Business;

import com.entity.Result;
import com.entity.Statute;
import com.github.pagehelper.PageInfo;
import com.service.StatuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * @auth jian j w
 * @date 2020/4/12 21:44
 * @Description
 */
@RestController
@RequestMapping("manager/Statues")
/*
 * @Description 法律法规
 * @author jian j w
 * @date 2020/4/12
 */
public class StatuteController {

    @Autowired
    StatuteService statuteService;

    //首页
    @RequestMapping("index")
    public ModelAndView index() {
        return new ModelAndView("/statute/index");
    }

    //分页查询
    @RequestMapping("selectPage/{pageNum}/{pageSize}/{type}")
    public Result selectPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize,@PathVariable("type") Integer type) {
        PageInfo<Statute> pageInfo = statuteService.selectPage(pageNum, pageSize,type);
        return new Result(true,"200",pageInfo);
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/statute/update");
    }

    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody Statute statute){
        statute.setUpdateDate(new Date());
        int i = statuteService.updateByPrimaryKeySelective(statute);
        if (i>0){
            return new Result(true,"更新成功",null);
        }
        else {
            return new Result(false,"更新失败",null);
        }
    }
    @RequestMapping("doDelete")
    public Result doDelete(@RequestBody Statute statute){
        int i = statuteService.updateByPrimaryKeySelective(statute);
        if (i>0){
            return new Result(true,"删除成功",null);
        }
        else {
            return new Result(false,"删除失败",null);
        }
    }

    @RequestMapping("doInsert")
    public Result doInsert(@RequestBody Statute statute){
        statute.setPubDate(new Date());
        statute.setCreateDate(new Date());
        statute.setDelFlag("0");
        statute.setUpdateDate(new Date());
        int i = statuteService.insertSelective(statute);
        if (i>0){
            return new Result(true,"添加成功",null);
        }
        else {
            return new Result(false,"添加失败",null);
        }
    }

}
