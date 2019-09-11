package com.taobao.taobaoadmin.config;

import com.taobao.taobaoadmin.bo.AdminUserDetails;
import com.taobao.taobaoadmin.component.JwtAuthenticationTokenFilter;
import com.taobao.taobaoadmin.component.RestAuthenticationEntryPoint;
import com.taobao.taobaoadmin.component.RestfulAccessDeniedHandler;
import com.taobao.taobaoadmin.model.UmsAdmin;
import com.taobao.taobaoadmin.model.UmsPermission;
import com.taobao.taobaoadmin.service.Ums.UmsAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import javax.servlet.Filter;
import java.util.List;


/**
 * SpringSecurity的配置
 * 自定义权限
 */
@Configuration
@EnableWebSecurity //帮助我们创建Spring Security工作过程中要使用到的Filter
@EnableGlobalMethodSecurity(prePostEnabled = true)//使用表达式时间方法级别的安全性,4个注解可用
//Spring Security默认是禁用注解的，要想开启注解，
// 需要在继承WebSecurityConfigurerAdapter的类上加@EnableGlobalMethodSecurity注解，来判断用户对某个控制层的方法是否具有访问权限
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UmsAdminService adminService;

    //当访问接口没有权限时，自定义的返回结果
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    /**
     * 用于配置需要拦截的url路径、jwt过滤器及出异常后的处理器；
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()// 由于使用的是JWT，我们这里不需要csrf
                .disable()
                .sessionManagement()// 基于token，所以不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**"
                )
                .permitAll()
                .antMatchers("/admin/login", "/admin/register")// 对登录注册要允许匿名访问
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
                .permitAll()
                .antMatchers("/**")//测试时全部运行访问
                .permitAll()
                .anyRequest()// 除上面外的所有请求全部需要鉴权认证
                .authenticated();
        // 禁用缓存
        httpSecurity.headers().cacheControl();
        // 添加自定义的JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录结果返回
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }


    //作用到底是什么呢？答：用于配置UserDetailsService及PasswordEncoder
    //重写自动注入AuthenticationManagerBuilder对象，完成构建AuthenticationManager
    //AuthenticationManager的作用： 对用户提交的用户名和密码进行验证
    /**
     * 用于配置UserDetailsService及PasswordEncoder；
     * @param auth
     * @throws Exception
     */
    @Override
    protected  void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }


    /**
     * SpringSecurity定义的用于对密码进行编码及比对的接口，目前使用的是BCryptPasswordEncoder；
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
            return new Md5PasswordEncoder();
    }


        /**
         * UserDetailsService:   SpringSecurity定义的核心接口，用于根据用户名获取用户信息，需要自行实现；
         * UserDetails：    SpringSecurity定义用于封装用户信息的类（主要是用户信息和权限），需要自行实现；
         * @return
         */
        @Bean
        @Override
        public UserDetailsService userDetailsService() {
            //获取登录用户信息， 使用 -> 这个是Lambda表达式，参数username
            //接收一个参数username，返回一个AdminUserDetails对象
            return username -> {
                UmsAdmin admin = adminService.getAdminByUsername(username);
                if (admin != null) {
                    //获取用户所有权限（包括角色权限和+-权限）
                    List<UmsPermission> permissionList = adminService.getPermissionList(admin.getId());
                    //返回一个自己实现的用户信息对象，参数是用户，用户的权限
                    //自己实现接口UserDetail的实体类
                    return new AdminUserDetails(admin, permissionList);
                }
                throw new UsernameNotFoundException("用户名或密码错误");
            };
        }

        /**
         * JWT登录授权过滤器
         * 在用户名和密码校验前添加的过滤器，如果有jwt的token，会自行根据token信息进行登录。
         * @return
        */
        @Bean
        public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter () {
            return new JwtAuthenticationTokenFilter();
        }

        @Bean
        public CorsFilter corsFilter () {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration();
            config.addAllowedOrigin("*");
            config.setAllowCredentials(true);
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");
            source.registerCorsConfiguration("/**", config);
            FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
            bean.setOrder(0);
            return new CorsFilter(source);
        }



}
