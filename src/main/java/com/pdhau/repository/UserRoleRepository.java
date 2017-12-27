package com.pdhau.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pdhau.model.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Long>{
	public List<UserRole> findByIdUser(int idUser);
}
