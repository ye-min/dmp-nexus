package com.wisdomsky.dmp.archive.common;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * 缓存操作类
 */
@Component
public class RedisUtility {
   @Autowired
   private StringRedisTemplate redisTemplate;

   // 维护一个本类的静态变量
   private static RedisUtility redisUtility;

   @PostConstruct
   public void init() {
      redisUtility = this;
      redisUtility.redisTemplate = this.redisTemplate;
   }

   /*
    * 将参数中的字符串值设置为键的值，不设置过期时间
    * 
    * @param key
    * 
    * @param value 必须要实现 Serializable 接口
    */
   public static void set(String key, String value) {
      redisUtility.redisTemplate.opsForValue().set(key, value);
   }

   /*
    * 将参数中的字符串值设置为键的值，设置过期时间
    * 
    * @param key
    * 
    * @param value 必须要实现 Serializable 接口
    * 
    * @param timeout
    */
   public static void set(String key, String value, Long timeout) {
      redisUtility.redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
   }

   public static Long increase(String key) {
      return redisUtility.redisTemplate.opsForValue().increment(key);
   }

   /*
    * 获取与指定键相关的值
    * 
    * @param key
    * 
    * @return
    */
   public static Object get(String key) {
      return redisUtility.redisTemplate.opsForValue().get(key);
   }

   /*
    * 设置某个键的过期时间
    * 
    * @param key 键值
    * 
    * @param ttl 过期秒数
    */
   public static boolean expire(String key, Long ttl) {
      return redisUtility.redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
   }

   /*
    * 判断某个键是否存在
    * 
    * @param key 键值
    */
   public static boolean hasKey(String key) {
      return redisUtility.redisTemplate.hasKey(key);
   }

   public static void hashSet(String key, String field, String value) {
      redisUtility.redisTemplate.opsForHash().put(key, field, value);
   }

   public static void hashSetMap(String key, Map<String, String> map) {
      redisUtility.redisTemplate.opsForHash().putAll(key, map);
   }

   public static Object hashGet(String key, String field) {
      return redisUtility.redisTemplate.opsForHash().get(key, field);
   }

   public static List<Object> hashMultiGet(String key, List<Object> field) {
      return redisUtility.redisTemplate.opsForHash().multiGet(key, field);
   }

   public static Map<Object, Object> hashGetAll(String key) {
      return redisUtility.redisTemplate.opsForHash().entries(key);
   }

   public static void hashDelete(String key, String field) {
      redisUtility.redisTemplate.opsForHash().delete(key, field);
   }

   public static long hashIncrease(String key, String field, long delta) {
      return redisUtility.redisTemplate.opsForHash().increment(key, field, delta);
   }

   /*
    * 向集合添加元素
    * 
    * @param key
    * 
    * @param value
    * 
    * @return 返回值为设置成功的value数
    */
   public static Long setAdd(String key, String value) {
      return redisUtility.redisTemplate.opsForSet().add(key, value);
   }

   public static Long setRemove(String key, String value) {
      return redisUtility.redisTemplate.opsForSet().remove(key, value);
   }

   /*
    * 获取集合中的某个元素
    * 
    * @param key
    * 
    * @return 返回值为redis中键值为key的value的Set集合
    */
   public static Set<String> setGetMembers(String key) {
      return redisUtility.redisTemplate.opsForSet().members(key);
   }

   // SISMEMBER
   public static Boolean setIsMember(String key, String member) {
      return redisUtility.redisTemplate.opsForSet().isMember(key, member);
   }

   public static Long getSetSize(String key) {
      return redisUtility.redisTemplate.opsForSet().size(key);
   }

   // SDIFF
   public static Set<String> setDifference(String key1, String key2) {
      return redisUtility.redisTemplate.opsForSet().difference(key1, key2);
   }

   /*
    * 将给定分数的指定成员添加到键中存储的排序集合中
    * 
    * @param key
    * 
    * @param value
    * 
    * @param score
    * 
    * @return
    */
   public static Boolean zAdd(String key, String value, double score) {
      return redisUtility.redisTemplate.opsForZSet().add(key, value, score);
   }

   /*
    * 返回指定排序集中给定成员的分数
    * 
    * @param key
    * 
    * @param value
    * 
    * @return
    */
   public static Double zScore(String key, String value) {
      return redisUtility.redisTemplate.opsForZSet().score(key, value);
   }

   /*
    * 删除指定的键
    * 
    * @param key
    * 
    * @return
    */
   public static Boolean delete(String key) {
      return redisUtility.redisTemplate.delete(key);
   }

   /*
    * 删除多个键
    * 
    * @param keys
    * 
    * @return
    */
   public static Long delete(Collection<String> keys) {
      return redisUtility.redisTemplate.delete(keys);
   }

   public static void keyExpire(String key, long timeout) {
      redisUtility.redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
   }

   // Only for unit test
   public static void deleteAllKey() {
      Set<String> keys = redisUtility.redisTemplate.keys("*");
      redisUtility.redisTemplate.delete(keys);
   }

   public static int keyCount() {
      Set<String> keys = redisUtility.redisTemplate.keys("*");
      return keys.size();
   }

   public static int keyCount(String patten) {
      Set<String> keys = redisUtility.redisTemplate.keys(patten);
      return keys.size();
   }

   public static Set<String> getKeySet(String patten) {
      Set<String> keySet = redisUtility.redisTemplate.keys(patten);
      return keySet;
   }

   public static void rename(String oldKey, String newKey) {
      redisUtility.redisTemplate.rename(oldKey, newKey);
   }
}