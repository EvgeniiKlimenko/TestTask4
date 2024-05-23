package com.brokenhead.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class JsonUtils {


    @SneakyThrows
    public static <T> String serialize(T object) {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return mapper.writeValueAsString(object);
    }
}
