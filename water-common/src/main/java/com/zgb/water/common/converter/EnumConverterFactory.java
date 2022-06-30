package com.zgb.water.common.converter;

import com.zgb.water.common.base.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 枚举类参数转换工厂
 */
@SuppressWarnings("unchecked")
public class EnumConverterFactory implements ConverterFactory<String, BaseEnum> {

    private static final Map<Class, Converter> converterMap = new WeakHashMap<>();

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        Converter result = converterMap.get(targetType);
        if (result == null) {
            result = new StrToEnum<>(targetType);
            converterMap.put(targetType, result);
        }
        return result;
    }

    class StrToEnum<T extends BaseEnum> implements Converter<String, T> {
        private final Class<T> enumType;

        private Map<String, T> enumMap = new HashMap<>();

        StrToEnum(Class<T> enumType) {
            this.enumType = enumType;
            T[] enums = enumType.getEnumConstants();
            for (T e : enums) {
                enumMap.put(e.getValue().toString(), e);
            }
        }

        @Override
        public T convert(@Nullable String source) {
            T result = enumMap.get(source);
            if (result == null) {
                throw new IllegalArgumentException("No element matches " + source);
            }
            return result;
        }
    }
}
 
