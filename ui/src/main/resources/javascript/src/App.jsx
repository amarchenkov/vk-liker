import React from "react"
import ReactDom from "react-dom"
import Menu from "./components/Menu"
import Content from "./components/Content"

import "bootstrap/dist/css/bootstrap.min.css"

class App extends React.Component {
    render() {
        return <div>
            <div className='navbar navbar-default navbar-fixed-top' role='navigation'>
                <div className='container-fluid'>
                    <div className='navbar-header'>
                        <a className="navbar-brand" href="#">VK Bot Console</a></div>
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

ReactDom.render(<App/>, document.getElementById("root"));