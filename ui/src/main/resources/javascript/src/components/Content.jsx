import React from "react"
import {Route, Switch} from "react-router-dom";
import AccountList from "./account/AccountList";

export default class Content extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <div className="col-sm-9 col-md-10 col-md-offset-2 main">
            <Switch>
                <Route exact path='/accounts' component={AccountList}/>
            </Switch>
        </div>;
    }
}