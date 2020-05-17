package com.controller.Business;

import com.entity.Result;
import com.service.ExaMineService;
import com.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/14 18:32
 * @Description
 */
@RestController
@RequestMapping("manager/examine")
public class ExamineController {


    @Autowired
    OfficeService officeService;

    @Autowired
    ExaMineService exaMineService;

    @RequestMapping("index")
    public ModelAndView index(){
        return new ModelAndView("/examine/index");
    }

    @RequestMapping("office")
    public Result selectOffice(){
        return new Result(true,"查询成功",officeService.selectAll());
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize,@RequestBody Map<String,Object>params){
        return new Result(true,"查询成功",exaMineService.pageInfo(pageNum,pageSize,params));
    }
}
