package com.lxx;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;


public class TestShiro {
    @Test
    public void testShiro() {
        // 1. 获取安全管理器工厂
        IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:shiro.ini");
        // 2. 通过工厂获取安全管理器
        SecurityManager securityManager = iniSecurityManagerFactory.createInstance();
        // 3. 将安全管理器放置于当前Shiro环境中
        SecurityUtils.setSecurityManager(securityManager);
        // 4. shiro环境中获取主体对象
        Subject subject = SecurityUtils.getSubject();
        // 5. 通过前端输入用户名密码 封装令牌
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("Rxx", "962482");
        // 6. 登陆
        // 注意 : shiro的登陆状况 由报错信息告知
        // IncorrectCredentialsException : 密码错误
        // UnknownAccountException : 未知账号异常
        subject.login(usernamePasswordToken);
        // subject.isAuthenticated() 判断用户是否登陆
        if (subject.isAuthenticated()) {
            System.out.println("helloworld");
        }
    }
}
