import React from "react"
import { Link } from 'react-router-dom'

export default class Menu extends React.Component {

    render() {
        const self = this;
        return <div className="col-sm-3 col-md-2 sidebar">
            <ul className="nav nav-sidebar">
                {this.props.items.map(function (m, index) {
                    return <li key={index}>
                        <Link to='/accounts'>{m}</Link>
                    </li>
                })}
            </ul>
        </div>;
    }
}