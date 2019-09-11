package com.taobao.taobaoadmin.service.IMPL.Ums;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.dao.UmsAdminPermissionRelationDao;
import com.taobao.taobaoadmin.dao.UmsAdminRoleRelationDao;
import com.taobao.taobaoadmin.mapper.UmsAdminLoginLogMapper;
import com.taobao.taobaoadmin.mapper.UmsAdminMapper;
import com.taobao.taobaoadmin.mapper.UmsAdminPermissionRelationMapper;
import com.taobao.taobaoadmin.model.*;
import com.taobao.taobaoadmin.service.Ums.UmsAdminService;
import com.taobao.taobaoadmin.util.JwtTokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * UmsAdminService实现类
 */

@Service
public class UmsAdminServiceImpl implements UmsAdminService {


    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UmsAdminMapper adminMapper;

    @Autowired
    private PasswordEncoder  passwordEncoder;



    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;

    @Autowired
    private UmsAdminPermissionRelationMapper adminPermissionRelationMapper;

    @Autowired
    private UmsAdminPermissionRelationDao adminPermissionRelationDao;

    @Autowired
    private UmsAdminLoginLogMapper loginLogMapper;


    /**
     * 注册
     * @param umsAdminParam
     * @return
     */
    @Override
    public UmsAdmin register(UmsAdminService umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);

        //查询是否有相同用户名的用户
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = adminMapper.selectByExample(example);
        if (umsAdminList.size() > 0) {
            return null;
        }

        System.out.println("加密之前的用户的密码："+umsAdmin.getPassword());
        //将密码进行加密操作
        String Password = passwordEncoder.encodePassword(umsAdmin.getPassword(), null);
        umsAdmin.setPassword(Password);
        System.out.println("加密之后的用户的密码："+umsAdmin.getPassword());
        //插入到数据库中（增加）
        adminMapper.insert(umsAdmin);

        return umsAdmin;
    }

    /**
     *  登陆
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public String login(String username, String password) {
        //令牌
        String token = null;

        //密码需要客户端加密后传递，制作令牌
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, passwordEncoder.encodePassword(password, null));


        try {
            //security的证明接口authentication接口对象
            // 调用authenticationManager接口的验证方法---authenticate方法，AuthenticationManager是用来管理AuthenticationProvider的接口
            // authenticate方法查找支持该token(UsernamePasswordAuthenticationToken)认证方式的provider，然后调用该provider的authenticate方法进行认证
            Authentication authentication = authenticationManager.authenticate(authenticationToken);




            /*
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            */

            //springSecurity把用户信息全部保存在securityContextholder中，使用authentication对象来包装好这些信息
            // 获取security的上下文，设置认证（证明）对象
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //用户信息接口对象UserDetails，使用接口userDetailService的方法，根据用户名锁定用户信息对象
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            //根据用户信息接口对象使用JWT工具，生成令牌
            token = jwtTokenUtil.generateToken(userDetails);
            //根据用户名修改登录时间
            updateLoginTimeByUsername(username);
            //添加登录记录
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            LOGGER.warn("登陆异常：{}", e.getMessage());
        }

        return token;
    }

    /**
     * 添加登录记录
     * @param username
     */
    private void insertLoginLog(String username) {
        //获取管理员对象
        UmsAdmin admin = getAdminByUsername(username);
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
        loginLogMapper.insert(loginLog);
    }

    /**
     * 根据用户名获取后台管理员
     */
    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return null;
    }

    /**
     * 根据用户名修改登录时间
     */
    private void updateLoginTimeByUsername(String username) {
        UmsAdmin record = new UmsAdmin();
        record.setLoginTime(new Date());
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        adminMapper.updateByExampleSelective(record, example);
    }

    /**
     * 刷新token
     * @param oldToken 旧的token
     * @return
     */
    @Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring(tokenHead.length());
        //判断是否token已经失效,失效就重新拿一个token
        if (jwtTokenUtil.canRefresh(token)) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

    /**
     * 根据用户名或昵称分页查询用户
     */
    @Override
    public List<UmsAdmin> list(String name, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UmsAdminExample example = new UmsAdminExample();
        UmsAdminExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            //模糊查询
            criteria.andUsernameLike("%" + name + "%");
            example.or(example.createCriteria().andNickNameLike("%" + name + "%"));
        }

        return adminMapper.selectByExample(example);
    }

    /**
     * 根据用户id获取用户
     */
    @Override
    public UmsAdmin getItem(Long id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改指定用户信息
     */
    @Override
    public int update(Long id, UmsAdmin umsAdmin) {
        umsAdmin.setId(id);
        return adminMapper.updateByPrimaryKey(umsAdmin);
    }

    /**
     * 删除指定用户
     */
    @Override
    public int delete(Long id) {
        return adminMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取用户对于角色
     */
    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return adminRoleRelationDao.getRoleList(adminId);
    }

    /**
     * 修改用户的+-权限
     */
    @Override
    public int updatePermission(Long adminId, List<Long> permissionIds) {
        //删除原所有权限关系
        UmsAdminPermissionRelationExample relationExample = new UmsAdminPermissionRelationExample();
        relationExample.createCriteria().andAdminIdEqualTo(adminId);
        adminPermissionRelationMapper.deleteByExample(relationExample);


        //获取用户所有角色权限
        List<UmsPermission> permissionList = adminRoleRelationDao.getRolePermissionList(adminId);
        List<Long> rolePermissionList = permissionList.stream().map(UmsPermission::getId).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(permissionIds)) {
            List<UmsAdminPermissionRelation> relationList = new ArrayList<>();
            //筛选出+权限
            List<Long> addPermissionIdList = permissionIds.stream()
                    .filter(permissionId -> !rolePermissionList.contains(permissionId)).collect(Collectors.toList());
            //筛选出-权限
            List<Long> subPermissionIdList = rolePermissionList.stream()
                    .filter(permissionId -> !permissionIds.contains(permissionId)).collect(Collectors.toList());
            //插入+-权限关系
            relationList.addAll(convert(adminId, 1, addPermissionIdList));
            relationList.addAll(convert(adminId, -1, subPermissionIdList));
            return adminPermissionRelationDao.insertList(relationList);
        }

        return 0;
    }

    /**
     * 将+-权限关系转化为对象
     */
    private List<UmsAdminPermissionRelation> convert(Long adminId, Integer type, List<Long> permissionIdList) {
        List<UmsAdminPermissionRelation> relationList = permissionIdList.stream().map(permissionId ->{
            UmsAdminPermissionRelation relation = new UmsAdminPermissionRelation();
            relation.setAdminId(adminId);
            relation.setType(type);
            relation.setPermissionId(permissionId);
            return relation;
        }).collect(Collectors.toList());
        return relationList;
    }


    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        return adminRoleRelationDao.getPermissionList(adminId);
    }

}
