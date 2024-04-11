package com.vulcan.controller;

import com.vulcan.dao.TEsbConfigDao;
import com.vulcan.entity.po.TEsbConfig;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.controller
 * @name: TEsbColumnConfigController
 * @Date: 2024/4/11  下午4:26
 * @Description //TODO
 */
@RestController
public class TEsbConfigController {

    @Resource
    private TEsbConfigDao tEsbConfigDao;

    @PostMapping("/test")
    public void test(){
        List<TEsbConfig> tEsbConfigList = tEsbConfigDao.findAll();
        System.out.println(tEsbConfigList.toString());
    }

}
