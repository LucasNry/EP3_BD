import React from "react";
import { ConflictCountry, Supplies } from "../models/apiModels";
import ApiClient from "../utilities/ApiClient";

interface GetConflictCountryResponse {
    details: string;
    items : Array<ConflictCountry>;
    success: boolean;
}

interface GetSuppliesResponse {
    details: string;
    items : Array<Supplies>;
    success: boolean;
}

interface IProps {
}

interface IState {
    countries: Array<string>;
    numberOfConflicts : Array<number>;
    nOfRows : number;
}

export class Top5Countries extends React.Component<IProps, IState> {
    apiClient : ApiClient;

    constructor(props : any) {
        super(props);
        this.apiClient = new ApiClient();
        this.state = {
            countries: [],
            numberOfConflicts: [],
            nOfRows: 0
        }
    }

    async fetchCountryInfo() {
        let endpoint = "conflictcountry/query";
        let requestParams = {
            query: "select pais, COUNT(pais) from conflitopais where codconflito in (select codconflito from conflito where tipoconf='Religioso') group by pais order by COUNT(pais) desc limit 1;"
        };
    
        let response : GetConflictCountryResponse = await this.apiClient.getFromQuery(endpoint, requestParams);
        let countries : Array<string> = response.items.map((countryconflict) => countryconflict.pais || "");
        let numberOfConflicts : Array<number> = await this.getNumberOfConflicts(countries);
        let nOfRows : number = countries.length;
        
        this.setState({
            countries,
            numberOfConflicts,
            nOfRows
        })
    }

    async getNumberOfConflicts(countries : Array<string>) : Promise<Array<number>> {
        let result : Array<number> = [];
        let endpoint = "conflictcountry/query";
        for (let i = 0; i < countries.length; i++) {
            let requestParams = {
                query: `select * from conflitopais where codconflito in (select codconflito from conflito where tipoconf='Religioso') and pais='${countries[i]}';`
            };
            let response: GetConflictCountryResponse = await this.apiClient.getFromQuery(endpoint, requestParams);
            result.push(response.items.length);
        }

        return result;
    }

    render() {
        let rowArray : Array<number> = [];
        for (let i = 0; i < this.state.nOfRows; i++) {
            rowArray.push(i);
        }

        return (
            <div className="Cadastro">
                <p>Listar o país e número de conflitos com maior número de conflitos religiosos.</p>
                <br/>
                {
                    this.state.countries.length > 0 ? 
                    <table>
                        <tr>
                            <th>País</th>
                            <th>Número de Conflitos</th>
                        </tr>
                        {
                            rowArray.map(
                                (rowNum) => {
                                    return (
                                        <tr>
                                            <td>{this.state.countries[rowNum]}</td>
                                            <td>{this.state.numberOfConflicts[rowNum]}</td>
                                        </tr>
                                    );
                                }
                            )
                        }
                    </table>
                    : null
                }
                <br/>
                <button className="botaoPadrao" onClick={() => this.fetchCountryInfo()}>Buscar</button>
            </div>
        );
    }
}