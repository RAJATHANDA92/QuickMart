package com.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.model.Role;



public interface RoleRepository extends JpaRepository<Role, Integer> {

}
