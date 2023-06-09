package toollibrary.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import toollibrary.dao.WaitlistDao;
import toollibrary.model.Member;
import toollibrary.model.Tool;
import toollibrary.model.Waitlist;

@Component
public class ToolComponent {
    @Autowired
    private WaitlistDao waitlistDao;

    /** @return JSON serializable information about a member's relationship with a tool */
    public ToolData getToolData(Tool tool, Member member) {
        Waitlist reservation = waitlistDao.getMin(tool.getId());
        long you = member == null ? -1 : member.getId();
        return new ToolData(
            tool,
            tool.getCheckedOutTo() == null &&
                (reservation == null || reservation.getMember().getId() == you),
            tool.getOwner() != null && tool.getOwner().getId() == you,
            tool.getCheckedOutTo() != null && tool.getCheckedOutTo().getId() == you);
    }
}
