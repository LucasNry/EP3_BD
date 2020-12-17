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
public class Supplies extends Table {

    private static final String TABLE_NAME = "fornece";
    private static final String CODIGO_G = "codigog";
    private static final String NOME_ARMA = "nomearma";
    private static final String NOME_TRAF = "nometraf";
    private static final String NUM_ARMAS = "numarmas";

    @PrimaryKey(name = CODIGO_G)
    private String codigoG;

    @PrimaryKey(name = NOME_ARMA)
    private String nomeArma;

    @PrimaryKey(name = NOME_TRAF)
    private String nomeTraf;

    @Attribute(name = NUM_ARMAS)
    private Integer numArmas;

    public Supplies(
            @JsonProperty(CODIGO_G) String codigoG,
            @JsonProperty(NOME_ARMA) String nomeArma,
            @JsonProperty(NOME_TRAF) String nomeTraf,
            @JsonProperty(NUM_ARMAS) Integer numArmas
    ) {
        super(TABLE_NAME);
        this.codigoG = codigoG;
        this.nomeArma = nomeArma;
        this.nomeTraf = nomeTraf;
        this.numArmas = numArmas;
    }

    public Supplies() {
        super(TABLE_NAME);
    }
}
