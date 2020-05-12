import React from 'react';

import { NavLink } from 'react-router-dom';

/**
 * Represents a navigation component to other pages on the website.
 */
const Navcomp = () => {
    return (
      <nav>
        <span id="nav-title">
          <NavLink to="/">Simply Shop</NavLink>
        </span>
        <ul>
          <li><NavLink to="/">Home</NavLink></li>
          <li><NavLink to="/items">Items</NavLink></li>
          <li><NavLink to="/manage">Manage</NavLink></li>
          <li><NavLink to="/stylepage">Stylepage</NavLink></li>
        </ul>
        {/* <button id="login-button">
          <NavLink to="/login">Login as Admin</NavLink>
        </button> */}
      </nav>
    );
}

export default Navcomp;