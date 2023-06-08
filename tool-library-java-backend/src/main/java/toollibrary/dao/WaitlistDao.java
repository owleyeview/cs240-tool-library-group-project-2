package toollibrary.dao;

import toollibrary.model.Waitlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository // This annotation indicates that the class provides the mechanism for CRUD operations on objects.
public interface WaitlistDao extends JpaRepository<Waitlist, Long> {
    /** Selects the oldest row where the tool has specified id */
    @Query(value = "SELECT * FROM waitlist WHERE tool_id = :tool ORDER BY id LIMIT 1", nativeQuery = true)
    public Waitlist getMin(@Param("tool") long tool);

    public boolean existsByToolId(long id);
}
