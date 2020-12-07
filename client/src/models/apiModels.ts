export default interface GetDivisionRequest {
    nrodivisao : number;
    codigog?: string;
}

export default interface GetMilitaryChiefRequest {
    codigochef : string;
}

export default interface GetMilitaryGroup {
    codigog? : string;
}

export default interface GetPoliticalLeaderRequest {
    nomel : string;
    codigog? : string;
}

export default interface GetWarConflict {
    codConflito : string;
}