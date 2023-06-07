import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import * as ToolService from '../services/ToolService'
import Spacer from './Spacer.js'

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

  return (
  <div className='container'>
    <Spacer axis="vertical" size={50} />
    <h2 className="text-center">Tools List</h2>
    <Link to = "/add-tool" className="btn btn-primary mb-2"> Add Tool </Link>
    <table className="table table-bordered table-striped">
      <thead>
        <tr>
          <th> Tool Id </th>
          <th> Name </th>
          <th> Description </th>
          <th> Category </th>
          <th> Location </th>
          <th> Available? </th>
          <th> Actions </th>
        </tr>
      </thead>
      <tbody>
        {
          tools.map(tool =>
            <tr key={tool.id}>
              <td> {tool.id} </td>
              <td> {tool.toolName} </td>
              <td> {tool.toolDescription} </td>
              <td> {tool.toolCategory} </td>
              <td> {tool.toolLocation} </td>
              <td> {tool.toolIsAvailable} </td>
              <td>
                <Link to={`/update-tool/${tool.id}`} className="btn btn-info">Update</Link>
                <button style={{marginLeft: "10px"}} className="btn btn-danger" onClick={() => deleteTool(tool.id)}>Delete</button>
              </td>
            </tr>
          )
        }
      </tbody>
    </table>

  </div>
  )
}