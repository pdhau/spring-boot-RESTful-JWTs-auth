package com.pdhau.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pdhau.model.Role;
import com.pdhau.model.User;
import com.pdhau.model.UserRole;
import com.pdhau.repository.RoleRepository;
import com.pdhau.repository.UserRepository;
import com.pdhau.repository.UserRoleRepository;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = userRepository.findOneByUsername(username);
		return user;
	}
	
	public List<Role> getRoles(User user) {
		return getRoles(userRoleRepository.findByIdUser(user.getId().intValue()));
	}

	private List<Role> getRoles(List<UserRole> userRoles) {
		List<Role> roles = new ArrayList<>();
		for (UserRole userRole : userRoles) {
			roles.add((Role) roleRepository.findOne((long) userRole.getIdRole()));
		}
		return roles;
	}

}
