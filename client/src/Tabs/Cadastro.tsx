import React, { useState } from "react";
import { Division, MilitaryChief, MilitaryGroup, PoliticalLeader, WarConflict } from "../models/apiModels";
import ApiClient from "../utilities/ApiClient";

export const Cadastro = () => {

    const apiClient = new ApiClient();

    const getEntityByName = (entityName : string) => {
        for( let i = 0; i < entities.length; i++) {
            if (entityName === entities[i].entityName) {
                return entities[i];
            }
        }

        return entities[0];
    }

    const assembleAndSend = () => {
    }

    const entities = [Division, MilitaryChief, MilitaryGroup, PoliticalLeader, WarConflict];
    let [selectedEntity, setEntity] = useState(entities[0].entityName);

    return (
        <div className="Cadastro">
            <select name="entitySelect" id="entityPicker" value={selectedEntity} onChange={(e) => setEntity(e.target.value)}>
                {
                    entities.map((entity) => <option key={entity.entityName} value={entity.entityName}>{entity.entityName}</option>)
                }
            </select>
            <br/>
            <br/>
            <br/>
            {
                getEntityByName(selectedEntity).getKeys().map((propertyName) => {
                return <div><span className="propertyName">{propertyName}:</span> <input className="insertInput" type="text" id={propertyName} /></div>
                })
            }
            <br/>
            <br/>
            <br/>
            <button onClick={() => apiClient.get(new Division(1))}>Cadastrar</button>
        </div>
    );
}