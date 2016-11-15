package com.serotonin.bacnet4j.util;

/**
 * 接口 TimeSource，唯一的方法是以毫秒返回当前时间
 * */
public interface TimeSource {
    long currentTimeMillis();
}
