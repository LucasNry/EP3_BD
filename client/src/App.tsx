import React, { useState } from 'react';
import './App.css';
import { Tab } from './Tab';
import { ArmsDealInfo } from './Tabs/ArmsDealInfo';
import { Cadastro } from './Tabs/Cadastro';
import { Graph } from './Tabs/Graph';
import { Top5Conflicts } from './Tabs/Top5Conflicts';
import { Top5Countries } from './Tabs/Top5Countries';
import { Top5MilitaryGroups } from './Tabs/Top5MilitaryGroups';
import { Top5Organizations } from './Tabs/Top5Organizations';

interface tabMap {
  [tabName : string] : any
}

function App() {
  const tabs : string[] = ["Cadastro", "Gráfico", "Listagem de Armas", "Top 5 Conflitos", "Top 5 Organizações", "Top 5 Grupos Armados", "Top País"]
  const tabMap : tabMap = {
    "Cadastro": <Cadastro/>,
    "Gráfico": <Graph/>,
    "Listagem de Armas": <ArmsDealInfo/>,
    "Top 5 Conflitos": <Top5Conflicts/>,
    "Top 5 Organizações": <Top5Organizations/>,
    "Top 5 Grupos Armados": <Top5MilitaryGroups/>,
    "Top País": <Top5Countries/>
  }
  const [selectedTab, setTab] : any = useState<string | null>(tabs[0]);
  return (
    <div className="App">
      <div className="TabContainer">
        <div className="TabHeader">
          {
            tabs.map(tab => <Tab key={tab} tabName={tab} onClick={setTab}/>)
          }
        </div>
        {
          tabMap[selectedTab]
        }
      </div>
    </div>
  );
}

export default App;
