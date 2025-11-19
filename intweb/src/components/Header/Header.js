import React from "react";
import { Link } from "react-router-dom";
import './Header.css'

function Header() {
    return (
        <header className="app-header">
            <Link to="/" className="header-title-link"><h1>Fruit-Bot</h1></Link>
            <nav>
                <ul className="nav-links">
                    <li><Link to="/dashboard">Dashboard</Link></li>
                </ul>
            </nav>
        </header>
    );
}

export default Header;