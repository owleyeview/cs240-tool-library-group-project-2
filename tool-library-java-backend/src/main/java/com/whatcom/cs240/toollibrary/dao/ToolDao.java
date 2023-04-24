package com.whatcom.cs240.toollibrary.dao;

import com.whatcom.cs240.toollibrary.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // This annotation indicates that the class provides the mechanism for CRUD operations on objects.
public interface ToolDao extends JpaRepository<Tool, Long> {

}
