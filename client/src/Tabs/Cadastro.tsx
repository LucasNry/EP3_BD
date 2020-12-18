import { AxiosResponse } from "axios";
import React from "react";
import { FailedPostResult } from "../FailedPostResult";
import { Division, EnrollDivision, GenericResponse, MediatingOrganization, MediationStart, MilitaryChief, MilitaryGroup, PoliticalLeader, WarConflict } from "../models/apiModels";
import { SuccessPostResult } from "../SuccessPostResult";
import ApiClient from "../utilities/ApiClient";

interface IProps {
}

interface IState {
  selectedEntity?: any;
  gennericResponse?: GenericResponse | null;
}

export class Cadastro extends React.Component<IProps, IState> {
    apiClient: ApiClient;
    entities: Array<any>;
    inputs: Array<HTMLInputElement> = [];

    constructor(props : any) {
        super(props);
        this.apiClient = new ApiClient();
        this.entities = [Division, MilitaryChief, MilitaryGroup, PoliticalLeader, WarConflict, MediatingOrganization, MediationStart, EnrollDivision];
        this.state = {
            selectedEntity: this.entities[0],
            gennericResponse: null
        }
    }

    getEntityByName(entityName : string) {
        for( let i = 0; i < this.entities.length; i++) {
            if (entityName === this.entities[i].entityName) {
                return this.entities[i];
            }
        }

        return this.entities[0];
    }

    async assembleAndSend() {
        let requestObject: {[k:string]: any} = {};
        
        this.inputs.forEach(
            (input) => { if(input != null || input !== undefined) { requestObject[input?.id] = input?.value }}
        )

        const response : AxiosResponse<GenericResponse> = await this.apiClient.post(requestObject, this.getEntityByName(this.state.selectedEntity).getInstance());

        this.setState({
            ...this.state,
            gennericResponse: response.data,
        })
    }

    render() {
        this.inputs = [];

        return (
            <div className="Cadastro">
                {
                   this.state.gennericResponse != null ? this.state.gennericResponse.success ? <SuccessPostResult/> : <FailedPostResult errorMessage={this.state.gennericResponse?.details}/> : null
                }
                <br/><br/>
                <p>Permita cadastrar divisões dentro de um grupo militar, cadastre conflitos bélicos, grupos militares, líderes políticos e chefes militares</p>
                <br/>
                <select name="entitySelect" id="entityPicker" value={this.state.selectedEntity} onChange={(e) => this.setState({selectedEntity: e.target.value})}>
                    {
                        this.entities.map((entity) => <option key={entity.entityName} value={entity.entityName}>{entity.entityName}</option>)
                    }
                </select>
                <br/>
                <br/>
                <br/>
                {
                    this.getEntityByName(this.state.selectedEntity).getKeys().map((propertyName : string) => {
                    return <div><span className="propertyName">{propertyName}:</span> <input className="insertInput" type="text" ref={(input: HTMLInputElement) => this.inputs?.push(input)} id={propertyName} /></div>
                    })
                }
                <br/>
                <br/>
                <br/>
                <button className="botaoPadrao" onClick={() => this.assembleAndSend()}>Cadastrar</button>
            </div>
        );
    }
}