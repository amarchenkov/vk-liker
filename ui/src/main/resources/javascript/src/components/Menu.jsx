import React from "react"

export default class Menu extends React.Component {

    navigateTo(to, index) {
        console.log(index)
    }

    render() {
        const self = this;
        return <div id='menu' className='col-sm-3 col-md-2 sidebar'>
            <ul className='nav nav-sidebar'>
                {this.props.items.map(function (m, index) {
                    return <li key={index}><a href='#' onClick={self.navigateTo.bind({m}, {index})}>{m}</a></li>
                })}
            </ul>
        </div>
    }
}