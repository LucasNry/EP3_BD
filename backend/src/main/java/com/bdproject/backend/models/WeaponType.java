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
public class WeaponType extends Table {

    private static final String TABLE_NAME = "tipoarma";
    private static final String NOME_ARMA = "nomearma";
    private static final String INDICADOR = "indicador";

    @PrimaryKey(name = NOME_ARMA)
    private String nomeArma;

    @Attribute(name = INDICADOR)
    private String indicador;

    public WeaponType(
            @JsonProperty(NOME_ARMA) String nomeArma,
            @JsonProperty(INDICADOR) String indicador
    ) {
        super(TABLE_NAME);
        this.nomeArma = nomeArma;
        this.indicador = indicador;
    }

    public WeaponType() {
        super(TABLE_NAME);
    }
}
