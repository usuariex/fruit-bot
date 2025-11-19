import React from "react";
import { Link } from "react-router-dom";
import './Header.css'

function Header() {
    return (
        <header className="app-header">
            <Link to="/" className="header-title-link"><h1>Fruit-Bot</h1></Link>
        </header>
    );
}

export default Header;