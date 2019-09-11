package com.taobao.taobaoadmin.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackson json序列化和反序列化工具类
 */
public class JsonUtils {
    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     */
    public static String objectToJson(Object data) {
        try {
            String str = MAPPER.writeValueAsString(data);
            return str;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
