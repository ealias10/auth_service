package com.example.login.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.login.model.Role;
import com.example.login.repository.RoleRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoleDaoTest {
  @InjectMocks RoleDao roleDao;
  @Mock RoleRepository roleRepository;
  private static final UUID ID = UUID.randomUUID();

  @Test
  void getRoleByNameTest() {
    Mockito.when(roleRepository.getRoleByName(Mockito.anyString())).thenReturn(getRole());
    var response = roleDao.getRoleByName("ADMIN");
    assertEquals(ID, response.getId());
  }

  private static Role getRole() {
    return Role.builder().id(ID).build();
  }
}
