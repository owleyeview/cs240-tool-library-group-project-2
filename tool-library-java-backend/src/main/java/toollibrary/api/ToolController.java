package toollibrary.api;

import toollibrary.component.MemberComponent;
import toollibrary.component.ToolComponent;
import toollibrary.component.ToolData;
import toollibrary.dao.ToolDao;
import toollibrary.exception.ForbiddenException;
import toollibrary.exception.ResourceNotFoundException;
import toollibrary.model.Member;
import toollibrary.model.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*") // allows requests from any origin
@RestController
@RequestMapping("/api/v1/tools")
public class ToolController {
    @Autowired
    private ToolDao toolDao;

    @Autowired
    private MemberComponent memberComponent;

    @Autowired
    private ToolComponent toolComponent;

    // get all tools
    @GetMapping
    public List<ToolData> getAllTools(@RequestHeader("Authorization") String user) { 
        Member member = memberComponent.findMember(user);       
        List<ToolData> tools = new ArrayList<ToolData>();

        for (Tool tool : toolDao.findAll()) {
            tools.add(toolComponent.getToolData(tool, member));
        }

        return tools;
    }

    // create a tool
    @PostMapping
    public ResponseEntity<Tool> createTool(
            @RequestHeader("Authorization") String user, @RequestBody Tool tool) {
        Member member = memberComponent.checkCredentials(user);

        tool.setOwner(member);
        
        return ResponseEntity.ok(toolDao.save(tool));
    }

    // method to return a single tool by id
    // requested by going to the request mapping url and adding the id of the tool
    // ex: localhost:8080/api/v1/tools/1
    @GetMapping("/{id}")
    public ResponseEntity<ToolData> getToolById(
            @PathVariable long id, @RequestHeader("Authorization") String user) {
        Tool tool = toolDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException());
        Member member = memberComponent.findMember(user);
        return ResponseEntity.ok(toolComponent.getToolData(tool, member));
    }

    // method to update a tool by id
    // this one requires a tool object to be passed in the body of the request
    @PutMapping("/{id}")
    public ResponseEntity<Tool> updateToolById(
            @PathVariable long id,
            @RequestHeader("Authorization") String user,
            @RequestBody Tool tool) {

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
    @DeleteMapping("/{id}")
    public ResponseEntity<Tool> deleteToolById(
            @PathVariable long id, @RequestHeader("Authorization") String user) {
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
