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
public class PoliticalLeader extends Table {

    private static final String TABLE_NAME = "liderpolitico";
    private static final String NOME_L = "nomel";
    private static final String CODIGO_G = "codigoG";
    private static final String APOIOS = "apoios";

    @PrimaryKey(name = NOME_L)
    private String nomeL;

    @PrimaryKey(name = CODIGO_G)
    private String codigoG;

    @Attribute(name = APOIOS)
    private String[] apoios;

    public PoliticalLeader(
            @JsonProperty(NOME_L) String nomeL,
            @JsonProperty(CODIGO_G) String codigoG,
            @JsonProperty(APOIOS) String[] apoios // Might need to work on a custom conversion
    ) {
        super(TABLE_NAME);
        this.nomeL = nomeL;
        this.codigoG = codigoG;
        this.apoios = apoios;
    }

    public PoliticalLeader() {
        super(TABLE_NAME);
    }
}
