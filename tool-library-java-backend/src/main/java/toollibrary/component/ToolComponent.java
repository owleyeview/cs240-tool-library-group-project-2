package toollibrary.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import toollibrary.dao.WaitlistDao;
import toollibrary.model.Member;
import toollibrary.model.Tool;

@Component
public class ToolComponent {
    @Autowired
    private WaitlistDao waitlistDao;

    /** @return JSON serializable information about a member's relationship with a tool */
    public ToolData getToolData(Tool tool, Member member) {
        return new ToolData(
            tool,
            tool.getCheckedOutTo() == null && !waitlistDao.existsByToolId(tool.getId()),
            member != null && tool.getOwner() != null &&
                member.getId() == tool.getOwner().getId(),
            member != null && tool.getCheckedOutTo() != null &&
                tool.getCheckedOutTo().getId() == member.getId());
    }
}
