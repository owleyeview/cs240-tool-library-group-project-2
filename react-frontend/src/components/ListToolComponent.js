import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import * as ToolService from '../services/ToolService';
import { request } from '../services/ReservationService';
import Spacer from './Spacer.js';

export default function ListToolComponent({ showLogin }) {
  const [tools, setTools] = useState([])
    
  useEffect(() => { 
    getTools();
  }, [])
  
  const getTools = () => {
    ToolService.getTools().then((response) => {
        setTools(response.data)
        console.log(response.data);
      }).catch((error) => {
          console.log(error.response.data);
      })
  }

  const deleteTool = (id) => {
    if (localStorage.token) {
      ToolService.deleteTool(id).then(getTools, ({ response }) => {
        if (response.status === 403) {
          alert('You can only delete a tool you made');
        } else if (response.status === 401) {
          localStorage.removeItem('token');
          showLogin();
        } else {
          console.error(response);
        }
      });
    } else {
      showLogin();
    }
  }

  async function reservation(type, id) {
    if (localStorage.token) {
      const response = await request(type, id);
      if (response.status === 401) {
        localStorage.removeItem('token');
        showLogin();
        return;
      } else if (response.status === 403) {
        alert(`Error. Cannot ${type} tool. Maybe someone else got it first.`);
        console.error(response);
      } else if (response.status === 404) {
        alert(`Error. Connot ${type} tool. Perhaps it was deleted.`);
        console.error(response);
      }
      getTools();
    } else {
      showLogin();
    }
  }

  return (
  <div className='container'>
    <Spacer axis="vertical" size={50} />
    <h2 className="text-center">Tools List</h2>
    <Link to = "/add-tool" className="btn btn-primary mb-2"> Add Tool </Link>
    <table className="table table-bordered table-striped">
      <thead>
        <tr>
          <th> Name </th>
          <th> Description </th>
          <th> Category </th>
          <th> Location </th>
          <th> Actions </th>
        </tr>
      </thead>
      <tbody>
        {
          tools.map(({ tool, available, mine, canReturn }) =>
            <tr key={tool.id}>
              <td> {tool.toolName} </td>
              <td> {tool.toolDescription} </td>
              <td> {tool.toolCategory} </td>
              <td> {tool.toolLocation} </td>
              <td className = "spacing">
                { mine && <>
                  <Link to={`/update-tool/${tool.id}`} className="btn btn-info">Update</Link>
                  <button className="btn btn-danger" onClick={() => deleteTool(tool.id)}>Delete</button>
                </> }
                { available &&
                  <button className="btn btn-success"
                    onClick={() => reservation('checkout', tool.id)}>Checkout</button>
                }
                { !available && !canReturn &&
                  <button className="btn btn-primary"
                    onClick={() => reservation('reserve', tool.id)}>Reserve</button>
                }
                { canReturn &&
                  <button className="btn btn-warning"
                    onClick={() => reservation('return', tool.id)}>Return</button>
                }
              </td>
            </tr>
          )
        }
      </tbody>
    </table>

  </div>
  )
}