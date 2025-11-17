package com.wisdomsky.dmp.library.common;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtility {

   private final StringRedisTemplate redisTemplate;

   public RedisUtility(StringRedisTemplate redisTemplate) {
      this.redisTemplate = redisTemplate;
   }

   public void set(String key, String value) {
      redisTemplate.opsForValue().set(key, value);
   }

   public void set(String key, String value, Long timeout) {
      redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
   }

   public long increase(String key) {
      Long result = redisTemplate.opsForValue().increment(key);
      return result != null ? result : 0L;
   }

   public String get(String key) {
      return redisTemplate.opsForValue().get(key);
   }

   public boolean expire(String key, Long ttl) {
      return Boolean.TRUE.equals(redisTemplate.expire(key, ttl, TimeUnit.SECONDS));
   }

   public boolean hasKey(String key) {
      return Boolean.TRUE.equals(redisTemplate.hasKey(key));
   }

   public void hashSet(String key, String field, String value) {
      redisTemplate.opsForHash().put(key, field, value);
   }

   public void hashSetMap(String key, Map<String, String> map) {
      redisTemplate.opsForHash().putAll(key, map);
   }

   public Object hashGet(String key, String field) {
      return redisTemplate.opsForHash().get(key, field);
   }

   public List<Object> hashMultiGet(String key, List<Object> fields) {
      return redisTemplate.opsForHash().multiGet(key, fields);
   }

   public Map<Object, Object> hashGetAll(String key) {
      return redisTemplate.opsForHash().entries(key);
   }

   public void hashDelete(String key, String field) {
      redisTemplate.opsForHash().delete(key, field);
   }

   public long hashIncrease(String key, String field, long delta) {
      return redisTemplate.opsForHash().increment(key, field, delta);
   }

   public Long setAdd(String key, String value) {
      Long result = redisTemplate.opsForSet().add(key, value);
      return result != null ? result : 0L;
   }

   public Long setRemove(String key, String value) {
      Long result = redisTemplate.opsForSet().remove(key, value);
      return result != null ? result : 0L;
   }

   public Set<String> setGetMembers(String key) {
      return redisTemplate.opsForSet().members(key);
   }

   public boolean setIsMember(String key, String member) {
      return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, member));
   }

   public long getSetSize(String key) {
      Long size = redisTemplate.opsForSet().size(key);
      return size != null ? size : 0L;
   }

   public Set<String> setDifference(String key1, String key2) {
      return redisTemplate.opsForSet().difference(key1, key2);
   }

   public Boolean zAdd(String key, String value, double score) {
      return Boolean.TRUE.equals(redisTemplate.opsForZSet().add(key, value, score));
   }

   public Double zScore(String key, String value) {
      return redisTemplate.opsForZSet().score(key, value);
   }

   public Boolean delete(String key) {
      return Boolean.TRUE.equals(redisTemplate.delete(key));
   }

   public Long delete(Collection<String> keys) {
      Long result = redisTemplate.delete(keys);
      return result != null ? result : 0L;
   }

   public void keyExpire(String key, long timeout) {
      redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
   }

   public void deleteAllKeys() {
      Set<String> keys = redisTemplate.keys("*");
      if (keys != null) {
         redisTemplate.delete(keys);
      }
   }

   public int keyCount() {
      Set<String> keys = redisTemplate.keys("*");
      return keys != null ? keys.size() : 0;
   }

   public int keyCount(String pattern) {
      Set<String> keys = redisTemplate.keys(pattern);
      return keys != null ? keys.size() : 0;
   }

   public Set<String> getKeySet(String pattern) {
      return redisTemplate.keys(pattern);
   }

   public void rename(String oldKey, String newKey) {
      redisTemplate.rename(oldKey, newKey);
   }
}