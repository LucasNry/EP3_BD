package com.bdproject.backend.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class GenericResponse {
    protected static final String SUCCESS = "success";
    protected static final String DETAILS = "details";

    private boolean success;

    private String details;

    public GenericResponse(
            @JsonProperty(SUCCESS) boolean success
    ) {
        this.success = success;
    }

    public GenericResponse(
            @JsonProperty(SUCCESS) boolean success,
            @JsonProperty(DETAILS) String details
    ) {
        this.success = success;
        this.details = details;
    }
}
