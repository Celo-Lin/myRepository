package com.juan.adx.common.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson2.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;
import redis.clients.jedis.ZParams.Aggregate;
import redis.clients.jedis.params.SetParams;

public class RedisTemplate {

    private JedisPool jedisPool;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 操作Key的方法
     */
    public Keys KEYS;
    /**
     * 对存储结构为String类型的操作
     */
    public Strings STRINGS;
    /**
     * 对存储结构为List类型的操作
     */
    public Lists LISTS;
    /**
     * 对存储结构为Set类型的操作
     */
    public Sets SETS;
    /**
     * 对存储结构为HashMap类型的操作
     */
    public Hash HASH;
    /**
     * 对存储结构为Set(排序的)类型的操作
     */
    public SortSet SORTSET;
    
    /**
     * 统计UV数据
     */
    public HyperLogLog HYPERLOGLOG;

    public Lock LOCK;
    

    public void init() {
        KEYS = new Keys();
        STRINGS = new Strings();
        LISTS = new Lists();
        SETS = new Sets();
        SORTSET = new SortSet();
        HASH = new Hash();
        LOCK = new Lock();
        HYPERLOGLOG = new HyperLogLog();
    }

    public class Keys {

        private final Logger logger = LoggerFactory.getLogger(Keys.class);

        /**
         * 设置key的过期时间，以秒为单位
         *
         * @param key
         * @param seconds 时间,以秒为单位
         * @return 影响的记录数
         */
        public long expired(String key, int seconds) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                long count = jedis.expire(key, seconds);
                return count;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception expired [key=%s, seconds=%d] errorMsg: ", key, seconds);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return 0;
        }

        /**
         * 删除指定key，如果删除的key不存在，则直接忽略
         *
         * @param key
         * @return 被删除的keys的数量
         */
        public Long del(String key) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Long count = jedis.del(key);
                return count;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception del [key=%s] errorMsg: ", key);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return 0L;
        }

        /**
         * 删除指定key，如果删除的key不存在，则直接忽略
         *
         * @param key
         * @return 被删除的keys的数量
         */
        public Long del(byte[] key) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Long count = jedis.del(key);
                return count;
            } catch (Exception e) {
                String keyStr = new String(key);
                String errorInfo = String.format("redis Exception del byte [key=%s] errorMsg: ", keyStr);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return 0L;
        }

        public Boolean exists(String key) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Boolean ret = jedis.exists(key);
                return ret;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception exists [key=%s] errorMsg: ", key);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return false;
        }
        
        public Long ttl(String key) {
        	Jedis jedis = null;
        	try {
        		jedis = jedisPool.getResource();
        		Long ret = jedis.ttl(key);
        		return ret;
        	} catch (Exception e) {
        		String errorInfo = String.format("redis Exception ttl [key=%s] errorMsg: ", key);
        		logger.error(errorInfo, e);
        	} finally {
        		if (jedis != null) {
        			jedis.close();
        		}
        	}
        	return -2l;
        }

    }

    public class Strings {

        private final Logger logger = LoggerFactory.getLogger(Strings.class);

        /**
         * 根据key获取记录
         *
         * @param key
         * @return 值
         */
        public String get(String key) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                String value = jedis.get(key);
                return value;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception get [key=%s] errorMsg: ", key);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }

        /**
         * 根据key获取记录
         *
         * @param key
         * @return 值
         */
        public byte[] get(byte[] key) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                byte[] value = jedis.get(key);
                return value;
            } catch (Exception e) {
                String keyStr = new String(key);
                String errorInfo = String.format("redis Exception get byte [key=%s] errorMsg: ", keyStr);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }

        /**
         * 添加有过期时间的记录
         *
         * @param key
         * @param seconds 过期时间，以秒为单位
         * @param value
         * @return String 操作状态
         */
        public String setEx(String key, int seconds, String value) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                String str = jedis.setex(key, seconds, value);
                return str;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception setEx [key=%s, value=%s, seconds=%d] errorMsg: ", key, value, seconds);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return "fail";
        }

        /**
         * 添加有过期时间的记录
         *
         * @param key
         * @param value
         * @return String 操作状态
         *
        public String set(String key, String value) {
        Jedis jedis = null;
        try {
        jedis = jedisPool.getResource();
        String str = jedis.set(key, value);
        return str;
        } catch (Exception e) {
        String errorInfo = String.format("redis Exception set [key=%s, value=%s] errorMsg: ", key, value);
        logger.error(errorInfo, e);
        } finally {
        if(jedis != null){
        jedis.close();
        }
        }
        return null;
        }*/

        /**
         * 添加有过期时间的记录
         *
         * @param key
         * @param    seconds 过期时间，以秒为单位
         * @param value
         * @return String 操作状态
         */
        public String setEx(byte[] key, int seconds, byte[] value) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                String str = jedis.setex(key, seconds, value);
                return str;
            } catch (Exception e) {
                String keyStr = new String(key);
                String errorInfo = String.format("redis Exception setEx byte [key=%s, value=%s, seconds=%d]. errorMsg: ", keyStr, value, seconds);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }

        /**
         * 批量获取记录,如果指定的key不存在返回List的对应位置将是null
         *
         * @param  keys
         * @return List<String> 值得集合
         */
        public List<byte[]> mget(byte[]... keys) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                List<byte[]> str = jedis.mget(keys);
                return str;
            } catch (Exception e) {
                logger.error("redis Exception mget.", e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }

        /**
         * 批量存储记录
         *
         * @param  keysvalues 例:keysvalues="key1","value1","key2","value2";
         * @return String 状态码
         */
        public String mset(byte[]... keysvalues) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                String str = jedis.mset(keysvalues);
                return str;
            } catch (Exception e) {
                logger.error("redis Exception mset errorMsg:", e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }

        /**
         * 原子加1
         *
         * @param  key
         * @return Long 操作后的数
         */
        public Long incr(String key) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Long value = jedis.incr(key);
                return value;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception incr [key=%s] errorMsg: ", key);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }

        /**
         * 原子减1
         *
         * @param  key
         * @return Long 操作后的数
         */
        public Long decr(String key) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Long value = jedis.decr(key);
                return value;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception decr [key=%s] errorMsg: ", key);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }

        /**
         * 如果key不存在，就设置key对应字符串value。在这种情况下，该命令和SET一样。当key已经存在时，就不做任何操作。
         *
         * @param key
         * @param value
         * @return 返回值
         * <br>数字，只有以下两种值：
         * <br>1 如果key被set
         * <br>0 如果key没有被set
         */
        public Long setnx(String key, String value) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Long ret = jedis.setnx(key, value);
                return ret;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception setnx [key=%s, value=%s] errorMsg: ", key, value);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }
    }

    public class Lists {

        private final Logger logger = LoggerFactory.getLogger(Lists.class);

        public Long llen(String key) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Long ret = jedis.llen(key);
                return ret;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception llen [key=%s] errorMsg: ", key);
                logger.error(errorInfo, e);
            } finally {
            	if (jedis != null) {
            		jedis.close();
            	}
			}
            return 0L;
        }

        public Long rpush(String key, int seconds, String[] values) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Long ret = jedis.rpush(key, values);
                KEYS.expired(key, seconds);
                return ret;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception rpush seconds [key=%s] errorMsg: ", key);
                logger.error(errorInfo, e);
            } finally {
            	if (jedis != null) {
            		jedis.close();
            	}
			}
            return 0L;
        }

        public Long rpush(String key, String value) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Long ret = jedis.rpush(key, value);
                return ret;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception rpush [key=%s] errorMsg: ", key);
                logger.error(errorInfo, e);
            } finally {
            	if (jedis != null) {
            		jedis.close();
            	}
			}
            return 0L;
        }

        public Long lpush(String key, String value) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Long ret = jedis.lpush(key, value);
                return ret;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception lpush [key=%s] errorMsg: ", key);
                logger.error(errorInfo, e);
            } finally {
            	if (jedis != null) {
            		jedis.close();
            	}
			}
            return 0L;
        }

        public String rpop(String key) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                String value = jedis.rpop(key);
                return value;
            } catch (Exception e) {
                String keyStr = new String(key);
                String errorInfo = String.format("redis Exception rpop [key=%s] errorMsg: ", keyStr);
                logger.error(errorInfo, e);
            } finally {
            	if (jedis != null) {
            		jedis.close();
            	}
			}
            return null;
        }
        
        public String lpop(String key) {
        	Jedis jedis = null;
        	try {
        		jedis = jedisPool.getResource();
        		String value = jedis.lpop(key);
        		return value;
        	} catch (Exception e) {
        		String keyStr = new String(key);
        		String errorInfo = String.format("redis Exception lpop [key=%s] errorMsg: ", keyStr);
        		logger.error(errorInfo, e);
        	} finally {
            	if (jedis != null) {
            		jedis.close();
            	}
			}
        	return null;
        }

        public List<String> lrange(String key, int start, int end) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                List<String> ret = jedis.lrange(key, start, end);
                return ret;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception lrange [key=%s] errorMsg: ", key);
                logger.error(errorInfo, e);
            } finally {
            	if (jedis != null) {
            		jedis.close();
            	}
			}
            return null;
        }

        public String ltrim(String key, int start, int end) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                String ret = jedis.ltrim(key, Integer.valueOf(start).longValue(), Integer.valueOf(end).longValue());
                return ret;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception ltrim [key=%s] errorMsg: ", key);
                logger.error(errorInfo, e);
            } finally {
            	if (jedis != null) {
            		jedis.close();
            	}
			}
            return null;
        }


    }


    public class Sets {

        private final Logger logger = LoggerFactory.getLogger(Sets.class);

        /**
         * 向Set添加一条记录，如果member已存在返回0,否则返回1
         */
        public Long sadd(String key, String member) {
            Long s = 0L;
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                s = jedis.sadd(key, member);
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception sadd [key=%s, value=%s] errorMsg: ", key, member);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return s;
        }

        /**
         * 返回集合 key 的基数(集合中元素的数量)
         */
        public Long scard(String key) {
            Long s = 0L;
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                s = jedis.scard(key);
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception scard [key=%s, value=%s] errorMsg: ", key,key);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return s;
        }

        /**
         * 从集合中删除指定成员，成功返回1，成员不存在返回0
         */
        public Long srem(String key, String member) {

            Jedis jedis = null;
            Long s = 0L;
            try {
                jedis = jedisPool.getResource();
                s = jedis.srem(key, member);
                return s;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception srem [key=%s, value=%s] errorMsg: ", key, member);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }

            return s;
        }

        /**
         * 返回集合中的所有成员
         *
         * @param key
         * @return 成员集合
         */
        public Set<String> smembers(String key) {

            Jedis jedis = null;
            Set<String> set = null;
            try {
                jedis = jedisPool.getResource();
                set = jedis.smembers(key);
                return set;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception smembers [key=%s] errorMsg: ", key);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return set;
        }

        /**
         * 判断 member 元素是否集合 key 的成员
         *
         * @param  key
         * @param  member
         * @return 如果 member 元素是集合的成员，返回 true
         * 如果 member 元素是集合的成员，返回 false
         */
        public boolean sismember(String key, String member) {

            Jedis jedis = null;
            boolean ret = false;
            try {
                jedis = jedisPool.getResource();
                ret = jedis.sismember(key, member);
                return ret;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception sismember [key=%s, member=%s] errorMsg: ", key, member);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return ret;
        }
    }

    public class Hash {

        private final Logger logger = LoggerFactory.getLogger(Hash.class);

        /**
         * 以Map的形式返回hash中的存储和值
         *
         * @param  key
         * @return Map<Strinig, String>
         */
        public Map<String, String> hgetall(String key) {

            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Map<String, String> map = jedis.hgetAll(key);
                return map;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception hgetall [key=%s] errorMsg: ", key);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return new HashMap<String, String>();
        }

        /**
         * 添加对应关系，如果对应关系已存在，则覆盖
         *
         * @param  key
         * @param
         * @return 状态，成功返回OK
         */
        public String hmset(String key, Map<String, String> map, int expireTime) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                String s = jedis.hmset(key, map);
                jedis.expire(key, expireTime);
                return s;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception hmset [key=%s, value=%s] errorMsg: ", key, JSON.toJSONString(map));
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }

        /**
         * 返回hash中指定存储位置的值
         *
         * @param  key
         * @param  field 存储的名字
         * @return 存储对应的值
         */
        public String hget(String key, String field) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                String s = jedis.hget(key, field);
                return s;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception hget [key=%s, field=%s] errorMsg: ", key, field);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }

        /**
         * 同时将多个 field-value (域-值)对设置到哈希表 key 中
         * @param key
         * @param fields
         * @return
         */
        public List<String> hmget(String key, String... fields) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                return jedis.hmget(key, fields);
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception hmget [key=%s, fields=%s] errorMsg: ", key, fields);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }

        /**
         * 将哈希表 key 中的字段 field 的值设为 value 
         * @param key
         * @param field
         * @param value
         * @return
         */
        public Long hset(String key, String field, String value) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Long ret = jedis.hset(key, field, value);
                return ret;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception hset [key=%s, field=%s, value=%s] errorMsg: ", key, field, value);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return 0L;
        }

        /**
         * 只有在字段 field 不存在时，设置哈希表字段的值
         * @param key
         * @param field
         * @param value
         * @return
         */
        public Long hsetnx(String key, String field, String value) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Long ret = jedis.hsetnx(key, field, value);
                return ret;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception hsetnx [key=%s, field=%s, value=%s] errorMsg: ", key, field, value);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return 0L;
        }

        public Long hincrby(String key, String field, long value) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Long ret = jedis.hincrBy(key, field, value);
                return ret;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception hincrby [key=%s, field=%s, value=%s] errorMsg: ", key, field, value);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return 0L;
        }
    }

    public class SortSet {

        private final Logger logger = LoggerFactory.getLogger(SortSet.class);

        /**
         * 向集合中增加一条记录,如果这个值已存在，这个值对应的权重将被置为新的权重
         *
         * @param  key
         * @param  score 权重
         * @param  member 要加入的值，
         * @return 状态码 1成功，0已存在member的值
         */
        public long zadd(String key, double score, String member) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                long s = jedis.zadd(key, score, member);
                return s;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception zadd [key=%s, score=%d, member=%s] errorMsg: ", key, score, member);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return 0;
        }

        /**
         * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略
         *
         * @param  key
         * @param  members 要删除的成员，
         * @return 被成功移除的成员的数量，不包括被忽略的成员
         */
        public long zrem(String key, String... members) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                long s = jedis.zrem(key, members);
                return s;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception zrem [key=%s, members=%s] errorMsg: ", key, members);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return 0;
        }


        /**
         * 获取给定区间的元素，原始按照权重由高到低排序
         *
         * @param  key
         * @param     start
         * @param     end
         * @return Set<String>
         */
        public Set<String> zrevrange(String key, int start, int end) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Set<String> set = jedis.zrevrange(key, start, end);
                return set;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception zrevrange [key=%s, start=%d, end=%d] errorMsg: ", key, start, end);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return new HashSet<String>();
        }

        /**
         * 返回有序集key中，指定区间内的成员。其中成员的位置按score值递减(从大到小)来排列。 具有相同score值的成员按字典序的反序排列。
         *
         * @param key
         * @param start 开始位置
         * @param end   结束位置
         * @return ${Set<Tuple>}
         */
        public Set<Tuple> zrevrangeWithScores(String key, int start, int end) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Set<Tuple> set = jedis.zrevrangeWithScores(key, start, end);
                return set;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception zrevrangeWithScores [key=%s, start=%d, end=%d] errorMsg: ", key, start, end);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }

        /**
         * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score 值递减(从大到小)的次序排列
         *
         * @param key
         * @param max 最高 score
         * @param min 最低 score
         * @return ${Set<Tuple>}
         */
        public Set<String> zrangebyscore(String key, String max, String min) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Set<String> set = jedis.zrangeByScore(key, min, max);
                return set;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception zrangebyscore [key=%s, max=%s, min=%s] errorMsg: ", key, max, min);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }

        /**
         * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score 值递减(从大到小)的次序排列
         *
         * @param key
         * @param max    最高 score
         * @param min    最低 score
         * @param offset 注意当 offset 很大时，定位 offset 的操作可能需要遍历整个有序集，此过程最坏复杂度为 O(N) 时间
         *               相当于mysql中 limit 函数的 start 参数
         * @param count  相当于mysql中 limit 函数的 count 参数
         * @return ${Set<Tuple>}
         */
        public Set<String> zrangebyscore(String key, int max, int min, int offset, int count) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Set<String> set = jedis.zrangeByScore(key, String.valueOf(min), String.valueOf(max), offset, count);
                return set;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception zrangebyscore [key=%s, max=%d, min=%d] errorMsg: ", key, max, min);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }

        /**
         * 计算给定的numkeys个有序集合的最大交集,并且把结果放到 dstkey中.
         *
         * @param dstkey
         * @param sets
         * @return
         */
        public long zinterstoreByAggregate(String dstkey, String... sets) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                ZParams zParams = new ZParams();
                zParams.aggregate(Aggregate.MAX);
                long len = jedis.zinterstore(dstkey, zParams, sets);
                return len;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception zinterstoreByAggregate [key=%s, sets=%s] errorMsg: ", dstkey, JSON.toJSONString(sets));
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return 0;
        }

        /**
         * 获取指定值在集合中的位置，集合排序从低到高
         *
         * @param  key
         * @param  member
         * @return long 位置
         * @see
         */
        public Long zrank(String key, String member) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Long index = jedis.zrank(key, member);
                return index;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception zrank [key=%s, member=%s] errorMsg: ", key, member);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return -1L;
        }

        /**
         * 获取指定值在集合中的位置，集合排序从高到低
         *
         * @param  key
         * @param  member
         * @return long 位置
         * @see
         */
        public Long zrevrank(String key, String member) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Long index = jedis.zrevrank(key, member);
                return index;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception zrevrank [key=%s, member=%s] errorMsg: ", key, member);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }

        /**
         * 获取集合中元素的数量
         *
         * @param  key
         * @return 如果返回0则集合不存在
         */
        public long zcard(String key) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                long len = jedis.zcard(key);
                return len;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception zrevrank [key=%s] errorMsg: ", key);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return 0;
        }

        /**
         * 获取指定成员在key中的值
         *
         * @param key
         * @param member
         * @return
         */
        public Double zscore(String key, String member) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Double score = jedis.zscore(key, member);
                return score;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception zscore [key=%s, member=%s] errorMsg: ", key, member);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }

    }
    
    public class HyperLogLog {
    	
    	private final Logger logger = LoggerFactory.getLogger(HyperLogLog.class);
    	
    	public Long pfadd(String key, String... elements) {
    		Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Long ret = jedis.pfadd(key, elements);
                return ret;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception pfadd [key=%s, elements=%s] errorMsg: ", key, JSON.toJSONString(elements));
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return 0L;
    	}
    	
    	public Long pfcount(String key) {
    		Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Long ret = jedis.pfcount(key);
                return ret;
            } catch (Exception e) {
                String errorInfo = String.format("redis Exception pfcount [key=%s] errorMsg: ", key);
                logger.error(errorInfo, e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return 0L;
    	}
    	
    	
    	
    	
    }

    public class Lock {

    	private final Long RELEASE_SUCCESS = 1L;
        private final String LOCK_SUCCESS = "OK";
        /* private final String SET_IF_NOT_EXIST = "NX"; */
        /* private final String SET_WITH_EXPIRE_TIME = "PX"; */

        private final Logger logger = LoggerFactory.getLogger(Lock.class);
        
        /**
         * 尝试获取分布式锁
         * @param key 锁
         * @param requestId 请求标识
         * @param expireTime 超期时间
         * @return 是否获取成功
         */
        public boolean tryGetDistributedLock(String key, String requestId, int expireTime) {
        	Jedis jedis = null;
            try {
                SetParams setParams = new SetParams().nx().px(expireTime);
                jedis = jedisPool.getResource();
                String ret = jedis.set(key, requestId, setParams);
                //String ret = jedis.set(key, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
                if (LOCK_SUCCESS.equalsIgnoreCase(ret)) {
                    return true;
                }
            } catch (Exception e) {
                logger.error("redis Exception releaseLock errorMsg: ", e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return false;

        }

        /**
         * 释放锁
         * @param lockKey 锁键
         * @param requestId 请求标识
         * @return
         */
        public boolean releaseLock(String lockKey, String requestId) {
        	Jedis jedis = null;
            try {
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                jedis = jedisPool.getResource();
                Object ret = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
                if (Objects.equals(RELEASE_SUCCESS, ret)) {
                    return true;
                }
            } catch (Exception e) {
                logger.error("redis Exception releaseLock errorMsg: ", e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return false;
        }
        

    }
}