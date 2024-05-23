package com.brokenhead.model.request;

import com.brokenhead.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.random.RandomGenerator;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Setter
@Getter
@Builder
public class PostRequest {

    private Integer userId;
    private String title;
    private String body;

    @JsonIgnore
    public static PostRequest buildGenericPostRequest() {
        return PostRequest.builder()
                .userId(RandomGenerator.getDefault().nextInt(1, 11))
                .title(randomAlphanumeric(10))
                .body("Test-body " + randomAlphanumeric(10))
                .build();
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this);
    }
}
