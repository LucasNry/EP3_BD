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
public class WarConflict extends Table {

    private static final String TABLE_NAME = "conflito";
    private static final String COD_CONFLITO = "codconflito";
    private static final String NOME = "nome";
    private static final String TIPO_CONF = "tipoconf";
    private static final String NUM_FERIDOS = "numferidos";
    private static final String NUM_MORTOS = "nummortos";

    @PrimaryKey(name = COD_CONFLITO)
    private String codConflito;

    @Attribute(name = NOME)
    private String nome;

    @Attribute(name = TIPO_CONF)
    private String tipoConf;

    @PrimaryKey(name = NUM_FERIDOS)
    private String numFeridos;

    @PrimaryKey(name = NUM_MORTOS)
    private String numMortos;

    public WarConflict(
            @JsonProperty(COD_CONFLITO) String codConflito,
            @JsonProperty(NOME) String nome,
            @JsonProperty(TIPO_CONF) String tipoConf,
            @JsonProperty(NUM_FERIDOS) String numFeridos,
            @JsonProperty(NUM_MORTOS) String numMortos
    ) {
        super(TABLE_NAME);
        this.codConflito = codConflito;
        this.nome = nome;
        this.tipoConf = tipoConf;
        this.numFeridos = numFeridos;
        this.numMortos = numMortos;
    }
}
