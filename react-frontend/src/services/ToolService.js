import axios from 'axios';

const TOOL_API_BASE_URL = "http://localhost:8080/api/v1/tools";

class ToolService {
    
        getTools(){
            return axios.get(TOOL_API_BASE_URL)
        }
    
        createTool(tool){
            return axios.post(TOOL_API_BASE_URL, tool)
        }
    
        getToolById(toolId){
            return axios.get(TOOL_API_BASE_URL + '/' + toolId)
        }
    
        updateTool(tool, toolId){
            return axios.put(TOOL_API_BASE_URL + '/' + toolId, tool)
        }
    
        deleteTool(toolId){
            return axios.delete(TOOL_API_BASE_URL + '/' + toolId)
        }
}

    const toolService = new ToolService();
    export default toolService; // So we can use this class in a component
    
