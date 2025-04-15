package com.vulcan.system.service.impl;

import com.vulcan.domain.entity.dto.SysUserDto;
import com.vulcan.domain.entity.po.SysUser;
import com.vulcan.domain.entity.vo.SysUserVo;
import com.vulcan.domain.repository.CodeQueryRepository;
import com.vulcan.domain.repository.UserQueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
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

class SysUserServiceTest {

    @Mock
    private UserQueryRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CodeQueryRepository codeRepository;

    @InjectMocks
    private SysUserServiceImpl sysUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        // Arrange
        Long userId = 1L;
        SysUser expectedUser = new SysUser();
        expectedUser.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<SysUser> result = sysUserService.findById(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        verify(userRepository).findById(userId);
    }

    @Test
    void findAll_ShouldReturnPagedResults() {
        // Arrange
        SysUserDto dto = new SysUserDto();
        dto.setPageNumber(0);
        dto.setPageSize(10);

        SysUser user1 = new SysUser();
        user1.setId(1L);
        SysUser user2 = new SysUser();
        user2.setId(2L);

        Page<SysUser> userPage = new PageImpl<>(Arrays.asList(user1, user2));
        when(userRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(userPage);

        SysUserVo userVo1 = new SysUserVo();
        userVo1.setId(1L);
        SysUserVo userVo2 = new SysUserVo();
        userVo2.setId(2L);

        when(modelMapper.map(user1, SysUserVo.class)).thenReturn(userVo1);
        when(modelMapper.map(user2, SysUserVo.class)).thenReturn(userVo2);

        // Act
        Page<SysUserVo> result = sysUserService.findAll(dto);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(userRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(modelMapper, times(2)).map(any(SysUser.class), eq(SysUserVo.class));
    }

    @Test
    void findByLoginName_ShouldReturnUser_WhenUserExists() {
        // Arrange
        String loginName = "testUser";
        SysUser expectedUser = new SysUser();
        expectedUser.setLoginName(loginName);
        when(userRepository.findByLoginName(loginName)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<SysUser> result = sysUserService.findByLoginName(loginName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(loginName, result.get().getLoginName());
        verify(userRepository).findByLoginName(loginName);
    }
}