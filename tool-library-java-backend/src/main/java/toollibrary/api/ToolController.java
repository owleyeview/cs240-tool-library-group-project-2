package toollibrary.api;

import toollibrary.dao.ToolDao;
import toollibrary.exception.ResourceNotFoundException;
import toollibrary.model.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*") // allows requests from any origin
@RestController
@RequestMapping("/api/v1/tools")
public class ToolController {
    @Autowired
    private ToolDao toolDao;

    // get all tools
    @GetMapping
    public List<Tool> getAllTools() { // we could use our own data structure here
        return toolDao.findAll();
    }

    @PostMapping
    public Tool createTool(@RequestBody Tool tool) {
        return toolDao.save(tool);
    }

    // method to return a single tool by id
    // requested by going to the request mapping url and adding the id of the tool
    // ex: localhost:8080/api/v1/tools/1
    @GetMapping("/{id}")
    public ResponseEntity<Tool> getToolById(@PathVariable long id) {
        Tool tool = toolDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tool not found with id: " + id));
        return ResponseEntity.ok(tool);
    }

    // method to update a tool by id
    // this one requires a tool object to be passed in the body of the request
    @PutMapping("/{id}")
    public ResponseEntity<Tool> updateToolById(@PathVariable long id,@RequestBody Tool tool) {
        Tool toolToUpdate = toolDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tool not found with id: " + id));

        toolToUpdate.setToolName(tool.getToolName());
        toolToUpdate.setToolDescription(tool.getToolDescription());
        toolToUpdate.setToolCategory(tool.getToolCategory());
        toolToUpdate.setToolLocation(tool.getToolLocation());

        Tool updatedTool = toolDao.save(toolToUpdate);
        return ResponseEntity.ok(updatedTool);
    }

    // method to delete a tool by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Tool> deleteToolById(@PathVariable long id) {
        Tool toolToDelete = toolDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tool not found with id: " + id));
        toolDao.delete(toolToDelete);
        return ResponseEntity.ok().build();
    }
}
