package com.service.impl.Business;

import com.dao.SysUserMapper;
import com.entity.SysUser;
import com.entity.SysUserRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.SysUserRoleService;
import com.service.SysUserService;
import com.service.impl.BaseImpl;
import com.util.EncryptUtils;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/24 20:18
 * @Description
 */
@Service
public class SysUserServiceImpl extends BaseImpl<SysUser> implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysUserRoleService sysUserRoleService;


    @Override
    public PageInfo<SysUser> selectByCondition(int pageNum, int pageSize, Map<String, Object> params) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> list = sysUserMapper.selectByCondition(params);
        return new PageInfo<SysUser>(list);
    }

    //重写更新方法，更新用户角色
    @Override
    public int updateByPrimaryKeySelective(SysUser sysUser) {
        int i = 0;
        i+= super.updateByPrimaryKeySelective(sysUser);
        if (sysUser.getPassword()!=null && sysUser.getPassword()!=""){
            String psd =  EncryptUtils.MD5_HEX(EncryptUtils.MD5_HEX(sysUser.getPassword())+sysUser.getUsername());
            sysUser.setPassword(psd);
        }
        if (sysUser.getUserRoleId()!=null ){
            //用户角色
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(sysUser.getRoleId());
            sysUserRole.setId(sysUser.getUserRoleId());
            i+=sysUserRoleService.updateByPrimaryKeySelective(sysUserRole);
        }
        else {
            //用户角色
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(sysUser.getRoleId());
            sysUserRole.setUserId(sysUser.getId());
            sysUserRole.setDelFlag(sysUser.getDelFlag());
            i+=sysUserRoleService.insertSelective(sysUserRole) ;
        }

        return super.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    public int insertSelective(SysUser sysUser) {
        int i = 0;
        if (sysUser.getPassword()!=null && sysUser.getPassword()!=""){
            String psd =  EncryptUtils.MD5_HEX(EncryptUtils.MD5_HEX(sysUser.getPassword())+sysUser.getUsername());
            sysUser.setPassword(psd);
        }
        sysUser.setUpdateDate(new Date());
        sysUser.setCreateDate(new Date());
        sysUser.setLoginDate(new Date());
        sysUser.setLoginIp("127.0.0.1");
        sysUser.setDelFlag("0");
        i += super.insertSelective(sysUser);
        if (i>0){
            String username = sysUser.getUsername();
            String passd = sysUser.getPassword();
            SysUser u = new SysUser();
            u.setUsername(username);
            u.setPassword(passd);
            SysUser user = sysUserMapper.selectOne(u);

            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(sysUser.getRoleId());
            sysUserRole.setUserId(user.getId());
            sysUserRole.setDelFlag("0");
            i+=sysUserRoleService.insertSelective(sysUserRole);
        }

        return i;
    }

    @Override
    public List<SysUser> selectByRid(long rid) {
        return sysUserMapper.selectByRid(rid);
    }

    @Override
    public List<SysUser> selectNoRole(long rid, long oid) {
        return sysUserMapper.selectNoRole(rid,oid);
    }
}
