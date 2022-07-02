package com.example.wt2.authcauthz;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.wt2.models.User;
import com.example.wt2.repositories.UserRepository;

public class accRealm extends AuthorizingRealm{

	@Autowired //Repo injection
	UserRepository userRepo;
	private static final transient Logger log = LoggerFactory.getLogger(accRealm.class);
	//Benötigte grundlegende Methoden um einen Realm zu generieren(Auth.Info und AuthenicationInfo
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String primaryPrincipal = (String) principals.getPrimaryPrincipal();
		Optional<User> userCheck = this.userRepo.findById(primaryPrincipal);
		if(userCheck.isEmpty()) {
			throw new UnknownAccountException();
		}
		// von Shrio bereitgestelltes POJO, in dem Rollen und Permissons intern gespeichert werden
		SimpleAuthorizationInfo sAuthInfo = new SimpleAuthorizationInfo();
		User user = userCheck.get();
		Set<String> userRole = user.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
		sAuthInfo.setRoles(userRole);
		sAuthInfo.setStringPermissions(user.getPermissions().stream().map(Enum::name).collect(Collectors.toSet()));
		return sAuthInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken userToken = (UsernamePasswordToken) token;
		if(userToken.getUsername().isEmpty()) {
			throw new UnknownAccountException();
		}
		Optional<User> userCheck = userRepo.findById(userToken.getUsername());
		if(userCheck.isEmpty()) {
			throw new UnknownAccountException();
		}
		return new SimpleAuthenticationInfo(userToken.getUsername(), userCheck.get().getPassword(), getName());
	}

}
