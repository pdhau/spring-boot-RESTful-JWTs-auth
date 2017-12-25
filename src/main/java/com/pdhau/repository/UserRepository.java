package com.pdhau.repository;

import org.springframework.data.repository.CrudRepository;

import com.pdhau.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	public User findOneByUsername(String username);
}
