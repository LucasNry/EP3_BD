package com.bdproject.backend.models;

import com.bdproject.backend.annotations.PrimaryKey;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(value = {"tableName"}, ignoreUnknown = true)
@Getter
@Setter
public class MediationStart extends Table {

    private static final String TABLE_NAME = "entradamed";
    private static final String CODIGO_ORG = "codigoorg";
    private static final String COD_CONFLITO = "codconflito";
    private static final String DE_MEDIA = "demedia";

    @PrimaryKey(name = CODIGO_ORG)
    private String codigoOrg;

    @PrimaryKey(name = COD_CONFLITO)
    private String codConflito;

    @PrimaryKey(name = DE_MEDIA)
    private String deMedia;

    public MediationStart(
            @JsonProperty(CODIGO_ORG) String codigoOrg,
            @JsonProperty(COD_CONFLITO) String codConflito,
            @JsonProperty(DE_MEDIA) String deMedia
    ) {
        super(TABLE_NAME);
        this.codigoOrg = codigoOrg;
        this.codConflito = codConflito;
        this.deMedia = deMedia;
    }

    public MediationStart() {
        super(TABLE_NAME);
    }
}
