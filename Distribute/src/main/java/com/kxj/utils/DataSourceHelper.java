package com.kxj.utils;

import com.kxj.enums.DataSourceType;

/**
 * @author xiangjin.kong
 * @date 2021/1/25 18:04
 */
public final class DataSourceHelper {
    private static final ThreadLocal<DataSourceType> DATA_SOURCE_TYPE = new InheritableThreadLocal<>();

    public static void setDataSourceType(DataSourceType type) {
        DATA_SOURCE_TYPE.set(type);
    }

    public static DataSourceType getDataSourceType() {
        return DATA_SOURCE_TYPE.get();
    }

    public static void clear() {
        DATA_SOURCE_TYPE.remove();
    }
}
