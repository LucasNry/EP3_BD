package com.bdproject.backend.models.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class GetResponse<T> {
    private List<T> items;

    public GetResponse(@RequestBody List<T> items) {
        this.items = items;
    }
}
