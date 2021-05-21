package fr.ChadOW.api.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class JedisManager {
    private static JedisManager INSTANCE;
    private static final GsonBuilder builder = new GsonBuilder();
    private static Gson gson = JedisManager.builder.create();
    public JedisPool poolJedis;
    public String REDIS_PASSWORD;


    public JedisManager(String hostname, int port, String password) {
        this.REDIS_PASSWORD = password;

        initJedis(hostname, port);

        INSTANCE = this;
    }



    private void initJedis(String hostname, int port) {
        this.poolJedis = new JedisPool(hostname, port);
    }


    public static JedisManager getInstance() {
/* 35 */     return INSTANCE;
/*    */   }


    public boolean containKey(String key) {
        Jedis j = null;
        try {
            JedisManager jedisManager = JedisManager.getInstance();
            j = (jedisManager).poolJedis.getResource();
            j.auth((jedisManager).REDIS_PASSWORD);
            j.select(1);

            return j.exists(key);
        } finally {
            assert j != null;
            j.close();
        }
    }

    public Set<String> getKeys(String pattern) {

        Jedis j = null;
        try {
            JedisManager jedisManager = JedisManager.getInstance();
            j = (jedisManager).poolJedis.getResource();
            j.auth((jedisManager).REDIS_PASSWORD);
            j.select(1);

            Set<String> keys = j.keys(pattern);
            if (keys == null)
                return new HashSet<>();
            return keys;
        } finally {
            assert j != null;
            j.close();
        }
    }

    public String getValue(String key) {
        Jedis j = null;
        try {
            JedisManager jedisManager = JedisManager.getInstance();
            j = (jedisManager).poolJedis.getResource();
            j.auth((jedisManager).REDIS_PASSWORD);
            j.select(1);

            if (j.exists(key)) {
                return j.get(key);
            } else {
                return null;
            }
        } finally {
            assert j != null;
            j.close();
        }
    }

    public void setValue(String key, String value) {
        Jedis j = null;
        try {
            JedisManager jedisManager = JedisManager.getInstance();
            j = (jedisManager).poolJedis.getResource();
            j.auth((jedisManager).REDIS_PASSWORD);
            j.select(1);
            j.set(key, value);
        } finally {
            assert j != null;
            j.close();
        }
    }

    public void delValue(String key) {
        Jedis j = null;
        try {
            JedisManager jedisManager = JedisManager.getInstance();
            j = (jedisManager).poolJedis.getResource();
            j.auth((jedisManager).REDIS_PASSWORD);
            j.select(1);

            if (j.exists(key)) {
                j.del(key);
            }
        } finally {
            assert j != null;
            j.close();
        }
    }

    public static Gson getGson() {
        return gson;
    }
}