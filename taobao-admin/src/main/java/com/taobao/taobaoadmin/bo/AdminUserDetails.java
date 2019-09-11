package com.taobao.taobaoadmin.bo;

import com.taobao.taobaoadmin.model.UmsAdmin;
import com.taobao.taobaoadmin.model.UmsPermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户详情
 */
public class AdminUserDetails implements UserDetails {


    private UmsAdmin umsAdmin;

    private List<UmsPermission> permissionList;

    public AdminUserDetails(UmsAdmin umsAdmin, List<UmsPermission> permissionList) {
        this.umsAdmin = umsAdmin;
        this.permissionList = permissionList;
    }

    // 封装了权限信息，用来返回该用户所拥有的权限，将用户的角色和权限关联起来
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限，将用户的角色和权限关联起来
        //为集合创建串行流。是Java8的新特性，Stream（流）是一个来自数据源的元素队列并支持聚合操作
        return permissionList.stream()
                //filter 方法用于通过设置的条件过滤出元素
                .filter(permission -> permission.getValue() != null)
                //map 方法用于映射每个元素到对应的结果----将用户的角色和权限关联起来？！
                .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                //Collectors 类实现了很多归约操作，例如将流转换成集合和聚合元素。
                .collect(Collectors.toList());
    }

    // 密码信息
    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    // 登录用户名
    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    // 帐户是否过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 帐户是否被冻结
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 帐户密码是否过期，一般有的密码要求性高的系统会使用到，比较每隔一段时间就要求用户重置密码
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 帐号是否可用
    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(1);
    }

}
