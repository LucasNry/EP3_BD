package com.bdproject.backend.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class GetResponse<T> extends GenericResponse {
    private static final String ITEMS = "items";
    private List<T> items;

    public GetResponse(
            @JsonProperty(SUCCESS) boolean success,
            @JsonProperty(ITEMS) List<T> items
    ) {
        super(success);
        this.items = items;
    }

    public GetResponse(
            @JsonProperty(SUCCESS) boolean success,
            @JsonProperty(DETAILS) String details,
            @JsonProperty(ITEMS) List<T> items
    ) {
        super(success, details);
        this.items = items;
    }
}
