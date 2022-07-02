package com.example.wt2.authcauthz;

//Testfunktionalität

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class WrapperSecUtil {

    public WrapperSecUtil() {
    }

    public org.apache.shiro.subject.Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    public String getPrincipal(){
        return (String) SecurityUtils.getSubject().getPrincipal();
    }
}