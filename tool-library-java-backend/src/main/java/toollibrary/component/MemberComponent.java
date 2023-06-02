package toollibrary.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import toollibrary.dao.MemberDao;
import toollibrary.model.Member;

@Component
public class MemberComponent {
    @Autowired
    private MemberDao memberDao;

    public Member checkCredentials(String credentials) {
        // TODO make sure credentials are valid
        // this should probably be implemented with a JWT

        return memberDao.findById(Long.parseLong(credentials)).orElse(null);
    }

    public Long addUser(String username, String password) {
        try {
            Member member = new Member(username, password);
            memberDao.save(member);
            return member.getId();
        } catch (DataIntegrityViolationException e) {
            // there is a duplicate username
            return null;
        }
    }
}
