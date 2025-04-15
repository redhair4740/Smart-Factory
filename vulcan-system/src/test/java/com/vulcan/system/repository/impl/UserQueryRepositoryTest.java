package com.vulcan.system.repository.impl;

import com.vulcan.domain.entity.po.SysUser;
import com.vulcan.system.repository.SysUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserQueryRepositoryTest {

    @Mock
    private SysUserRepository sysUserRepository;

    @InjectMocks
    private UserQueryRepositoryImpl userQueryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByLoginName_ShouldReturnUser_WhenUserExists() {
        // Arrange
        String loginName = "testUser";
        SysUser expectedUser = new SysUser();
        expectedUser.setLoginName(loginName);
        when(sysUserRepository.findByLoginName(loginName)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<SysUser> result = userQueryRepository.findByLoginName(loginName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(loginName, result.get().getLoginName());
        verify(sysUserRepository).findByLoginName(loginName);
    }

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        // Arrange
        Long userId = 1L;
        SysUser expectedUser = new SysUser();
        expectedUser.setId(userId);
        when(sysUserRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<SysUser> result = userQueryRepository.findById(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        verify(sysUserRepository).findById(userId);
    }

    @Test
    void save_ShouldReturnSavedUser() {
        // Arrange
        SysUser user = new SysUser();
        user.setLoginName("testUser");
        when(sysUserRepository.save(user)).thenReturn(user);

        // Act
        SysUser result = userQueryRepository.save(user);

        // Assert
        assertNotNull(result);
        assertEquals(user.getLoginName(), result.getLoginName());
        verify(sysUserRepository).save(user);
    }

    @Test
    void deleteById_ShouldDeleteUser() {
        // Arrange
        Long userId = 1L;

        // Act
        userQueryRepository.deleteById(userId);

        // Assert
        verify(sysUserRepository).deleteById(userId);
    }

    @Test
    void existsById_ShouldReturnTrue_WhenUserExists() {
        // Arrange
        Long userId = 1L;
        when(sysUserRepository.existsById(userId)).thenReturn(true);

        // Act
        boolean result = userQueryRepository.existsById(userId);

        // Assert
        assertTrue(result);
        verify(sysUserRepository).existsById(userId);
    }

    @Test
    void existsByLoginName_ShouldReturnTrue_WhenLoginNameExists() {
        // Arrange
        String loginName = "testUser";
        when(sysUserRepository.existsByLoginName(loginName)).thenReturn(true);

        // Act
        boolean result = userQueryRepository.existsByLoginName(loginName);

        // Assert
        assertTrue(result);
        verify(sysUserRepository).existsByLoginName(loginName);
    }

    @Test
    void findAll_ShouldReturnPagedResults() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        SysUser user1 = new SysUser();
        user1.setId(1L);
        SysUser user2 = new SysUser();
        user2.setId(2L);
        Page<SysUser> expectedPage = new PageImpl<>(Arrays.asList(user1, user2));
        
        when(sysUserRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(expectedPage);

        // Act
        Page<SysUser> result = userQueryRepository.findAll(any(Specification.class), pageable);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(sysUserRepository).findAll(any(Specification.class), eq(pageable));
    }
}