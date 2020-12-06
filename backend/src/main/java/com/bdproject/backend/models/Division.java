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
public class Division extends Table{

    private static final String TABLE_NAME = "divisao";
    private static final String NRO_DIVISAO = "nrodivisao";
    private static final String CODIGO_G = "codigog";
    private static final String NUM_BAIXAS_D = "numbaixasd";
    private static final String BARCOS = "barcos";
    private static final String TANQUES = "tanques";
    private static final String AVIOES = "avioes";
    private static final String HOMENS = "homens";

    @PrimaryKey(name = NRO_DIVISAO)
    private Integer nroDivisao;

    @PrimaryKey(name = CODIGO_G)
    private String codigoG;

    @Attribute(name = NUM_BAIXAS_D)
    private Integer numBaixasD;

    @Attribute(name = BARCOS)
    private Integer barcos;

    @Attribute(name = TANQUES)
    private Integer tanques;

    @Attribute(name = AVIOES)
    private Integer avioes;

    @Attribute(name = HOMENS)
    private Integer homens;

    public Division(
            @JsonProperty(NRO_DIVISAO) Integer nroDivisao,
            @JsonProperty(CODIGO_G) String codigoG,
            @JsonProperty(NUM_BAIXAS_D) Integer numBaixasD,
            @JsonProperty(BARCOS) Integer barcos,
            @JsonProperty(TANQUES) Integer tanques,
            @JsonProperty(AVIOES) Integer avioes,
            @JsonProperty(HOMENS) Integer homens
    ) {
        super(TABLE_NAME);
        this.nroDivisao = nroDivisao;
        this.codigoG = codigoG;
        this.numBaixasD = numBaixasD;
        this.barcos = barcos;
        this.tanques = tanques;
        this.avioes = avioes;
        this.homens = homens;
    }

    public Division() {
        super(TABLE_NAME);
    }
}
