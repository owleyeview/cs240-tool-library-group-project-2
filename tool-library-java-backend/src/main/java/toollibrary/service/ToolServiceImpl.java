package toollibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toollibrary.dao.ToolDao;
import toollibrary.exception.ResourceNotFoundException;
import toollibrary.model.Tool;

import java.util.List;
import java.util.Optional;

@Service
public class ToolServiceImpl implements ToolService{

    private final ToolDao toolDao;

    @Autowired
    public ToolServiceImpl(ToolDao toolDao) {
        this.toolDao = toolDao;
    }

    @Override
    public List<Tool> getAllTools() {  // this should be changed to return our own data structure
        return toolDao.findAll();
    }

    @Override
    public Tool createTool(Tool tool) {
        return toolDao.save(tool);
    }

    @Override
    public Tool getToolById(long id) {
        Optional<Tool> toolOptional = toolDao.findById(id);
        if(toolOptional.isPresent()){
            return toolOptional.get();
        } else {
            throw new ResourceNotFoundException("Tool not found with id: " + id);
        }
    }

    @Override
    public Tool updateToolById(long id, Tool tool) {
        Optional<Tool> toolOptional = toolDao.findById(id);
        if(toolOptional.isPresent()){
            Tool toolToUpdate = toolOptional.get();

            toolToUpdate.setToolName(tool.getToolName());
            toolToUpdate.setToolDescription(tool.getToolDescription());
            toolToUpdate.setToolCategory(tool.getToolCategory());
            toolToUpdate.setToolLocation(tool.getToolLocation());
            toolToUpdate.setToolIsAvailable(tool.isToolIsAvailable());

            return toolDao.save(toolToUpdate);
        } else {
            throw new ResourceNotFoundException("Tool not found with id: " + id);
        }
    }

    @Override
    public void deleteToolById(long id) {
        Optional<Tool> toolOptional = toolDao.findById(id);
        if(toolOptional.isPresent()){
            toolDao.delete(toolOptional.get());
        } else {
            throw new ResourceNotFoundException("Tool not found with id: " + id);
        }
    }
}

