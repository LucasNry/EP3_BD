package com.bdproject.backend.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PostResponse {
    private static final String SUCCESS = "success";

    private boolean success;

    public PostResponse(@JsonProperty(SUCCESS) boolean success) {
        this.success = success;
    }
}
