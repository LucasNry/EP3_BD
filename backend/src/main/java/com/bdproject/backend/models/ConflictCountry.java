package com.bdproject.backend.models;

import com.bdproject.backend.annotations.PrimaryKey;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(value = {"tableName"}, ignoreUnknown = true)
@Getter
@Setter
public class ConflictCountry extends Table {

    private static final String TABLE_NAME = "conflitopais";
    private static final String COD_CONFLITO = "codconflito";
    private static final String PAIS = "pais";

    @PrimaryKey(name = COD_CONFLITO)
    private String codConflito;

    @PrimaryKey(name = PAIS)
    private String pais;

    public ConflictCountry(
            @JsonProperty(COD_CONFLITO) String codConflito,
            @JsonProperty(PAIS) String pais
    ) {
        super(TABLE_NAME);
        this.codConflito = codConflito;
        this.pais = pais;
    }

    public ConflictCountry() {
        super(TABLE_NAME);
    }
}
