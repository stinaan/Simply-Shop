// import code dependencies
import React from 'react';
import { 
  BrowserRouter as Router, 
  Route, 
  Switch,
  NavLink
} from 'react-router-dom';

// import assets
import logo from './logo.svg';

// import styling
import './App.css';

// import additional pages & components
import Stylepage from './pages/stylepage'

/**
 * Represents the base of all pages in the application.
 */
function App() {
  return (
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
  );
}

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

/**
 * Represents the home page of the website.
 */
const Homepage = () => {
  return (
    <div>
      <h1>Simply Shop</h1>
      <p>This is the home page!</p>
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

/**
 * Represents an error page when a page could not be found.
 */
const Errorpage = () => {
  return (
    <div>
      <h1>Error!</h1>
      <p>Sorry, that page doesn't exist!</p>
    </div>
  );
}

export default App;
