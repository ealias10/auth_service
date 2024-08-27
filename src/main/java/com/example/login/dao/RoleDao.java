package com.example.login.dao;

import com.example.login.model.Role;
import com.example.login.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoleDao {

  private final RoleRepository roleRepository;

  public Role getRoleByName(String role) {
    return roleRepository.getRoleByName(role);
  }
}
