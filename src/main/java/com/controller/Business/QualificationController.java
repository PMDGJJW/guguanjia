package com.controller.Business;

import com.entity.Qualification;
import com.entity.QualificationCongition;
import com.entity.Result;
import com.github.pagehelper.PageInfo;
import com.service.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @auth jian j w
 * @date 2020/4/13 18:34
 * @Description
 */
@RestController
@RequestMapping("manager/qualification")
public class QualificationController {

    @Autowired
    QualificationService service;

    @RequestMapping("index")
    public ModelAndView index() {
        return new ModelAndView("/qualification/index");//  "/WEB-INF/html"+"/app/index"+".html"
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate() {
        return new ModelAndView("/qualification/update");//  "/WEB-INF/html"+"/app/index"+".html"
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, QualificationCongition condition) {
        PageInfo<Qualification> pageInfo = service.selectPage(pageNum, pageSize, condition);
        return new Result(true, "查询成功", pageInfo);//Result 统一结果响应
    }
    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody Qualification qualification){
        //设置更新时间
        Result result = new Result();
        int i = service.updateByPrimaryKeySelective(qualification);
        if(i>0){
            result.setSuccess(true);
            result.setMsg("更新成功");
        }else{
            result.setMsg("更新失败");
        }
        return result;
    }
}
