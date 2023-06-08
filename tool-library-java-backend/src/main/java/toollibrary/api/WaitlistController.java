package toollibrary.api;

import toollibrary.component.MemberComponent;
import toollibrary.dao.ToolDao;
import toollibrary.dao.WaitlistDao;
import toollibrary.model.Member;
import toollibrary.model.Tool;
import toollibrary.model.Waitlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*") // allows requests from any origin
@RestController
@RequestMapping("/api/v1/waitlist")
public class WaitlistController {
    @Autowired
    private MemberComponent memberComponent;

    @Autowired
    private ToolDao toolDao;
    @Autowired
    private WaitlistDao waitlistDao;

    // reserve a tool
    @PostMapping("/reserve/{id}")
    public ResponseEntity<Void> reserve(
            @PathVariable long id, @RequestHeader("Authorization") String user) {
        Tool tool = toolDao.findById(id).orElse(null);
        Member member = memberComponent.checkCredentials(user);
        if (member == null) {
            // http 401: the user did not provide valid credentials
            return ResponseEntity.status(401).build();
        }
        if (tool == null) {
            // http 404: tool not found
            return ResponseEntity.status(404).build();
        }
        waitlistDao.save(new Waitlist(tool, member));
        // http 204: success with no content
        return ResponseEntity.status(204).build();
    }

    // checkout a tool to a user
    @PostMapping("/checkout/{id}")
    public ResponseEntity<Void> checkout(
            @PathVariable long id, @RequestHeader("Authorization") String user) {
        Tool tool = toolDao.findById(id).orElse(null);

        if (tool == null) {
            return ResponseEntity.status(404).build();
        }
    
        if (tool.getCheckedOutTo() == null) {
            Member member = memberComponent.checkCredentials(user);

            if (member == null) {
                // http 401: the user did not provide valid credentials
                return ResponseEntity.status(401).build();
            }

            Waitlist waitlist = waitlistDao.getMin(id);

            if (waitlist == null || waitlist.getMember().getId() == member.getId()) {
                tool.setCheckedOutTo(member);
                if (waitlist != null) {
                    waitlistDao.delete(waitlist);
                }
                // http 204: success with no content
                return ResponseEntity.status(204).build();
            }
        }
       
        // http 403: the user is forbidden to check out a tool when they are not next in line
        return ResponseEntity.status(403).build();
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<Void> returnTool(
            @PathVariable long id, @RequestHeader("Authorization") String user) {
        Tool tool = toolDao.findById(id).orElse(null);
        Member member = memberComponent.checkCredentials(user);

        if (member == null) {
            // http 401: the user did not provide valid credentials
            return ResponseEntity.status(401).build();
        }

        if (tool == null) {
            // http 404: tool not found
            return ResponseEntity.status(404).build();
        }

        Member checkedOutTo = tool.getCheckedOutTo();
        if (checkedOutTo != null && checkedOutTo.getId() == member.getId()) {
            tool.setCheckedOutTo(null);
            toolDao.save(tool);
            // http 204: success with no content
            return ResponseEntity.status(204).build();
        }

        // http 403: the user is forbidden to check out a tool that is checked out to someone else
        return ResponseEntity.status(403).build();
    }
}
