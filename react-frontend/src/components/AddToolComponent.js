import React, {useEffect, useState} from 'react'
import {Link, useNavigate, useParams } from 'react-router-dom'
import ToolService from '../services/ToolService'
import Spacer from './Spacer.js'

const AddToolComponent = () => {
  
    const [toolName, setToolName] = useState('')
    const [toolDescription, setToolDescription] = useState('')
    const [toolCategory, setToolCategory] = useState('')
    const [toolLocation, setToolLocation] = useState('')
    const [toolIsAvailable, setToolIsAvailable] = useState(true)
    const navigate = useNavigate(); // for navigating back to tools page after adding a tool
    const {id} = useParams();

    const saveOrUpdateTool = (event) => {
        event.preventDefault();

        const tool = {toolName, toolDescription, toolCategory, toolLocation, toolIsAvailable}

        if(id){
            ToolService.updateTool(tool, id).then((response) => {
                console.log(response.data)
                navigate('/tools')
            }).catch(error => {
                console.log(error.response.data)
            })

        }else{
            ToolService.createTool(tool).then((response) =>{
                console.log(response.data)
                navigate('/tools');
            }).catch(error => {
                console.log(error.response.data)
            })
        }  
    }
    
    useEffect(() => {
        if (id) {
        ToolService.getToolById(id).then((response) => {
            setToolName(response.data.toolName || '') // if a field is null, set to empty string
            setToolDescription(response.data.toolDescription || '')
            setToolCategory(response.data.toolCategory || '')
            setToolLocation(response.data.toolLocation || '')
            setToolIsAvailable(response.data.toolIsAvailable || true)
        }).catch(error => {
            console.log(error.response.data)
        })
        }
    }, [id])

    // are we updating or adding?
    const title = () => {
        if(id){
            return <h2 className = "text-center">Update Tool</h2>
        }else{
            return <h2 className = "text-center">Add Tool</h2>
        }
    }
    
    return (
        <div>
           <br /><br /> 
           <div className = "container">
                <div className = "row">
                    <div className = "card col-md-6 offset-md-3 offset-md-3">
                        <Spacer axis="vertical" size={16} />
                       {
                            title()
                       }
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

                                <button className = "btn btn-success" onClick = {(event) => saveOrUpdateTool(event)} >Submit </button>
                                <Link to="/tools" className="btn btn-danger"> Cancel </Link>
                            </form>

                        </div>
                    </div>
                </div>

           </div>

        </div>
    )
}

export default AddToolComponent