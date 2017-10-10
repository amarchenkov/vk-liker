import React from "react"
import rest from "rest"
import mime from "rest/interceptor/mime"
import 'react-notifications/lib/notifications.css';
import {NotificationManager} from 'react-notifications';

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
        };
        client({
            method: 'POST',
            headers: {'Content-Type': 'application/json'}, entity: data, path: 'http://localhost:8095/account'
        }).then(response => {
            if (response.status.code === 201) {
                NotificationManager.success("Account have been successfully saved", "Accounts");
                this.loginInput.value = '';
                this.props.onUpdate();
            } else {
                NotificationManager.error("Account save failed" + response.error, "Accounts");
            }
        })
    }

    render() {
        return <div id='account-form' className="col-sm-5 list-form">
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
                    <div className="col-sm-offset-2 col-sm-10">
                        <button type="submit" className="btn btn-info" onClick={this.postNewAccountData}>Добавить
                        </button>
                    </div>
                </div>
            </form>
        </div>;
    }
}