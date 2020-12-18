import React from "react";
import { MediatingOrganization, MediationStart } from "../models/apiModels";
import ApiClient from "../utilities/ApiClient";

interface GetMediationStartResponse {
    details: string;
    items : Array<MediationStart>;
    success: boolean;
}

interface GetMediatingOrganizationResponse {
    details: string;
    items : Array<MediatingOrganization>;
    success: boolean;
}

interface IProps {
}

interface IState {
    organizations: Array<MediatingOrganization>;
}

export class Top5Organizations extends React.Component<IProps, IState> {
    apiClient : ApiClient;

    constructor(props : any) {
        super(props);
        this.apiClient = new ApiClient();
        this.state = {
            organizations: []
        }
    }

    async fetchOrganizationInfo() {
        let endpoint = "mediationstart/query";
        let requestParams = {
            query: "select codigoorg, COUNT(codigoorg) from entradamed group by codigoorg order by 2 desc limit 5;"
        };
        let response : GetMediationStartResponse = await this.apiClient.getFromQuery(endpoint, requestParams);
        let items = response.items;
        
        let organizations = await this.getOrganizations(items);

        this.setState({
            organizations
        })
    }

    async getOrganizations(medStarts : Array<MediationStart>) : Promise<Array<MediatingOrganization>> {
        let organizations: Array<MediatingOrganization> = [];
        for (let i = 0; i < medStarts.length; i++) {
            let queryObject : MediatingOrganization = new MediatingOrganization(medStarts[i].codigoorg);
            let response: GetMediatingOrganizationResponse = await this.apiClient.get(queryObject);

            let mediatingOrganization : MediatingOrganization = response.items[0];
            organizations.push(mediatingOrganization);
        }

        return organizations;
    }

    render() {

        return (
            <div className="Cadastro">
                <p>Listar as 5 maiores organizações em número de mediações.</p>
                <br/>
                {
                    this.state.organizations.length > 0 ? 
                    <table>
                        <tr>
                            <th>Nome da Organização</th>
                        </tr>
                        {
                            this.state.organizations.map(
                                (organization) => {
                                    return (
                                        <tr>
                                            <td>{organization?.nomeorg}</td>
                                        </tr>
                                    );
                                }
                            )
                        }
                    </table>
                    : null
                }
                <br/>
                <button className="botaoPadrao" onClick={() => this.fetchOrganizationInfo()}>Buscar</button>
            </div>
        );
    }
}