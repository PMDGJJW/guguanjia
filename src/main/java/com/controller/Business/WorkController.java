package com.controller.Business;

import com.entity.Detail;
import com.entity.Result;
import com.entity.Transfer;
import com.entity.WorkOrder;
import com.github.pagehelper.PageInfo;
import com.service.DetailService;
import com.service.OfficeService;
import com.service.TransferService;
import com.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/15 16:58
 * @Description 电子台账controller
 */
@RestController
@RequestMapping("manager/Work")
public class WorkController {

    @Autowired
    OfficeService officeService;

    @Autowired
    WorkOrderService workOrderService;

    @Autowired
    DetailService detailService;

    @Autowired
    TransferService transferService;

    //查询office
    @RequestMapping("office")
    public Result selectOffice(){
        return new Result(true,"查询成功",officeService.selectAll());
    }
    //电子帐台跳转
    @RequestMapping("index")
    public ModelAndView index(){
        return new ModelAndView("/work/admin/index");
    }

    @RequestMapping("toPrint")
    public ModelAndView print(){
        return new ModelAndView("/work/print");
    }

    //分页查询
    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize,@RequestBody Map<String,Object>params){
        PageInfo<WorkOrder> pageInfo = workOrderService.selectPage(pageNum, pageSize, params);
        return new Result(true,"查询成功",pageInfo);
    }

    //详情页
    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/work/work-detail");
    }

    @RequestMapping("selectDetail/{id}")
    public Map<String,Object> selectDetail(@PathVariable("id") Long id){
        Map<String,Object> map = new HashMap<>();
        List<Detail> details = detailService.selectByDetail(id);
        List<Transfer> transfers = transferService.selectByTransfer(id);
        map.put("details", details);
        map.put("transfet", transfers);
        return map;
    }

    @RequestMapping("selectOfficeByRid/{rid}")
    public Result selectOfficeByRid(@PathVariable("rid") Long rid){
        return new Result(true,"查询成功",officeService.selectByRid(rid));
    }
}
