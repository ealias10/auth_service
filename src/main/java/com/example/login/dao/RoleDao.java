package com.example.login.dao;



import com.example.login.model.Role;
import com.example.login.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDao {

    @Autowired
    RoleRepository roleRepository;

    public Role getRoleByName(String role) {
        return roleRepository.getRoleByName(role);
    }
}
