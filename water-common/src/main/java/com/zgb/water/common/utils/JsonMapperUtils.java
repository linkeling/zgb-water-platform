package com.zgb.water.common.utils;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class JsonMapperUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(JsonMapper.class);

    static {
        //忽略在json字符串中存在的属性，但是在javaBean中不存在的属性。
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }

    public static <T> String obj2String(T src) {
        if (src == null) {
            return null;
        }
        try {
            return src instanceof String ? (String)src : objectMapper.writeValueAsString(src);
        } catch (Exception e) {
            logger.warn("parse object to string exception(转换对象为字符串发生异常), ", e);
            //这里返回为null 而不是抛出异常是为了调用端考虑，这里返回空，调用端可以同步判断是否为空，做进一步操作，
            //如果直接抛出异常，那么调用端无法控制自己的逻辑往下进行。
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
        } catch (Exception e) {
            logger.warn("Parse String to Object exception(转换字符串为对象时发生异常)", e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, Class<?> collectionClass, Class<?>... elementClass) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClass);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (Exception e) {
            logger.warn("Parse String to Object exception(转换字符串为对象时发生异常)", e);
            return null;
        }
    }
}
