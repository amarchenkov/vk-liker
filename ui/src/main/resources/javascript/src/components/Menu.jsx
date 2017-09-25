import React from "react"

export default class Menu extends React.Component {

    navigateTo(to) {
        alert(to);
    }

    render() {
        return <div id='menu' className='col-sm-3 col-md-2 sidebar'>
            <ul className='nav nav-sidebar'>
                <li><a href='#' onClick={this.navigateTo}>Accounts</a></li>
                <li><a href='#' onClick={this.navigateTo}>Groups</a></li>
                <li><a href='#' onClick={this.navigateTo}>Source</a></li>
                <li><a href='#' onClick={this.navigateTo}>User Base</a></li>
                <li><a href='#' onClick={this.navigateTo}>Security</a></li>
                <li><a href='#' onClick={this.navigateTo}>Log Dashboard</a></li>
            </ul>
        </div>
    }
}