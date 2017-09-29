import React from "react"
import ReactDom from "react-dom"
import Menu from "./components/Menu"
import Content from "./components/Content"
import "bootstrap/dist/css/bootstrap.min.css"
import {HashRouter} from 'react-router-dom';

class App extends React.Component {
    render() {
        return <div>
            <div className="navbar navbar-inverse navbar-fixed-top" role="navigation">
                <div className="container-fluid">
                    <div className="navbar-header">
                        <button type="button" className="navbar-toggle" data-toggle="collapse"
                                data-target=".navbar-collapse">
                            <span className="sr-only">Toggle navigation</span>
                        </button>
                        <a className="navbar-brand" href="#">VK Bot</a>
                    </div>
                </div>
            </div>
            <div id='web' className='container-fluid'>
                <div className='row'>
                    <Menu items={['Accounts', 'Groups', 'Source', 'User Base', 'Security', 'Log Dashboard']}/>
                    <Content/>
                </div>
            </div>
        </div>;
    }
}

ReactDom.render(<HashRouter>
        <App/>
    </HashRouter>,
    document.getElementById("root")
);