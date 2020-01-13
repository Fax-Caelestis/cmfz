package com.lxx.realm;

import com.lxx.dao.AdminDao;
import com.lxx.entity.Admin;
import com.lxx.util.MyWebWare;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class Myrealm extends AuthorizingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1. 获取用户名
        String principal = (String) authenticationToken.getPrincipal();
        // 2. 根据用户名从数据库查询数据
        AdminDao adminDao = (AdminDao) MyWebWare.getBeanByClass(AdminDao.class);
        Admin admin = new Admin();
        admin.setUsername(principal);
        Admin adminByDB = adminDao.selectOne(admin);
        // 3. 封装AuthenticationInfo信息
        //AuthenticationInfo authenticationInfo = new SimpleAccount(adminByDB.getUsername(),adminByDB.getPassword(),this.getName());
        //加密
        AuthenticationInfo authenticationInfo = new SimpleAccount(adminByDB.getUsername(),adminByDB.getPassword(), ByteSource.Util.bytes("abcd"),this.getName());
        return authenticationInfo;
    }
    // doGetAuthorizationInfo 获取授权信息的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
