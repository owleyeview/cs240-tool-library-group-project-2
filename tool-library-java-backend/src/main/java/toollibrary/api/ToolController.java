package toollibrary.api;

import toollibrary.component.MemberComponent;
import toollibrary.dao.ToolDao;
import toollibrary.model.Member;
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

    @Autowired
    private MemberComponent memberComponent;

    // get all tools
    @GetMapping
    public List<Tool> getAllTools() { // we could use our own data structure here
        return toolDao.findAll();
    }

    // create a tool
    @PostMapping("/{user}")
    public ResponseEntity<Tool> createTool(@PathVariable String user, @RequestBody Tool tool) {
        Member member = memberComponent.checkCredentials(user);

        if (member == null) {
            return ResponseEntity.status(401).build();
        }

        tool.setOwner(member);
        
        return ResponseEntity.ok(toolDao.save(tool));
    }

    // method to return a single tool by id
    // requested by going to the request mapping url and adding the id of the tool
    // ex: localhost:8080/api/v1/tools/1
    @GetMapping("/{id}")
    public ResponseEntity<Tool> getToolById(@PathVariable long id) {
        Tool tool = toolDao.findById(id).orElse(null);
        if (tool == null) {
            return ResponseEntity.status(404).build();
        }
        
        return ResponseEntity.ok(tool);
    }

    // method to update a tool by id
    // this one requires a tool object to be passed in the body of the request
    @PutMapping("/{id}/{user}")
    public ResponseEntity<Tool> updateToolById(
            @PathVariable long id, @PathVariable String user, @RequestBody Tool tool) {
        Tool toolToUpdate = toolDao.findById(id).orElse(null);

        if (toolToUpdate == null) {
            return ResponseEntity.status(404).build();
        }

        Member member = memberComponent.checkCredentials(user);
        Member owner = toolToUpdate.getOwner();

        if (member == null || owner == null || owner.getId() != member.getId()) {
            return ResponseEntity.status(401).build();
        }

        toolToUpdate.setToolName(tool.getToolName());
        toolToUpdate.setToolDescription(tool.getToolDescription());
        toolToUpdate.setToolCategory(tool.getToolCategory());
        toolToUpdate.setToolLocation(tool.getToolLocation());

        Tool updatedTool = toolDao.save(toolToUpdate);
        return ResponseEntity.ok(updatedTool);
    }

    // method to delete a tool by id
    @DeleteMapping("/{id}/{user}")
    public ResponseEntity<Tool> deleteToolById(@PathVariable long id, @PathVariable String user) {
        Tool toolToDelete = toolDao.findById(id).orElse(null);

        if (toolToDelete == null) {
            return ResponseEntity.status(404).build();
        }

        Member member = memberComponent.checkCredentials(user);
        Member owner = toolToDelete.getOwner();

        if (member == null || owner == null || owner.getId() != member.getId()) {
            return ResponseEntity.status(401).build();
        }

        toolDao.delete(toolToDelete);
        return ResponseEntity.ok().build();
    }
}
