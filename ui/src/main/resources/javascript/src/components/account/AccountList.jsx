import React from "react"
import AccountForm from "./AccountForm";
import rest from "rest"
import mime from "rest/interceptor/mime"
import {NotificationManager} from "react-notifications"

export default class AccountList extends React.Component {

    constructor() {
        super();
        this.state = {"accounts": []}
    }

    componentDidMount() {
        const client = rest.wrap(mime);
        const self = this;
        client({
            path: 'http://localhost:8095/account',
            method: 'GET',
            headers: {'Content-Type': 'application/json'}
        }).then(function (response) {
            console.log(response);
            if (response.status.code === 200) {
                self.setState({"accounts": response.entity});
                NotificationManager.info("Account list received", "Accounts");
            } else {
                NotificationManager.error("Getting account failed. " + response.error, "Accounts");
            }
        });
    }

    deleteAccount(e, id) {
        e.preventDefault();
        const client = rest.wrap(mime);
        const self = this;
        client({
            path: 'http://localhost:8095/account/' + id,
            method: 'DELETE',
        }).then(function (response) {
            if (response.status.code === 204) {
                NotificationManager.success("Account " + id + " removed", "Accounts");
            } else {
                NotificationManager.error("Remove account failed. " + response.error, "Accounts");
            }
        });
    }

    onUpdate() {
        this.forceUpdate();
    }

    render() {
        const self = this;
        return <div id='account-list' className='table-responsive'>
            <h1 className="page-header">Accounts</h1>
            <AccountForm onUpdate={this.onUpdate.bind(this)}/>
            <table className='table table-striped'>
                <thead>
                <tr>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                    <th>#</th>
                    <th>ID</th>
                    <th>Login</th>
                    <th>Password</th>
                    <th>Access Token</th>
                    <th>Expiration Time</th>
                </tr>
                </thead>
                <tbody>
                {this.state.accounts.map(function (account, index) {
                    return <tr key={index}>
                        <td><a onClick={(e) => self.deleteAccount(e, account.id)} href="#"><span
                            className="glyphicon glyphicon-trash">&nbsp;</span></a></td>
                        <td><a
                            href={"https://oauth.vk.com/authorize?client_id=6194447&display=page&redirect_uri=http://" + window.location.host + "/response/" + account.id + "&scope=friends&response_type=code&v=5.68"}>Получить
                            Access Token</a></td>
                        <td>{index + 1}</td>
                        <td>{account.id}</td>
                        <td>{account.login}</td>
                        <td>{account.password}</td>
                        <td>{(account.access_token !== null) ? account.access_token['access_token'] : ''}</td>
                        <td>{(account.access_token !== null) ? account.access_token.expires_in : ''}</td>
                    </tr>;
                })}
                </tbody>
            </table>
        </div>;
    }
}