package com.bdproject.backend.models;

import com.bdproject.backend.annotations.Attribute;
import com.bdproject.backend.annotations.PrimaryKey;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(value = {"tableName"}, ignoreUnknown = true)
@Getter
@Setter
public class MilitaryChief extends Table {

    private static final String TABLE_NAME = "chefemilitar";
    private static final String CODIGO_CHEF = "codigochef";
    private static final String FAIXA = "faixa";
    private static final String NRO_DIVISAO = "nrodivisao";
    private static final String NOME_L = "nomel";
    private static final String CODIGO_G = "codigog";

    @PrimaryKey(name = CODIGO_CHEF)
    private String codigoChef;

    @Attribute(name = FAIXA)
    private String faixa;

    @Attribute(name = NRO_DIVISAO)
    private String nroDivisao;

    @Attribute(name = NOME_L)
    private int nomeL;

    @Attribute(name = CODIGO_G)
    private int codigoG;

    public MilitaryChief(
            @JsonProperty(CODIGO_CHEF) String codigoChef,
            @JsonProperty(FAIXA) String faixa,
            @JsonProperty(NRO_DIVISAO) String nroDivisao,
            @JsonProperty(NOME_L) int nomeL,
            @JsonProperty(CODIGO_G) int codigoG
    ) {
        super(TABLE_NAME);
        this.codigoChef = codigoChef;
        this.faixa = faixa;
        this.nroDivisao = nroDivisao;
        this.nomeL = nomeL;
        this.codigoG = codigoG;
    }
}
