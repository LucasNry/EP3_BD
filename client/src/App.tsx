import React, { useState } from 'react';
import './App.css';
import { Tab } from './Tab';
import { Cadastro } from './Tabs/Cadastro';

interface tabMap {
  [tabName : string] : any
}

function App() {
  const tabs : string[] = ["Cadastro", "Cadastro", "Cadastro", "Cadastro", "Cadastro"]
  const tabMap : tabMap = {
    "Cadastro": <Cadastro/>
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
