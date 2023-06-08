package toollibrary.api;

import toollibrary.component.MemberComponent;
import toollibrary.dao.ToolDao;
import toollibrary.exception.ForbiddenException;
import toollibrary.exception.ResourceNotFoundException;
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
    public List<Tool> getAllTools() {
        return toolDao.findAll();
    }

    // create a tool
    @PostMapping("/{user}")
    public ResponseEntity<Tool> createTool(@PathVariable String user, @RequestBody Tool tool) {
        Member member = memberComponent.checkCredentials(user);

        tool.setOwner(member);
        
        return ResponseEntity.ok(toolDao.save(tool));
    }

    // method to return a single tool by id
    // requested by going to the request mapping url and adding the id of the tool
    // ex: localhost:8080/api/v1/tools/1
    @GetMapping("/{id}")
    public ResponseEntity<Tool> getToolById(@PathVariable long id) {
        Tool tool = toolDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException());
        
        return ResponseEntity.ok(tool);
    }

    // method to update a tool by id
    // this one requires a tool object to be passed in the body of the request
    @PutMapping("/{id}/{user}")
    public ResponseEntity<Tool> updateToolById(
            @PathVariable long id, @PathVariable String user, @RequestBody Tool tool) {
        Tool toolToUpdate = toolDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException());

        Member member = memberComponent.checkCredentials(user);
        Member owner = toolToUpdate.getOwner();

        if (owner == null || owner.getId() != member.getId()) {
            throw new ForbiddenException();
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
        Tool toolToDelete = toolDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException());

        Member member = memberComponent.checkCredentials(user);
        Member owner = toolToDelete.getOwner();

        if (owner == null || owner.getId() != member.getId()) {
            throw new ForbiddenException();
        }

        toolDao.delete(toolToDelete);
        return ResponseEntity.ok().build();
    }
}
