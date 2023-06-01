package toollibrary.api;

import toollibrary.dao.ToolDao;
import toollibrary.dao.WaitlistDao;
import toollibrary.model.Tool;
import toollibrary.model.Waitlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*") // allows requests from any origin
@RestController
@RequestMapping("/api/v1/waitlist")
public class WaitlistController {
    @Autowired
    private ToolDao toolDao;
    @Autowired
    private WaitlistDao waitlistDao;

    @PostMapping("/reserve/{id}/{user}")
    public void reserve(@PathVariable long id, @PathVariable String user) {
        Tool tool = toolDao.getReferenceById(id);
        Waitlist waitlist = new Waitlist(tool); // TODO add user
        waitlistDao.save(waitlist);
    }

    @PostMapping("/checkout/{id}/{user}")
    public void checkout(@PathVariable long id, @PathVariable String user) {
        // TODO check if the user is next in line for that tool
        // TODO check the tool out to the user
        waitlistDao.dequeue(id);
    }
}
