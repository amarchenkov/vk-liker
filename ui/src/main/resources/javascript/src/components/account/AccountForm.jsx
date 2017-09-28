import React from "react"

export default class AccountForm extends React.Component {
    render() {
        return <div id='account-form'>
            <h3>Add Account</h3>
            <form>
                <div className='row'>
                    <label>Login</label>
                    <input type='text' name='login' ref="login"/>
                </div>
                <div className='row'>
                    <label>Password</label>
                    <input type='text' name='password' ref="password"/>
                </div>
                <div className='row'>
                    <button className='btn btn-info'>Добавить</button>
                </div>
            </form>
        </div>;
    }
}