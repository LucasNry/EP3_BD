import React from "react";

interface IProps {
}

interface IState {
  selectedEntity?: any;
  success?: boolean | null;
}

export class SuccessPostResult extends React.Component<IProps, IState> {
    render() {

        return (
            <div className="SuccessResultTab">
                <span>Sucesso!</span>
            </div>
        );
    }
}