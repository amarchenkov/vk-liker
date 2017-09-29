import React from "react"
import AccountForm from "./AccountForm";

export default class AccountList extends React.Component {

    componentDidMount() {
        this.state.accounts = [{
            "id" : 1,
            "login": "login",
            "password": "password",
            "access"
        }];
    }

    render() {
        return <div id='account-list' className='table-responsive'>
            <h1 className="page-header">Accounts</h1>
            <table className='table table-striped'>
                <thead>
                <tr>
                    <th>#</th>
                    <th>ID</th>
                    <th>Login</th>
                    <th>Password</th>
                    <th>Access Token</th>
                    <th>Expiration Time</th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                {this.state.accounts.map(function (account, index) {
                    return <tr>
                        <td>{index + 1}</td>
                        <td>{account.id}</td>
                        <td>{account.login}</td>
                        <td>{account.password}</td>
                        <td>{account.access_token}</td>
                        <td>{account.expires_in}</td>
                        <td><a href="#">Получить Access Token</a></td>
                    </tr>;
                })}
                </tbody>
            </table>
            <AccountForm/>
        </div>;
    }
}