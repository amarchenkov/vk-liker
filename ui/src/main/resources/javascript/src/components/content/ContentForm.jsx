import React from "react"
import rest from "rest"
import mime from "rest/interceptor/mime"
import {NotificationManager} from 'react-notifications';

export default class ContentForm extends React.Component {

    constructor() {
        super();
        this.postNewSourceData = this.postNewSourceData.bind(this);
    }

    postNewSourceData(e) {
        e.preventDefault();
        const client = rest.wrap(mime, {mime: 'application/json'});
        let data = {
            "name": this.nameInput.value,
            "source_id": this.sourceIdInput.value,
            "source_type": this.sourceTypeInput.value
        };
        client({
            method: 'POST',
            headers: {'Content-Type': 'application/json'}, entity: data, path: 'http://localhost:8095/content'
        }).then(response => {
                if (response.status.code === 201) {
                    NotificationManager.success("Source have been successfully saved", "Sources");
                    this.nameInput.value = '';
                    this.sourceIdInput.value = '';
                    this.sourceTypeInput.value = '';
                    this.props.onUpdate();
                } else {
                    NotificationManager.error("Source save failed" + response.error, "Sources");
                }
            },
            response => {
                NotificationManager.error("Source save failed. " + response.error, "Sources");
            })
    }

    render() {
        return <div id='source-form' className="col-sm-6 list-form">
            <h3>Add Content Source</h3>
            <form className="form-horizontal">

                <div className="form-group">
                    <label htmlFor="name" className="col-sm-3 control-label">Name</label>
                    <div className="col-sm-9">
                        <input type='text' className="form-control" placeholder='Name' id='name' name='name'
                               ref={(input) => {
                                   this.nameInput = input;
                               }}/>
                    </div>
                </div>

                <div className="form-group">
                    <label htmlFor="source_id" className="col-sm-3 control-label">Source ID</label>
                    <div className="col-sm-9">
                        <input type='text' className="form-control" id="source_id" name='source_id'
                               placeholder="Source ID"
                               ref={(input) => {
                                   this.sourceIdInput = input;
                               }}/>
                    </div>
                </div>

                <div className="form-group">
                    <label htmlFor="source_type" className="col-sm-3 control-label">Source Type</label>
                    <div className="col-sm-9">
                        <select className="form-control" id="source_type" name='source_type' ref={(input) => {
                            this.sourceTypeInput = input;
                        }}>
                            <option value='VK_GROUP'>VKontakte Group</option>
                            <option value="FB_GROUP">Facebook Group</option>
                        </select>
                    </div>
                </div>

                <div className="form-group">
                    <div className="col-sm-offset-2 col-sm-10">
                        <button type="submit" className="btn btn-info" onClick={this.postNewSourceData}>Добавить
                        </button>
                    </div>
                </div>
            </form>
        </div>;
    }
}