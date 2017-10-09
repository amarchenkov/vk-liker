import React from "react"
import rest from "rest"
import mime from "rest/interceptor/mime"
import GroupForm from "./GroupForm";
import {NotificationManager} from "react-notifications"

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
            if (response.status.code === 200) {
                self.setState({"groups": response.entity});
                NotificationManager.info("Group list received", "Groups");
            } else {
                NotificationManager.error("Getting account failed. " + response.error, "Groups");
            }
        });
    }

    deleteGroup(e, id, group_name) {
        e.preventDefault();
        const client = rest.wrap(mime);
        const self = this;
        client({
            path: 'http://localhost:8095/group/' + id,
            method: 'DELETE',
        }).then(function (response) {
            if (response.status.code === 204) {
                NotificationManager.success("Group \"" + group_name + "\" removed", "Groups");
            } else {
                NotificationManager.error("Remove group failed. " + response.error, "Groups");
            }
        });
    }

    onUpdate() {
        this.forceUpdate();
    }

    render() {
        const self = this;
        return <div id='group-list' className='table-responsive'>
            <h1 className="page-header">Groups</h1>
            <GroupForm onUpdate={this.onUpdate.bind(this)}/>
            <table className='table table-striped'>
                <thead>
                <tr>
                    <th>&nbsp;</th>
                    <th>#</th>
                    <th>ID</th>
                    <th>Group Name</th>
                    <th>Group ID</th>
                </tr>
                </thead>
                <tbody>
                {this.state.groups.map(function (group, index) {
                    return <tr key={index}>
                        <td><a onClick={(e) => self.deleteGroup(e, group.id, group.group_name)} href="#"><span
                            className="glyphicon glyphicon-trash">&nbsp;</span></a></td>
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