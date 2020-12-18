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
public class MediatingOrganization extends Table {

    private static final String TABLE_NAME = "organizacaom";
    private static final String CODIGO_ORG = "codigoorg";
    private static final String NOME_ORG = "nomeorg";
    private static final String TIPO = "tipo";
    private static final String TIPO_AJUDA = "tipoajuda";
    private static final String ORG_LIDER = "orglider";
    private static final String NUM_PESSOAS = "numpessoas";

    @PrimaryKey(name = CODIGO_ORG)
    private String codigoOrg;

    @Attribute(name = NOME_ORG)
    private String nomeOrg;

    @Attribute(name = TIPO)
    private String tipo;

    @Attribute(name = TIPO_AJUDA)
    private String tipoAjuda;

    @Attribute(name = ORG_LIDER)
    private String orgLider;

    @Attribute(name = NUM_PESSOAS)
    private Integer numPessoas;

    public MediatingOrganization(
            @JsonProperty(CODIGO_ORG) String codigoOrg,
            @JsonProperty(NOME_ORG) String nomeOrg,
            @JsonProperty(TIPO) String tipo,
            @JsonProperty(TIPO_AJUDA) String tipoAjuda,
            @JsonProperty(ORG_LIDER) String orgLider,
            @JsonProperty(NUM_PESSOAS) Integer numPessoas
    ) {
        super(TABLE_NAME);
        this.codigoOrg = codigoOrg;
        this.nomeOrg = nomeOrg;
        this.tipo = tipo;
        this.tipoAjuda = tipoAjuda;
        this.orgLider = orgLider;
        this.numPessoas = numPessoas;
    }

    public MediatingOrganization() {
        super(TABLE_NAME);
    }
}
