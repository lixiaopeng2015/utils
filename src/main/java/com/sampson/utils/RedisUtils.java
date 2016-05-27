package com.sampson.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created with IntelliJ IDEA
 * ProjectName: utils
 * CreateUser:  lixiaopeng
 * CreateTime : 2016/5/20 17:43
 * Email: lixiaopeng913@sina.com
 * ModifyUser: lixiaopeng
 * Class Description:
 * To change this template use File | Settings | File Template
 */
public class RedisUtils {
    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    private static Jedis jedis;
    private static JedisPool jedisPool;
    private static String redis_host;
    private static Integer redis_port;

    public RedisUtils(){
        initJedisPool();
        jedis = jedisPool.getResource();
    }

    private void initJedisPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(200);
        //最大空闲数
        jedisPoolConfig.setMaxIdle(20);
        //最小空闲数
        jedisPoolConfig.setMinIdle(5);
        //在空闲时检查有效性, 默认true
        jedisPoolConfig.setTestWhileIdle(true);
        //最大等待毫秒数
        jedisPoolConfig.setMaxWaitMillis(10001);
        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setMinEvictableIdleTimeMillis(1800000);
        //在borrow一个jedis实例时，是否提前进行validate操作
        jedisPoolConfig.setTestOnBorrow(true);
        //在return一个jedis实例时，是否提前进行validate操作
        jedisPoolConfig.setTestOnReturn(true);
        //是否启用jmx管理功能
        jedisPoolConfig.setJmxEnabled(true);
        //test idle 线程的时间间隔
        jedisPoolConfig.setNumTestsPerEvictionRun(60000);

        jedisPool = new JedisPool(jedisPoolConfig,redis_host,redis_port);
    }

    public synchronized static Jedis getJedis(){
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            }else{
                return null;
            }
        } catch (Exception e) {
            logger.error("acquire jedis encountered an exception...."+e.getMessage());
            return null;
        }
    }

    /**
     * 关闭jedis
     * @param jedis jedis实例
     * @param redisIP redis IP
     * @param redisPort redis Port
     */
    public static void closeJedis(Jedis jedis,String redisIP,int redisPort){
        if (null != jedis) {
            jedisPool.close();
        }
    }
}
