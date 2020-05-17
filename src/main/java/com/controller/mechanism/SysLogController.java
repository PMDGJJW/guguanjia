package com.controller.mechanism;

import com.entity.Result;
import com.entity.SysLog;
import com.github.pagehelper.PageInfo;
import com.service.SysLogService;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @auth jian j w
 * @date 2020/5/2 20:34
 * @Description
 */
@RestController
@RequestMapping("manager/syslog")
public class SysLogController {

    @Autowired
    SysLogService sysLogService;

    @RequestMapping("/index")
    public ModelAndView index(){
        return new ModelAndView("/log/log");
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize,  SysLog params){
        PageInfo<SysLog> pageInfo = sysLogService.selectPage(pageNum, pageSize, params);
        System.out.println(params);

        return new Result(true,"查询成功",pageInfo);

    }

}
