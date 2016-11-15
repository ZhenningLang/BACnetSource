package com.serotonin.bacnet4j.util;


/**
 * 接口 TimeSource 的实现
 * 唯一的方法是以毫秒返回当前时间
 * */
public class ClockTimeSource implements TimeSource {
    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
