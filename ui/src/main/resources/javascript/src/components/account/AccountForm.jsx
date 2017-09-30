import React from "react"
import rest from "rest"
import mime from "rest/interceptor/mime"
import AccountList from "./AccountList";

export default class AccountForm extends React.Component {

    constructor() {
        super();
        this.postNewAccountData = this.postNewAccountData.bind(this);
    }

    postNewAccountData(e) {
        e.preventDefault();
        const client = rest.wrap(mime, {mime: 'application/json'});
        let data = {
            "login": this.loginInput.value,
            "password": this.passwordInput.value
        };
        client({
            method: 'POST',
            headers: {'Content-Type': 'application/json'}, entity: data, path: 'http://localhost:8095/api/account'
        }).then(response => {
            if (response.status.code === 201) {
                this.loginInput.value = '';
                this.passwordInput.value = '';
                this.props.onUpdate();
            }
        })
    }

    render() {
        return <div id='account-form' className="col-sm-5">
            <h3>Add Account</h3>
            <form className="form-horizontal">
                <div className="form-group">
                    <label htmlFor="login" className="col-sm-2 control-label">Login</label>
                    <div className="col-sm-10">
                        <input type='text' className="form-control" placeholder='Login' id='login' name='login'
                               ref={(input) => {
                                   this.loginInput = input;
                               }}/>
                    </div>
                </div>
                <div className="form-group">
                    <label htmlFor="password" className="col-sm-2 control-label">Password</label>
                    <div className="col-sm-10">
                        <input type='text' className="form-control" id="password" name='password' placeholder="Password"
                               ref={(input) => {
                                   this.passwordInput = input;
                               }}/>
                    </div>
                </div>
                <div className="form-group">
                    <div className="col-sm-offset-2 col-sm-10">
                        <button type="submit" className="btn btn-info" onClick={this.postNewAccountData}>Добавить
                        </button>
                    </div>
                </div>
            </form>
        </div>;
    }
}