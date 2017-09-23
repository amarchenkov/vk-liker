import React from "react"
import ReactDom from "react-dom"
import Menu from "./components/Menu"
import Content from "./components/Content"

import "bootstrap/dist/css/bootstrap.min.css"

class App extends React.Component {
    render() {
        return <div id='web' className='row'><Menu/><Content/></div>;
    }
}

ReactDom.render(<App/>, document.getElementById("root"));