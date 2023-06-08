import axios from 'axios';

const TOOL_API_BASE_URL =
    (process.env.REACT_APP_BASE_URL ?? 'http://localhost:8080/api/v1/') + 'tools';

export function getTools() {
    return axios.get(TOOL_API_BASE_URL)
}
    
export function createTool(tool) {
    return axios.post(`${TOOL_API_BASE_URL}/${localStorage.token}`, tool);
}

export function getToolById(toolId) {
    return axios.get(`${TOOL_API_BASE_URL}/${toolId}`);
}

export function updateTool(tool, toolId) {
    return axios.put(`${TOOL_API_BASE_URL}/${toolId}/${localStorage.token}`, tool);
}

export function deleteTool(toolId) {
    return axios.delete(`${TOOL_API_BASE_URL}/${toolId}/${localStorage.token}`);
}