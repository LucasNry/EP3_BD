import React from "react";
import { MilitaryGroup, Supplies, WarConflict } from "../models/apiModels";
import ApiClient from "../utilities/ApiClient";

interface GetMilitaryGroupResponse {
    details: string;
    items : Array<MilitaryGroup>;
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
    militaryGroups: Array<MilitaryGroup>;
}

export class Top5MilitaryGroups extends React.Component<IProps, IState> {
    apiClient : ApiClient;

    constructor(props : any) {
        super(props);
        this.apiClient = new ApiClient();
        this.state = {
            militaryGroups: []
        }
    }

    async fetchMilitaryGroupInfo() {
        let endpoint = "supplies/query";
        let requestParams = {
            query: "select codigog from fornece group by codigog order by SUM(numarmas) desc limit 5;"
        };
        let response : GetSuppliesResponse = await this.apiClient.getFromQuery(endpoint, requestParams);
        console.log(response)
        let militaryGroupCodes : Array<string> = response.items.map((supplies) => supplies.codigog || "");
        let militaryGroups : Array<MilitaryGroup> = await this.getGroups(militaryGroupCodes)
        
        this.setState({
            militaryGroups
        })
    }

    async getGroups(groupCodes : Array<string>) : Promise<Array<MilitaryGroup>> {
        let groups: Array<MilitaryGroup> = [];
        for (let i = 0; i < groupCodes.length; i++) {
            let queryObject : MilitaryGroup = new MilitaryGroup(groupCodes[i]);
            let response: GetMilitaryGroupResponse = await this.apiClient.get(queryObject);

            let militaryGroup : MilitaryGroup = response.items[0];
            groups.push(militaryGroup);
        }

        return groups;
    }

    render() {

        return (
            <div className="Cadastro">
                {
                    this.state.militaryGroups.length > 0 ? 
                    <table>
                        <tr>
                            <th>Nome do Grupo</th>
                        </tr>
                        {
                            this.state.militaryGroups.map(
                                (militaryGroup) => {
                                    return (
                                        <tr>
                                            <td>{militaryGroup.nomegrupo}</td>
                                        </tr>
                                    );
                                }
                            )
                        }
                    </table>
                    : null
                }
                <br/>
                <button className="botaoPadrao" onClick={() => this.fetchMilitaryGroupInfo()}>Buscar</button>
            </div>
        );
    }
}