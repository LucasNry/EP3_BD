package com.bdproject.backend.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollDivisionRequest {
    private static final String NRO_DIVISAO = "nrodivisao";
    private static final String CODIGO_G = "codigog";

    private Integer nroDivisao;

    private String codigoG;

    public EnrollDivisionRequest(
            @JsonProperty(NRO_DIVISAO) Integer nroDivisao,
            @JsonProperty(CODIGO_G) String codigoG
    ) {
        this.nroDivisao = nroDivisao;
        this.codigoG = codigoG;
    }
}
