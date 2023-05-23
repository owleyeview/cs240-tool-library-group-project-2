package toollibrary.service;

import toollibrary.model.Tool;
import java.util.List;

public interface ToolService {
    List<Tool> getAllTools(); // this should be changed to return our own data structure
    Tool createTool(Tool tool);
    Tool getToolById(long id);
    Tool updateToolById(long id, Tool tool);
    void deleteToolById(long id);
}
