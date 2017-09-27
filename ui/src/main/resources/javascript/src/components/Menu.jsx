import React from "react"
import Content from "./Content";

export default class Menu extends React.Component {

    navigateTo(to, index) {
        console.log(this.refs.non);
    }

    render() {
        const self = this;
        return <div id='menu' className='col-sm-3 col-md-2 sidebar'>
            <ul className='nav nav-sidebar'>
                {this.props.items.map(function (m, index) {
                    return <li key={index}><a href='#' onClick={self.navigateTo.bind({m}, index)}>{m}</a></li>
                })}
            </ul>
        </div>
    }
}