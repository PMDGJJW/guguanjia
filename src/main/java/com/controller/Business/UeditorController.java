package com.controller.Business;

import com.baidu.ueditor.ActionEnter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @auth jian j w
 * @date 2020/4/16 19:33
 * @Description
 */
@RestController
@RequestMapping("ueditor")
public class UeditorController {

    @Value("${realPath}")
    private String realPath;
    @Value("${path}")
    private String path;

    @RequestMapping
    public String execute(String action, HttpServletRequest request, MultipartFile upfile){
        String result = null;
        if("config".equals(action)){//富文本编辑器初始化后会自动请求访问统一接口action=config，返回配置json

            String rootPath = request.getServletContext().getRealPath( "/" );
            result = new ActionEnter( request, rootPath ).exec();//调用ueditorapi读取config.json，转换成json字符串
        }
        else if ("uploadImage".equals(action)){
            if (!upfile.isEmpty()){
                File flie = new File(realPath);
                String fileName = UUID.randomUUID().toString();
                String originalFilename = upfile.getOriginalFilename();
                String type = originalFilename.substring(originalFilename.lastIndexOf("."));
                String realName = fileName+type;

                try {
                    upfile.transferTo(new File(flie, realName));
                    result = new JSONObject(resultMap("SUCCESS",originalFilename,upfile.getSize(),realName,type,path+File.separator+realName)).toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    result = new JSONObject(resultMap("FAIL",null,0,null,null,null)).toString();
                }
            }
        }
        return result;
    }

    private Map<String,Object> resultMap(String state, String original, long size, String title, String type, String url){
        Map<String ,Object> result = new HashMap<>();
        result.put("state",state);
        result.put("original",original);
        result.put("size",size);
        result.put("title",title);
        result.put("type",type);
        result.put("url", url);
        return result;
    }
}
