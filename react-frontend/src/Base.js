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
import './normalize.css';
import './Base.css';

// import additional pages & components
import Homepage from './pages/homepage'
import Errorpage from './pages/errorpage'
import Stylepage from './pages/stylepage'
import Loginpage from './pages/loginpage'
import Itemspage from './pages/itemspage';
import Managepage from './pages/managepage';

import Navcomp from './comps/navigation'

const Basepage = () => {
    return (
        <Router>
        <div id="base">
            <div id="pagegrid">
                <header>
                    <Navcomp/>
                </header>
                <main>
                    <Switch>
                    <Route path="/" component={Homepage} exact/>
                    <Route path="/items" component={Itemspage} exact/>
                    <Route path="/stylepage" component={Stylepage}/>
                    <Route path="/manage" component={Managepage}/>
                    <Route path="/react-default" component={DefaultReactpage}/>
                    <Route path="/login" component={Loginpage} exact/>
                    <Route component={Errorpage}/>
                    </Switch>
                </main>
                <footer>
                    <p>Created by Adam Ball, Christina Nguyen, and Richard Pham. CMPE172, Spring 2020. <a href="https://github.com/richardphamsjsu2016/172project">See the GitHub code here.</a></p>
                </footer>
            </div>
        </div>
        </Router>
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
