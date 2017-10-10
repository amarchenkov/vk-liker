import React from "react"
import {Route, Switch} from "react-router-dom";
import AccountList from "./account/AccountList";
import GroupList from "./group/GroupList";
import SourceList from "./source/SourceList";

export default class Content extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <div className="col-sm-9 col-md-10 col-md-offset-2 main" id="content-block">
            <Switch>
                <Route exact path='/Accounts' component={AccountList}/>
                <Route exact path='/Groups' component={GroupList}/>
                <Route exact path='/Source' component={SourceList}/>
            </Switch>
        </div>;
    }
}