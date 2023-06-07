import React, {useEffect, useState} from 'react'
import {Link, useNavigate, useParams } from 'react-router-dom'
import * as ToolService from '../services/ToolService'
import Spacer from './Spacer.js'

export default function AddToolComponent({ showLogin }) {
  
    const [toolName, setToolName] = useState('')
    const [toolDescription, setToolDescription] = useState('')
    const [toolCategory, setToolCategory] = useState('')
    const [toolLocation, setToolLocation] = useState('')
    const [toolIsAvailable, setToolIsAvailable] = useState(true)
    const navigate = useNavigate(); // for navigating back to tools page after adding a tool
    const {id} = useParams();

    const saveOrUpdateTool = event => {
        event.preventDefault();

        if (localStorage.token) {
            const tool = {toolName, toolDescription, toolCategory, toolLocation};

            (id ? ToolService.updateTool(tool, id) : ToolService.createTool(tool)).then(() => navigate('/'), 
            ({ response }) => {
                if (response.status === 401) {
                    showLogin();
                } else if (response.status === 403) {
                    alert("You can only update a tool you created.");
                } else {
                    console.error(response);
                }
            });
        } else {
            showLogin();
        }
    }
    
    useEffect(() => {
        if (id) {
            // fill in the fields with information about the tool if updating
            ToolService.getToolById(id).then(({ data }) => {
                setToolName(data.toolName ?? '') // if a field is null, set to empty string
                setToolDescription(data.toolDescription ?? '')
                setToolCategory(data.toolCategory ?? '')
                setToolLocation(data.toolLocation ?? '')
                setToolIsAvailable(data.toolIsAvailable ?? true)
            }).catch(console.error);
        }
    }, [id]);

    return (
        <div>
           <br /><br /> 
           <div className = "container">
                <div className = "row">
                    <div className = "card col-md-6 offset-md-3 offset-md-3">
                        <Spacer axis="vertical" size={16} />
                        <h2 className = "text-center">{id ? 'Update' : 'Add'} Tool</h2>
                        <div className = "card-body">
                            <form>
                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Tool Name :</label>
                                    <input
                                        type = "text"
                                        placeholder = "Enter tool name"
                                        name = "toolName"
                                        className = "form-control"
                                        value = {toolName}
                                        onChange = {(event) => setToolName(event.target.value)}
                                    >
                                    </input>
                                </div>

                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Description :</label>
                                    <input
                                        type = "text"
                                        placeholder = "Enter tool description"
                                        name = "toolDescription"
                                        className = "form-control"
                                        value = {toolDescription}
                                        onChange = {(event) => setToolDescription(event.target.value)}
                                    >
                                    </input>
                                </div>

                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Category :</label>
                                    <input
                                        type = "text"
                                        placeholder = "Category"
                                        name = "toolCategory"
                                        className = "form-control"
                                        value = {toolCategory}
                                        onChange = {(event) => setToolCategory(event.target.value)}
                                    >
                                    </input>
                                </div>

                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Location :</label>
                                    <input
                                        type = "text"
                                        placeholder = "Location"
                                        name = "toolLocation"
                                        className = "form-control"
                                        value = {toolLocation}
                                        onChange = {(event) => setToolLocation(event.target.value)}
                                    >
                                    </input>
                                </div>

                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Available now?  </label>
                                    <input
                                        type = "checkbox"
                                        name = "toolAvailable"
                                        className = "form-check-input"
                                        value = {toolIsAvailable}
                                        onChange = {(event) => setToolIsAvailable(event.target.checked)}
                                    >
                                    </input>
                                </div>

                                <button className = "btn btn-success" onClick = {saveOrUpdateTool} >Submit </button>
                                <Link to="/" className="btn btn-danger"> Cancel </Link>
                            </form>
                        </div>
                    </div>
                </div>
           </div>
        </div>
    )
}