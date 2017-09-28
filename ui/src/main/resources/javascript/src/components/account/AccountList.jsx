import React from "react"

export default class AccountList extends React.Component {

    render() {
        return <div id='account-list'>
            <h1>Accounts</h1>
            <table>
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
                {this.props.accounts.map(function (account, index) {
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
        </div>;
    }
}