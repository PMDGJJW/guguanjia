package com.controller.Business;

import com.entity.Demand;
import com.entity.Result;
import com.github.pagehelper.PageInfo;
import com.service.DemandService;
import com.sun.org.glassfish.gmbal.ParameterNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @auth jian j w
 * @date 2020/4/11 10:03
 * @Description
 */
@RestController
@RequestMapping("manager/Service")
public class ServiceRequirementsController {

    @Autowired
    DemandService demandService;

    /*
     * @Description 服务需求首页
     * @author jian j w
     * @date 2020/4/11
     */
    @RequestMapping("index")
    public ModelAndView toIndex() {
        return new ModelAndView("/demand/index");
    }

    /*
     * @Description 更新页弹框
     * @author jian j w
     * @date 2020/4/11
     */
    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/demand/update");
    }

    /*
     * @Description 更新页弹框
     * @author jian j w
     * @date 2020/4/11
     */
    @RequestMapping("toDetail")
    public ModelAndView toDetail(){
        return new ModelAndView("/demand/detail");
    }

    /*
     * @Description 服务需求分页查询
     * @author jian j w
     * @date 2020/4/11
     */
    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        //分页查询详情
        PageInfo<Demand> pageInfo = demandService.selectPage(pageNum, pageSize);
        return new Result(true,"200",pageInfo);
    }
    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody Demand demand){
        int i = demandService.updateByPrimaryKeySelective(demand);
        if (i>0){
            return new Result(true,"更新成功",null);
        }
        else {
            return new Result(true,"更新失败",null);
        }
    }

}
