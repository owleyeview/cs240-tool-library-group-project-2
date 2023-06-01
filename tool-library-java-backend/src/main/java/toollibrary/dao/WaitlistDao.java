package toollibrary.dao;

import toollibrary.model.Waitlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository // This annotation indicates that the class provides the mechanism for CRUD operations on objects.
public interface WaitlistDao extends JpaRepository<Waitlist, Long> {
    /** Deletes the oldest row where the tool has specified id */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM waitlist WHERE tool_id = :tool ORDER BY id LIMIT 1", nativeQuery = true)
    public void dequeue(@Param("tool") long tool);
}
