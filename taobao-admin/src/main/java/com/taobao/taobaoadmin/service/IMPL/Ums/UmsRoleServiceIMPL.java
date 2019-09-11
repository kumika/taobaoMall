package com.taobao.taobaoadmin.service.IMPL.Ums;

import com.taobao.taobaoadmin.dao.Ums.UmsRolePermissionRelationDao;
import com.taobao.taobaoadmin.mapper.UmsRoleMapper;
import com.taobao.taobaoadmin.mapper.UmsRolePermissionRelationMapper;
import com.taobao.taobaoadmin.model.*;
import com.taobao.taobaoadmin.service.Ums.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UmsRoleServiceIMPL implements UmsRoleService {

    @Autowired
    private UmsRoleMapper roleMapper;

    @Autowired
    private UmsRolePermissionRelationDao rolePermissionRelationDao;

    @Autowired
    private UmsRolePermissionRelationMapper rolePermissionRelationMapper;

    @Override
    public List<UmsRole> list() {
        return roleMapper.selectByExample(new UmsRoleExample());
    }

    @Override
    public int create(UmsRole role) {
        role.setCreateTime(new Date());
        role.setStatus(1);
        role.setAdminCount(0);
        role.setSort(0);

        return roleMapper.insert(role);
    }

    @Override
    public List<UmsPermission> getPermissionList(Long roleId) {
        return rolePermissionRelationDao.getPermissionList(roleId);
    }

    @Override
    public int updatePermission(Long roleId, List<Long> permissionIds) {
        //先删除原有关系
        UmsRolePermissionRelationExample example = new UmsRolePermissionRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        rolePermissionRelationMapper.deleteByExample(example);

        //批量插入新关系
        List<UmsRolePermissionRelation> relationList = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            UmsRolePermissionRelation relation = new UmsRolePermissionRelation();
            relation.setRoleId(roleId);
            relation.setPermissionId(permissionId);
            relationList.add(relation);
        }
        return rolePermissionRelationDao.insertList(relationList);
    }

    @Override
    public int update(Long id, UmsRole role) {
        role.setId(id);
        return roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public int delete(List<Long> ids) {
        UmsRoleExample example = new UmsRoleExample();
        example.createCriteria().andIdIn(ids);
        return roleMapper.deleteByExample(example);
    }
}
