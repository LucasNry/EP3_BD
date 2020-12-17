import React from "react";
import { Division, MilitaryChief, MilitaryGroup, PoliticalLeader, WarConflict } from "../models/apiModels";
import ApiClient from "../utilities/ApiClient";

interface IProps {
}

interface IState {
  selectedEntity?: any;
}

export class Cadastro extends React.Component<IProps, IState> {
    apiClient: ApiClient;
    entities: Array<any>;
    inputs: Array<HTMLInputElement> = [];

    constructor(props : any) {
        super(props)
        this.apiClient = new ApiClient();
        this.entities = [Division, MilitaryChief, MilitaryGroup, PoliticalLeader, WarConflict];
        this.state = {
            selectedEntity: this.entities[0],
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

    assembleAndSend() {
        let requestObject: {[k:string]: any} = {};
        
        this.inputs.forEach(
            (input) => { if(input != null || input !== undefined) { requestObject[input?.id] = input?.value }}
        )

        console.log(requestObject)

        this.apiClient.post(requestObject, this.getEntityByName(this.state.selectedEntity).getInstance())
    }

    render() {
        const entities = [Division, MilitaryChief, MilitaryGroup, PoliticalLeader, WarConflict];
        this.inputs = [];

        return (
            <div className="Cadastro">
                <select name="entitySelect" id="entityPicker" value={this.state.selectedEntity} onChange={(e) => this.setState({selectedEntity: e.target.value})}>
                    {
                        entities.map((entity) => <option key={entity.entityName} value={entity.entityName}>{entity.entityName}</option>)
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