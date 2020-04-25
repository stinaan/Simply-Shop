/**
 * This JS file is for defining the base website template to fit
 * everything else into. The majority, if not all, of the pages on
 * the site should adhere to the markup returned from this file.
 */

// import code dependencies
import React from 'react';
import { 
  BrowserRouter as Router, 
  Route, 
  Switch,
} from 'react-router-dom';

// import assets
import logo from './logo.svg';

// import styling
import './App.css';

// import additional pages & components
import Homepage from './pages/homepage'
import Errorpage from './pages/errorpage'
import Stylepage from './pages/stylepage'

import Navcomp from './comps/navigation'

const Basepage = () => {
    return (
        <div id="base">
        <Router>
          <div>
            <Navcomp/>
            <Switch>
              <Route path="/" component={Homepage} exact/>
              <Route path="/stylepage" component={Stylepage}/>
              <Route path="/react-default" component={DefaultReactpage}/>
              <Route component={Errorpage}/>
            </Switch>
          </div>
        </Router>
        </div>
    );
}

/**
 * Represents a webpage with just the content of the default React
 * page template.
 */
const DefaultReactpage = () => {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>Simply Shop</h1>
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer">
          Learn React
        </a>
      </header>
    </div>
  );
}

export default Basepage;
