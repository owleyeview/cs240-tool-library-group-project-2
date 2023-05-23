package toollibrary.api;


import toollibrary.service.ToolService;
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

    private ToolService toolService;

    @Autowired
    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    // get all tools
    @GetMapping
    public List<Tool> getAllTools() { // we could use our own data structure here
        return toolService.getAllTools();
    }

    @PostMapping
    public Tool createTool(@RequestBody Tool tool) {
        return toolService.createTool(tool);
    }

    // method to return a single tool by id
    // requested by going to the request mapping url and adding the id of the tool
    // ex: localhost:8080/api/v1/tools/1
    @GetMapping("/{id}")
    public ResponseEntity<Tool> getToolById(@PathVariable long id) {
        return ResponseEntity.ok(toolService.getToolById(id));
    }

    // method to update a tool by id
    // this one requires a tool object to be passed in the body of the request
    @PutMapping("/{id}")
    public ResponseEntity<Tool> updateToolById(@PathVariable long id,@RequestBody Tool tool) {
        return ResponseEntity.ok(toolService.updateToolById(id, tool));
    }

    // method to delete a tool by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Tool> deleteToolById(@PathVariable long id) {
        toolService.deleteToolById(id);
        return ResponseEntity.ok().build();
    }
}
