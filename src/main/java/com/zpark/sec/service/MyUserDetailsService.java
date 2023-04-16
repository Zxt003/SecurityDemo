package com.zpark.sec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //
        System.out.println("Username::"+username);
        Map<String, Object> systemUser = this.getSystemUser(username);
        System.out.println(systemUser);
        List<String> auth = (List<String>) systemUser.get("auth");
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList();
        for(String pr : auth){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(pr);
            authorityList.add(authority);
        }//给权限
        User user = new User(systemUser.get("username").toString(), systemUser.get("password").toString(), authorityList);
        return user;
    }

    private Map<String,Object> getSystemUser(String username){//写死的一个get方法，后续可以将map的值换成数据库获取过来的值，shiro
            // 假设从数据库中获得数据 se by username
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password",encoder.encode("123456"));

        List<String> auth = new ArrayList<>();
        auth.add("getUser");
        auth.add("addUser");
        auth.add("deleteUser");
        auth.add("editUser");
        map.put("auth",auth);
        System.out.println(auth);
        return map;
    }
}
