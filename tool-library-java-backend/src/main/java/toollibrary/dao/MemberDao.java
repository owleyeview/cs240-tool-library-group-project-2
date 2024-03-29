package toollibrary.dao;

import toollibrary.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // This annotation indicates that the class provides the mechanism for CRUD operations on objects.
public interface MemberDao extends JpaRepository<Member, Long> {
    public Member findByUsernameAndPasswordHash(String username, String passwordHash);

    public Member findByUsername(String username);
}
