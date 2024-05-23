package com.brokenhead.model.response;

import com.brokenhead.util.JsonUtils;
import lombok.Getter;

@Getter
public class PostResponse {

    private Integer userId;
    private Integer id;
    private String title;
    private String body;

    @Override
    public String toString() {
        return JsonUtils.serialize(this);
    }
}
