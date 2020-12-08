export class Division {
    static entityName : string = "Divisão";

    nrodivisao : number | null = null;
    codigoG? : string | null = null;
    numBaixasD? : number | null = null;
    barcos? : number | null = null;
    tanques? : number | null = null;
    avioes? : number | null = null;
    homens? : number | null = null;

    constructor(
        nrodivisao : number,
        codigoG? : string,
        numBaixasD? : number,
        barcos? : number,
        tanques? : number,
        avioes? : number,
        homens? : number
    ) {
        this.nrodivisao = nrodivisao;
        this.codigoG = codigoG;
        this.numBaixasD = numBaixasD;
        this.barcos = barcos;
        this.tanques = tanques;
        this.avioes = avioes;
        this.homens = homens;
    }

    static getKeys() {
        return Object.keys(new Division(0));
    }

    static getInstance() {
        return new Division(0, "");
    }
}

export class MilitaryChief {
    static entityName : string = "Chefe Militar";

    codigochef : string | null = null;
    faixa? : string | null = null;
    nrodivisao? : number | null = null;
    nomel? : number | null = null;
    codigog? : number | null = null;

    constructor(
        codigochef : string,
        faixa? : string,
        nrodivisao? : number,
        nomel? : number,
        codigog? : number
    ) {
        this.codigochef  = codigochef;
        this.faixa = faixa;
        this.nrodivisao = nrodivisao
        this.nomel = nomel;
        this.codigog = codigog;
    }

    static getKeys() {
        return Object.keys(new MilitaryChief(""));
    }

    static getInstance() {
        return new MilitaryChief("");
    }
}

export class MilitaryGroup {
    static entityName : string = "Grupo Armado";

    codigog : string | null = null;
    nomegrupo? : string | null = null;
    numbaixasg? : number | null = null;

    constructor(
        codigog : string,
        nomegrupo? : string,
        numbaixasg? : number
    ) {
        this.codigog  = codigog;
        this.nomegrupo = nomegrupo;
        this.numbaixasg = numbaixasg;
    }

    static getKeys() {
        return Object.keys(new MilitaryGroup(""));
    }

    static getInstance() {
        return new MilitaryGroup("");
    }
}

export class PoliticalLeader {
    static entityName : string = "Líder Político";

    nomel : string | null = null;
    codigog? : string | null = null;
    apoios? : string[] | null = null;

    constructor(
        nomel : string,
        codigog? : string,
        apoios? : string[]
    ) {
        this.nomel  = nomel;
        this.codigog = codigog;
        this.apoios = apoios;
    }

    static getKeys() {
        return Object.keys(new PoliticalLeader(""));
    }

    static getInstance() {
        return new PoliticalLeader("");
    }
}

export class WarConflict {
    static entityName : string = "Conflito Armado";

    codconflito : string | null = null;
    nome? : string | null = null;
    tipoconf? : string | null = null;
    numferidos? : string | null = null;
    nummortos? : string | null = null;

    constructor(
        codconflito : string,
        nome? : string,
        tipoconf? : string,
        numferidos? : string,
        nummortos? : string
    ) {
        this.codconflito  = codconflito;
        this.nome = nome;
        this.tipoconf = tipoconf;
        this.numferidos = numferidos;
        this.nummortos = nummortos;
    }

    static getKeys() {
        return Object.keys(new WarConflict(""));
    }

    static getInstance() {
        return new WarConflict("");
    }
}

export interface GetResponse {
    success : boolean;
    details : string;
    items : Object[];
}

export interface GenericResponse {
    success : boolean;
    details : string;
}