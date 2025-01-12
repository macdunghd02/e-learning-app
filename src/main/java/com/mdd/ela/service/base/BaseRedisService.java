package com.mdd.ela.service.base;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author dungmd
 * @created 1/10/2025 10:10 下午
 * @project e-learning-app
 */

public interface BaseRedisService {
    void set(String key, String value);

    void setTimeToLive(String key, long timeoutInDays);

    void hashSet(String key, String field, Object value);

    boolean hashExists(String key, String field);

    Object get(String key);

    public Map<String, Object> getField(String key);

    Object hashGet(String key, String field);

    List<Object> hashGetByFieldPrefix(String key, String filedPrefix);

    Set<String> getFieldPrefixes(String key);

    void delete(String key);

    void delete(String key, String field);

    void delete(String key, List<String> fields);
}
