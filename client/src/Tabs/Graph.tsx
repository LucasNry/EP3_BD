import React from "react";
import { WarConflict } from "../models/apiModels";
import ApiClient from "../utilities/ApiClient";
import Plot from 'react-plotly.js';

interface GetResponse {
    details: string;
    items : Array<WarConflict>;
    success: boolean;
}

interface IProps {
}

interface IState {
    names: Array<string>;
    values: Array<number>;
}

export class Graph extends React.Component<IProps, IState> {
    apiClient : ApiClient;

    constructor(props : any) {
        super(props);
        this.apiClient = new ApiClient();
        this.state = {
            names: [],
            values: []
        }
    }

    async plotGraph() {
        let response: GetResponse = await this.apiClient.get(WarConflict.getInstance());
        
        if (response.success) {
            let dataDict: Map<string | null | undefined, any> = new Map();
            let items: WarConflict[] = response.items;
            items.forEach(
                (item) => {
                    let currentCount = dataDict.get(item.tipoconf);
                    if (currentCount != null) {
                        dataDict.set(item.tipoconf, currentCount + 1);
                    } else {
                        dataDict.set(item.tipoconf, 1);
                    }
                }
            );

            let names = [];
            let values = [];

            let keyIter = dataDict.keys();
            let valueIter = dataDict.values();

            for (let i = 0; i < dataDict.size; i++) {
                names.push(keyIter.next().value);
                values.push(valueIter.next().value)
            }

            this.setState({
                names,
                values
            })
        }
    }

    render() {
        return (
            <div className="Cadastro">
                <div>
                    <p>Gerar um gráfico, histograma, por tipo de conflito e número de conflitos.</p>
                    <br/>
                    {
                        this.state.names.length > 0 && this.state.values.length > 0 ? <Plot 
                        data={[
                            {
                                x: this.state.names,
                                y: this.state.values,
                                type: 'bar'
                            }
                        ]} 
                        layout={{
                            width: 540, height: 350, title: 'Tipo de Conflito X No de Conflitos'
                        }}/> : null
                    }
                </div>
                <br/>
                <button className="botaoPadrao" onClick={() => this.plotGraph()}>Gerar gráfico</button>
            </div>
        );
    }
}