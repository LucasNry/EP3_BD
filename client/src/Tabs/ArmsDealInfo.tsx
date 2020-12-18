import React from "react";
import { MilitaryGroup, Supplies } from "../models/apiModels";
import ApiClient from "../utilities/ApiClient";

interface GetSuppliesResponse {
    details: string;
    items : Array<Supplies>;
    success: boolean;
}

interface GetMilitaryGroupResponse {
    details: string;
    items : Array<MilitaryGroup>;
    success: boolean;
}

interface IProps {
}

interface IState {
    nOfRows: number;
    weapons: Array<string>;
    dealers: Array<string>;
    groups: Array<string>;
}

export class ArmsDealInfo extends React.Component<IProps, IState> {
    apiClient : ApiClient;

    constructor(props : any) {
        super(props);
        this.apiClient = new ApiClient();
        this.state = {
            nOfRows: 0,
            weapons: [],
            dealers: [],
            groups: []
        }
    }

    async fetchArmsDealInfo() {
        let endpoint = "supplies/query";
        let requestParams = {
            query: "select * from fornece where nomearma='Barret M82' or nomearma='M200 intervention'"
        };
        let response : GetSuppliesResponse = await this.apiClient.getFromQuery(endpoint, requestParams);
        let items = response.items
        
        let weapons = items.map((item) => item.nomearma);
        let dealers = items.map((item) => item.nometraf);
        let nOfRows = dealers.length;
        let groupCodes = items.map((item) => item.codigog);
        let groups : Array<string> = await this.getGroups(groupCodes);
        
        this.setState({
            nOfRows,
            weapons,
            dealers,
            groups
        })
    }

    async getGroups(groupCodes : Array<string>) : Promise<Array<string>> {
        let groupNames: Array<string> = [];
        for (let i = 0; i < groupCodes.length; i++) {
            let queryObject : MilitaryGroup = new MilitaryGroup(groupCodes[i]);
            let response: GetMilitaryGroupResponse = await this.apiClient.get(queryObject);

            let groupName : string = response.items[0].nomegrupo || "";
            groupNames.push(groupName);
        }

        return groupNames;
    }

    render() {
        let rowArray : Array<number> = [];
        for (let i = 0; i < this.state.nOfRows; i++) {
            rowArray.push(i);
        }

        return (
            <div className="Cadastro">
                <span>Listar os traficantes e os grupos armadospara os quais os traficantes fornecem armas “Barret M82” ou “M200 intervention”.</span>
                <br/><br/><br/>
                {
                    this.state.dealers.length > 0 && this.state.groups.length > 0 ? 
                    <table>
                        <tr>
                            <th>De</th>
                            <th>Para</th>
                            <th>Item Fornecido</th>
                        </tr>
                        {
                            rowArray.map(
                                (rowNum) => {
                                    return (
                                        <tr>
                                            <td>{this.state.dealers[rowNum]}</td>
                                            <td>{this.state.groups[rowNum]}</td>
                                            <td>{this.state.weapons[rowNum]}</td>
                                        </tr>
                                    );
                                }
                            )
                        }
                    </table>
                    : null
                }
                <br/><br/><br/>
                <button className="botaoPadrao" onClick={() => this.fetchArmsDealInfo()}>Buscar</button>
            </div>
        );
    }
}