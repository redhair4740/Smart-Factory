package com.vulcan.dao;

import com.vulcan.entity.po.TEsbConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.dao
 * @name: TEsbConfigDao
 * @Date: 2024/4/11  下午4:26
 * @Description //TODO
 */
@Repository
public interface TEsbConfigDao extends JpaRepository<TEsbConfig,Integer> {
 
}