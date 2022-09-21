package com.example.wethink.config;

import com.example.wethink.dto.Result;
import com.example.wethink.service.impl.CustomerUserDetailServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启security注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerUserDetailServiceImpl customerUserDetailService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider())
                .httpBasic()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    Result result = new Result(HttpStatus.UNAUTHORIZED.value(), "未登录，请先登录！");
                    response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                })
                .and()
                .authorizeRequests()
                .antMatchers("/comments/save").authenticated()
                .anyRequest().permitAll()

                .and()
                .formLogin()
                //登录失败，返回json
                .failureHandler((request, response, authentication) -> {
                    Result result;
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    if(authentication instanceof UsernameNotFoundException || authentication instanceof BadCredentialsException){
                        result = new Result<>(HttpStatus.UNAUTHORIZED.value(), "用户名或者密码错误!");
                    }else if(authentication instanceof DisabledException){
                        result = new Result<>(HttpStatus.FORBIDDEN.value(), "账户被禁用!");
                    }else {
                        result = new Result<>(HttpStatus.UNAUTHORIZED.value(), "登录失败!");
                    }
                    String json = new ObjectMapper().writeValueAsString(result);
                    response.getWriter().write(json);
                })
                .successHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_OK);
                    Result result = new Result(HttpStatus.OK.value(), "登录成功!", authentication);
                    response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                })
                .permitAll()

                .and()
                .logout() //开启注销
                .logoutSuccessHandler((request,response,authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    Result result = new Result(HttpStatus.OK.value(), "退出成功!", authentication);
                    response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                })
                .permitAll();
        http.csrf().disable();
        http.cors().disable();
        http.rememberMe();
        //解决中文乱码问题
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //对默认的UserDetailsService进行覆盖
        authenticationProvider.setUserDetailsService(customerUserDetailService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }

}