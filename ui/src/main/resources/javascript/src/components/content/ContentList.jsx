import React from "react"
import ContentForm from "./ContentForm";
import rest from "rest"
import mime from "rest/interceptor/mime"
import {NotificationManager} from "react-notifications"

export default class ContentList extends React.Component {
    constructor() {
        super();
        this.state = {"sources": []};
    }

    componentDidMount() {
        this.getSourceList();
    }

    getSourceList() {
        const client = rest.wrap(mime);
        const self = this;
        client({
            path: 'http://localhost:8095/content',
            method: 'GET',
            headers: {'Content-Type': 'application/json'}
        }).then(function (response) {
                if (response.status.code === 200) {
                    self.setState({"sources": response.entity});
                    NotificationManager.info("Content source list received", "Sources");
                } else {
                    NotificationManager.error("Getting source failed. " + response.error, "Sources");
                }
            },
            function (response) {
                NotificationManager.error("Getting source failed. " + response.error, "Sources");
            });
    }

    deleteSource(e, id, source_name) {
        e.preventDefault();
        const client = rest.wrap(mime);
        const self = this;
        client({
            path: 'http://localhost:8095/content/' + id,
            method: 'DELETE',
        }).then(function (response) {
            if (response.status.code === 204) {
                NotificationManager.success("Source \"" + source_name + "\" removed", "Sources");
                self.getSourceList()
            } else {
                NotificationManager.error("Remove source failed. " + response.error, "Sources");
            }
        });
    }

    onUpdate() {
        this.getSourceList();
    }

    render() {
        const self = this;
        return <div id='source-list' className='table-responsive'>
            <h1 className="page-header">Content Sources</h1>
            <ContentForm onUpdate={this.onUpdate.bind(this)}/>
            <table className='table table-striped'>
                <thead>
                <tr>
                    <th>&nbsp;</th>
                    <th>#</th>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Source ID</th>
                    <th>Source Type</th>
                    <th>Last check</th>
                </tr>
                </thead>
                <tbody>
                {this.state.sources.map(function (source, index) {
                    return <tr key={index}>
                        <td><a onClick={(e) => self.deleteSource(e, source.id, source.name)} href="#"><span
                            className="glyphicon glyphicon-trash">&nbsp;</span></a></td>
                        <td>{index + 1}</td>
                        <td>{source.id}</td>
                        <td>{source.name}</td>
                        <td>{source.source_id}</td>
                        <td>{source.source_type}</td>
                        <td>{source.last_check}</td>
                    </tr>;
                })}
                </tbody>
            </table>
        </div>;
    }
}