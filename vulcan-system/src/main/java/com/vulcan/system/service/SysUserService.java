package com.vulcan.system.service;

import com.vulcan.domain.entity.dto.SysUserDto;
import com.vulcan.domain.entity.po.SysUser;
import com.vulcan.domain.entity.vo.SysUserVo;
import org.springframework.data.domain.Page;
import java.util.Optional;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.service
 * @name: SysUserService
 * @Date: 2024/4/12 下午3:28
 * @Description 系统用户服务接口，定义用户查询、分页列表等功能
 */
public interface SysUserService {

    Optional<SysUser> findById(Long id);

    Page<SysUserVo> findAll(SysUserDto sysUserDto);

    Optional<SysUser> findByLoginName(String loginName);

}
