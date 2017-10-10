import React from "react"
import SourceForm from "./SourceForm";

export default class SourceList extends React.Component {
    render() {
        return <div id="content-list">
            <h1 className="page-header">Sources</h1>
            <SourceForm/>
        </div>;
    }
}