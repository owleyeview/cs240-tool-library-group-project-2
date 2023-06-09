import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import HeaderComponent from './components/HeaderComponent';
import ListToolComponent from './components/ListToolComponent';
import AddToolComponent from './components/AddToolComponent';
import { useState } from 'react';
import Login from './components/Login';

export default function App() {
  const [loginShown, setLoginShown] = useState(false);

  const showLogin = () => setLoginShown(true);

  const loginEvent = { onLogin: () => {} };

  return (
    <div>
      <Router>
        <HeaderComponent showLogin = { showLogin }/>
        <div className= "container">
          <Routes>
            <Route exact path = "/" element = {
              <ListToolComponent showLogin={showLogin} loginEvent={loginEvent}/>
            }/>
            <Route path = "/tools" element = {
              <ListToolComponent showLogin={showLogin} loginEvent={loginEvent}/>
            }/>
            <Route path = "/add-tool" element = {
              <AddToolComponent showLogin={showLogin}/>
            }/>
            <Route path = "/update-tool/:id" element = {
              <AddToolComponent showLogin={showLogin}/>
            }/>
          </Routes>
        </div>
        <Login isShown={loginShown} setLoginShown={setLoginShown} loginEvent={loginEvent}/>
    </Router>
    </div>
  );
}