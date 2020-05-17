package com.service.impl.Business;

import com.dao.SysRoleMapper;
import com.entity.SysRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.SysRoleService;
import com.service.impl.BaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/4/25 13:43
 * @Description
 */
@Service
@Transactional
public class SysRoleServiceImpl extends BaseImpl<SysRole> implements SysRoleService {

    @Autowired
    SysRoleMapper roleMapper;


    @Override
    public PageInfo<SysRole> selectByCondition(Map<String, Object> params, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum,pageSize);

        List<SysRole> sysRoles = roleMapper.selectByCondition(params);
        PageInfo<SysRole> pageInfo = new PageInfo<>(sysRoles);//生成分页对象

        return pageInfo;
    }


    @Override
    public int deleteBatch(long rid, long[] ids){
        return roleMapper.deleteBatch(rid,ids);
    }

    @Override
    public int insertBatch( long rid,List<Long> cids){
        return roleMapper.insertBatch(rid,cids);
    }

    @Override
    public int updateByPrimaryKeySelective(SysRole sysRole) {
        int i = 0;
        i = super.updateByPrimaryKeySelective(sysRole);
        if (sysRole.getRoleId()!=null&&sysRole.getRoleId().length>0){
            roleMapper.updateByDeleteRole(sysRole.getId());
             i= roleMapper.updateRole(sysRole.getId(),sysRole.getRoleId());
        }
        if (sysRole.getResourceId()!=null&&sysRole.getResourceId().length>0){
            roleMapper.updateByDeleteOffice(sysRole.getId());
            i=roleMapper.updateRoleOffice(sysRole.getId(),sysRole.getResourceId());
        }
        return i;
    }
}


