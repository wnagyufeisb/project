package com.kong.userfilter;

import com.kong.center.service.util.RedisUtil;

import redis.clients.jedis.Jedis;

public class TestRedis {
	 static String constr = "118.193.95.90" ;  
     public static Jedis getRedis(){  
          Jedis jedis = new Jedis(constr) ;  
        return jedis ;
        
    }  
     public static void main(String[] args){  
    		System.out.println(RedisUtil.getJedis().get("be5f2961f5ded2d41a3526acdc062f35"));
    }  
}
