import React from 'react';

import { NavLink } from 'react-router-dom';

/**
 * Represents a navigation component to other pages on the website.
 */
const Navcomp = () => {
    return (
      <nav>
        <ul>
          <li><NavLink to="/">Home</NavLink></li>
          <li><NavLink to="/stylepage">Stylepage</NavLink></li>
          <li><NavLink to="/react-default">React Default Page</NavLink></li>
          <li><NavLink to="/garbage">Error Page</NavLink></li>
        </ul>
      </nav>
    );
}

export default Navcomp;