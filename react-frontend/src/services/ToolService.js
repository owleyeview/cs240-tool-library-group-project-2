import axios from 'axios';

const TOOL_API_BASE_URL =
    (process.env.REACT_APP_BASE_URL ?? 'http://localhost:8080/api/v1/') + 'tools';

export function getTools() {
    return axios.get(TOOL_API_BASE_URL, getConfig());
}
    
export function createTool(tool) {
    return axios.post(TOOL_API_BASE_URL, tool, getConfig());
}

export function getToolById(toolId) {
    return axios.get(`${TOOL_API_BASE_URL}/${toolId}`, getConfig());
}

export function updateTool(tool, toolId) {
    return axios.put(`${TOOL_API_BASE_URL}/${toolId}`, tool, getConfig());
}

export function deleteTool(toolId) {
    return axios.delete(`${TOOL_API_BASE_URL}/${toolId}`, getConfig());
}

function getConfig() {
    return {
        headers: {
            Authorization: localStorage.token ?? ''
        }
    }
}