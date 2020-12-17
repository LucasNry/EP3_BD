import React from "react";
import { WarConflict } from "../models/apiModels";
import ApiClient from "../utilities/ApiClient";

interface GetWarConflictResponse {
    details: string;
    items : Array<WarConflict>;
    success: boolean;
}

interface IProps {
}

interface IState {
    conflicts: Array<WarConflict>;
}

export class Top5Conflicts extends React.Component<IProps, IState> {
    apiClient : ApiClient;

    constructor(props : any) {
        super(props);
        this.apiClient = new ApiClient();
        this.state = {
            conflicts: []
        }
    }

    async fetchConflictInfo() {
        let endpoint = "warconflict/query";
        let requestParams = {
            query: "select codconflito, nome, tipoconf, numferidos, MAX(nummortos) from conflito group by codconflito order by MAX(nummortos) desc limit 5;"
        };
        let response : GetWarConflictResponse = await this.apiClient.getFromQuery(endpoint, requestParams);
        let conflicts : Array<WarConflict> = response.items;
        this.setState({
            conflicts
        })
    }

    render() {

        return (
            <div className="Cadastro">
                {
                    this.state.conflicts.length > 0 ? 
                    <table>
                        <tr>
                            <th>Conflito</th>
                            <th>No de Mortos</th>
                        </tr>
                        {
                            this.state.conflicts.map(
                                (conflict) => {
                                    return (
                                        <tr>
                                            <td>{conflict.nome}</td>
                                            <td>{conflict.nummortos}</td>
                                        </tr>
                                    );
                                }
                            )
                        }
                    </table>
                    : null
                }
                <br/>
                <button className="botaoPadrao" onClick={() => this.fetchConflictInfo()}>Buscar</button>
            </div>
        );
    }
}