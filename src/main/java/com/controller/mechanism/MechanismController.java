package com.controller.mechanism;

import com.entity.Office;
import com.entity.Result;
import com.entity.Waste;
import com.github.pagehelper.PageInfo;
import com.service.MechanismService;
import com.service.WasteService;
import com.service.WasteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/21 18:16
 * @Description 机构管理
 */

@RestController
@RequestMapping("manager/office")
public class MechanismController {

    @Autowired
    MechanismService mechanismService;

    @Autowired
    WasteTypeService wasteTypeService;

    @Autowired
    WasteService wasteService;


    /* 跳转首页 */
    @RequestMapping("index")
    public ModelAndView index(){
        return new ModelAndView("/mechanism/office");
    }

    /*分页及查询条件*/
    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @RequestBody Map<String, Object> params) {
        PageInfo<Office> pageInfo = mechanismService.selectPage(pageNum,pageSize,params);
        return new Result(true, "查询成功",pageInfo );
    }
    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/mechanism/update");
    }

    @RequestMapping("selectZtree")
    public Result selectZtree(@RequestBody Map<String,Object>params){
        return new Result(true,"查询成功",mechanismService.officeZtree(params));
    }

    @RequestMapping("selectWasteType")
    public Result selectWasteType(){
        return new Result(true,"查询成功",wasteTypeService.selectAll());
    }

    @RequestMapping("selectWaste/{id}")
    public Result selectWaste(@PathVariable("id") Long id){
        Waste waste = new Waste();
        waste.setParentId(id);
        return  new Result(true,"查询成功",wasteService.select(waste));
    }

    /**
     * 根据office的id查询waste
     * @return
     */
    @RequestMapping("selectWasteByOid/{oid}")
    public Result selectWasteByOid(@PathVariable("oid") long oid){
        return  new Result(true,"操作成功",wasteService.selectByOid(oid));
    }

    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody Office sysOffice){
        Result result = new Result();
        int i = mechanismService.updateByPrimaryKeySelective(sysOffice);
        if(i>0){
            result.setSuccess(true);
            result.setMsg("更新成功");
        }
        return result;
    }
    @RequestMapping("doDelete/{id}")
    public Result doDelete(@PathVariable("id") Long id){
        Office office = new Office();
        office.setId(id);
        office.setDelFlag("1");
        int i= mechanismService.updateByPrimaryKeySelective(office);
        if(i>0){
            return new Result(true,"删除成功",null);
        }
        else {
            return new Result(false,"删除失败",null);
        }
    }
 }
