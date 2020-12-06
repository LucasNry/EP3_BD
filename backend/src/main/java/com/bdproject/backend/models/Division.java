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
    private String nroDivisao;

    @PrimaryKey(name = CODIGO_G)
    private String codigoG;

    @Attribute(name = NUM_BAIXAS_D)
    private String numBaixasD;

    @Attribute(name = BARCOS)
    private int barcos;

    @Attribute(name = TANQUES)
    private int tanques;

    @Attribute(name = AVIOES)
    private int avioes;

    @Attribute(name = HOMENS)
    private int homens;

    public Division(
            @JsonProperty(NRO_DIVISAO) String nroDivisao,
            @JsonProperty(CODIGO_G) String codigoG,
            @JsonProperty(NUM_BAIXAS_D) String numBaixasD,
            @JsonProperty(BARCOS) int barcos,
            @JsonProperty(TANQUES) int tanques,
            @JsonProperty(AVIOES) int avioes,
            @JsonProperty(HOMENS) int homens
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
}
