package com.vulcan.dao;

import com.vulcan.entity.po.TEsbConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TEsbConfigDao extends JpaRepository<TEsbConfig,Integer> {
 
}