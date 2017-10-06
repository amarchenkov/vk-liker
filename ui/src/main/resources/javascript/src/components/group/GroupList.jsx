import React from "react"
import rest from "rest"
import mime from "rest/interceptor/mime"
import GroupForm from "./GroupForm";

export default class GroupList extends React.Component {

    constructor() {
        super();
        this.state = {"groups": []}
    }

    componentDidMount() {
        const client = rest.wrap(mime);
        const self = this;
        client({
            path: 'http://localhost:8095/group',
            method: 'GET',
            headers: {'Content-Type': 'application/json'}
        }).then(function (response) {
            self.setState({"groups": response.entity});
        });
    }

    onUpdate() {
        this.forceUpdate();
    }

    render() {
        return <div id='group-list' className='table-responsive'>
            <h1 className="page-header">Groups</h1>
            <GroupForm onUpdate={this.onUpdate.bind(this)}/>
            <table className='table table-striped'>
                <thead>
                <tr>
                    <th>#</th>
                    <th>ID</th>
                    <th>Group Name</th>
                    <th>Group ID</th>
                </tr>
                </thead>
                <tbody>
                {this.state.groups.map(function (group, index) {
                    return <tr key={index}>
                        <td>{index + 1}</td>
                        <td>{group.id}</td>
                        <td>{group.group_name}</td>
                        <td>{group.group_id}</td>
                    </tr>;
                })}
                </tbody>
            </table>
        </div>;
    }
}