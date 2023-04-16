package com.zpark.sec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecConfig  extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http.authorizeHttpRequests().antMatchers("/index.html").permitAll();
        //http.authorizeHttpRequests().antMatchers("/xxx.html").permitAll();表示能全部放行xxx。html
        http.formLogin();//默认登录
        //http.authorizeHttpRequests().antMatchers("/xxx.html").hasAuthority("xxxUser") 必须有xxxUSer权限才可以访问xxx。html
        super.configure(http);
    }


}
