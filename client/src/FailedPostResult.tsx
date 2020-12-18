import React from "react";

interface IProps {
    errorMessage : string | undefined;
}

interface IState {
  selectedEntity?: any;
  success?: boolean | null;
}

export class FailedPostResult extends React.Component<IProps, IState> {
    render() {

        return (
            <div className="FailedResultTab">
                <span>{this.props.errorMessage}</span>
            </div>
        );
    }
}