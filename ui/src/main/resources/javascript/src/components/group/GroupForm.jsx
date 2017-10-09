import React from "react"
import rest from "rest"
import mime from "rest/interceptor/mime"
import {NotificationManager} from 'react-notifications';

export default class GroupForm extends React.Component {

    constructor() {
        super();
        this.postNewAccountData = this.postNewAccountData.bind(this);
    }

    postNewAccountData(e) {
        e.preventDefault();
        const client = rest.wrap(mime, {mime: 'application/json'});
        let data = {
            "group_name": this.nameInput.value,
            "group_id": this.idInput.value
        };
        client({
            method: 'POST',
            headers: {'Content-Type': 'application/json'}, entity: data, path: 'http://localhost:8095/group'
        }).then(response => {
            if (response.status.code === 201) {
                NotificationManager.success("Group have been successfully saved", "Groups");
                this.nameInput.value = '';
                this.idInput.value = '';
                this.props.onUpdate();
            } else {
                NotificationManager.error("Group save failed" + response.error, "Groups");
            }
        })
    }

    render() {
        return <div id='group-form' className="col-sm-5 list-form">
            <h3>Add Group</h3>
            <form className="form-horizontal">
                <div className="form-group">
                    <label htmlFor="login" className="col-sm-2 control-label">Name</label>
                    <div className="col-sm-10">
                        <input type='text' className="form-control" placeholder='Name' id='name' name='name'
                               ref={(input) => {
                                   this.nameInput = input;
                               }}/>
                    </div>
                </div>
                <div className="form-group">
                    <label htmlFor="password" className="col-sm-2 control-label">VK ID</label>
                    <div className="col-sm-10">
                        <input type='text' className="form-control" id="id" name='id' placeholder="ID"
                               ref={(input) => {
                                   this.idInput = input;
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