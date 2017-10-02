import React from "react"
import {Route, Switch} from "react-router-dom";
import AccountList from "./account/AccountList";
import GroupList from "./group/GroupList";

export default class Content extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <div className="col-sm-9 col-md-10 col-md-offset-2 main">
            <Switch>
                <Route exact path='/Accounts' component={AccountList}/>
                <Route exact path='/Groups' component={GroupList}/>
            </Switch>
        </div>;
    }
}