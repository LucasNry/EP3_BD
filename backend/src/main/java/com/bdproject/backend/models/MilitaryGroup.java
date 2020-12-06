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
public class MilitaryGroup extends Table {

    private static final String TABLE_NAME = "grupoarmado";
    private static final String CODIGO_G = "codigog";
    private static final String NOME_GRUPO = "nomegrupo";
    private static final String NUM_BAIXAS_G = "numbaixasg";

    @PrimaryKey(name = CODIGO_G)
    private String codigoG;

    @Attribute(name = NOME_GRUPO)
    private String nomeGrupo;

    @Attribute(name = NUM_BAIXAS_G)
    private int numBaixasG;

    public MilitaryGroup(
            @JsonProperty(CODIGO_G) String codigoG,
            @JsonProperty(NOME_GRUPO) String nomeGrupo,
            @JsonProperty(NUM_BAIXAS_G) int numBaixasG
    ) {
        super(TABLE_NAME);
        this.codigoG = codigoG;
        this.nomeGrupo = nomeGrupo;
        this.numBaixasG = numBaixasG;
    }
}
