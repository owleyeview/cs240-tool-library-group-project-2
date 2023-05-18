import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import HeaderComponent from './components/HeaderComponent';
import ListToolComponent from './components/ListToolComponent';
import AddToolComponent from './components/AddToolComponent';

export default function App() {
  return (
    <div>
      <Router>
        <HeaderComponent />
        <div className= "container">
          <Routes>
            <Route exact path = "/" element = {<ListToolComponent/>}></Route>
            <Route path = "/tools" element = {<ListToolComponent/>}></Route>
            <Route path = "/add-tool" element = {<AddToolComponent/>}></Route>
            <Route path = "/update-tool/:id" element = {<AddToolComponent/>}></Route>
          </Routes>
        </div>
    </Router>
    </div>
  );
}
