import React from "react"

export default class Content extends React.Component {
    constructor(props) {
        super(props);
        this.state = {"header": "Добро пожаловать в VK Bot"};
    }

    render() {
        return <div className='col-sm-9 col-md-10'>
            <h1>{this.state.header}</h1>
        </div>;
    }
}