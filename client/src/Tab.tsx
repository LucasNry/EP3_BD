import "./App.css"
import React from "react";

interface TabProps {
    tabName : string;
    onClick : Function;
}

export const Tab = ({tabName, onClick} : TabProps) => {
    return (
        <div className="Tab" onClick={() => onClick(tabName)}>
            <span>{tabName}</span>
        </div>
    );
}