package com.vulcan.utils.redis;

import com.vulcan.config.RedisConfig;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.entity.property
 * @name: RedisConfig
 * @Date: 2024/4/12  上午10:46
 * @Description // Redis 连接池工具包:封装Jedis连接池配置JedisPoolConfig信息，通过单例RedisPool获取redis对象
 */
@Component
public class JedisPoolUtil
{
    private static volatile JedisPool jedisPool = null;
    private static RedisConfig redisConfig = null;

    private JedisPoolUtil(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    /**
     * 1、获取RedisPool单例模式的对象：单例模式指的是在应用整个生命周期内只能存在一个实例
     *
     * @return RedisPool实例（单例）
     */
    public static JedisPool getJedisPoolInstance()
    {
        if (jedisPool == null)
        {
            synchronized (JedisPoolUtil.class)
            {
                if (jedisPool == null) {

                    JedisPoolConfig poolConfig = new JedisPoolConfig();
                    poolConfig.setMaxTotal(redisConfig.getMaxTotal());           // 最大连接数
                    poolConfig.setMaxIdle(redisConfig.getMaxIdle());              // 最大空闲连接数
                    poolConfig.setMaxWaitMillis(redisConfig.getMaxWaitMillis());  // 最大等待时间
                    poolConfig.setTestOnBorrow(true);       // 检查连接可用性, 确保获取的redis实例可用

                    jedisPool = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort());
                }
            }
        }

        return jedisPool;
    }

    /**
     * 2、从连接池中获取一个 Jedis 实例（连接）
     *
     * @return Jedis 实例
     */
    public static Jedis getJedisInstance() {

        return getJedisPoolInstance().getResource();
    }

    /**
     * 3、将Jedis对象（连接）归还连接池
     *
     * @param jedis     连接对象
     */
    public static void releaseJeids(Jedis jedis) {

        if (jedis != null) {
            jedis.close();
        }
    }

}
