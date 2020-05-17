package com.controller;

import com.entity.Result;
import com.entity.SysResource;
import com.entity.SysUser;
import com.service.SysResourceService;
import com.service.SysUserService;
import com.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/10 22:01
 * @Description
 */
@Controller
@RequestMapping("main")
public class MainController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysResourceService sysResourceService;

    @RequestMapping("navbar")
    public String navbar() {
        return "/comment/navbar";
    }

    @RequestMapping("sidebar")
    public String sidebar() {
        return "/comment/sidebar";
    }

    @RequestMapping("toIndex")
    public String toIndex() {
        return "/index";
    }

    @RequestMapping("doLogin")
    @ResponseBody
    public Result doLogin(@RequestBody Map<String, Object> params, HttpSession session) {
        String check = (String) session.getAttribute("checkCode");
        SysUser sysUser = new SysUser();
        Result result = new Result(true,"登录成功",null);

        String name = (String)params.get("name");
        String passWord = (String) params.get("passWord");
        if (params.containsKey("code") && !StringUtils.isEmpty(params.get("code"))) {
            if (!check.equals(params.get("code"))) {
                return new Result(false, "验证码错误", null);
            } else {
                if (params.containsKey("name") && !StringUtils.isEmpty(params.get("name"))){
                 sysUser.setUsername(name);
                }
                if (params.containsKey("passWord") && !StringUtils.isEmpty(params.get("passWord"))){
                 String checkPassWord = EncryptUtils.MD5_HEX(EncryptUtils.MD5_HEX(passWord)+name);
                 sysUser.setPassword(checkPassWord);
                }
                SysUser loginUser = sysUserService.selectOne(sysUser);
                if ( loginUser == null){
                    result.setSuccess(false);
                    result.setMsg("用户名或者密码错误");
                    result.setObj(null);
                }
                else {
                    loginUser.setPassword(null);
                    List<SysResource>userResource = new ArrayList<>();
                    userResource = sysResourceService.selectByUid(loginUser.getId());
                    Map<String,Object> map = new HashMap<>();
                    map.put("loginUser",loginUser);
                    map.put("userResource",userResource);
                    result.setObj(map);
                    session.setAttribute("loginUser",loginUser);
                    session.setAttribute("resources",userResource);
                }
            }
        }
        else {
            result.setSuccess(false);
            result.setMsg("用户名或者密码错误");
            result.setObj(null);
        }
        return result;
    }

    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/api/login.html";
    }
}
