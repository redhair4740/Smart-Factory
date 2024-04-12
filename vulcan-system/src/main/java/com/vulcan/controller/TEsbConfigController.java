package com.vulcan.controller;

import com.vulcan.dao.TEsbConfigDao;
import com.vulcan.entity.po.TEsbConfig;
import com.vulcan.utils.redis.JedisPoolUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

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

    @PostMapping("/test2")
    public void test2(){
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtil.getJedisInstance();  // 获取Redis连接对象

            // 业务
            jedis.set("key1", "value111");
            System.out.println(jedis.get("key1"));
        } finally {
            jedis.close(); // 关闭redis连接
        }
    }

}
