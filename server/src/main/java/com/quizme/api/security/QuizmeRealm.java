package com.quizme.api.security;

import com.quizme.api.dao.UserDAO;
import com.quizme.api.model.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jbeale on 1/29/15.
 */
@Component
public class QuizmeRealm extends AuthorizingRealm {
    protected UserDAO userDAO;

    public QuizmeRealm() {
        setName("QuizmeRealm");
        configureCredentialSecurity();
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    protected void configureCredentialSecurity() {
        HashedCredentialsMatcher hcm = new HashedCredentialsMatcher();
        hcm.setHashAlgorithmName("SHA-256");
        hcm.setHashIterations(100000);
        hcm.setStoredCredentialsHexEncoded(true);
        setCredentialsMatcher(hcm);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        User user = userDAO.findUser(token.getUsername());
        if (user != null) {
            SimpleAuthenticationInfo ai = new SimpleAuthenticationInfo(user.getId(), user.getPassword(), getName());
            ai.setCredentialsSalt(new SimpleByteSource("GLOBALSALT"));
            return ai;
        } else {
            return null;
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Integer userId = (Integer) principals.fromRealm(getName()).iterator().next();
        User user = userDAO.getUser(userId);

        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            /*for (Role role : user.getRoles()) {
                //info.addRole(role.getName());
                //info.addStringPermission(role.getPermissions());
            }*/
            return info;
        } else {
            return null;
        }
    }

}
