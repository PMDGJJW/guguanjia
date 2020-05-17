package com.controller.mechanism;

import com.entity.Result;
import com.entity.SysArea;
import com.github.pagehelper.PageInfo;
import com.service.SysAreaService;
import com.sun.net.httpserver.HttpServer;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.net.www.http.HttpCaptureOutputStream;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/18 16:04
 * @Description 区域管理控制层
 */

@RestController
@RequestMapping("manager/area")
public class AreaController {

    @Autowired
    SysAreaService sysAreaService;

    /*首页跳转*/
    @RequestMapping("index")
    public ModelAndView area() {
        return new ModelAndView("/area/area");
    }

    /*分页查询*/
    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @RequestBody Map<String, Object> params) {
        PageInfo<SysArea> pageInfo = sysAreaService.selectPage(pageNum, pageSize, params);
        return new Result(true, "查询成功", pageInfo);
    }

    /*跳转更新页*/
    @RequestMapping("toUpdate")
    public ModelAndView toUpdate() {
        return new ModelAndView("/area/save");
    }

    /*更新执行*/
    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody SysArea sysArea) {
        int i = sysAreaService.updateByPrimaryKeySelective(sysArea);
        if (i > 0) {
            return new Result(true, "更新成功", null);
        } else {
            return new Result(false, "更新失败", null);
        }
    }
    @RequestMapping("doDown")
    public void downLoad(HttpServletResponse response) throws Exception{
        response.addHeader("Content-Disposition",
                "attachment;filename=" + new String("区域名单.xlsx".getBytes("UTF-8"), "iso8859-1"));
        sysAreaService.downloadExcel(response.getOutputStream());
    }

    @RequestMapping("upLoad")
    public Result upLoad(MultipartFile file) throws Exception{
        int i = sysAreaService.upload(file.getInputStream());
        if (i>0){
            return new Result(true,"上传成功",null);
        }
        else {
            return new Result(false,"上传失败",null);
        }

    }
    /*查询所有区域*/
    @RequestMapping("selectAll")
    public Result selectAll() {
        return new Result(true, "success", sysAreaService.selectAll());
    }

    /*跳转到图标选择页 */
    @RequestMapping("toModules")
    public ModelAndView toModules() {
        return new ModelAndView("/modules/font-awesome");
    }

    //跳转选择上级目录
    @RequestMapping("toSelect")
    public ModelAndView toSelect() {
        return new ModelAndView("/area/select");
    }

    //根据id跳转更新页数据回显
    @RequestMapping("selectBySubId")
    public Result selectBySubId (String sid){
        return new Result(true,"查询成功",sysAreaService.selectByPrimaryKey(sid));
    }


}
