package toollibrary.datastructure;

import toollibrary.model.Tool;

// A node to hold a tool object in a linked list
public class ToolNode {

    // The tool object
    private Tool tool;
    protected ToolNode next;

    // Constructor
    public ToolNode(Tool tool) {
        this.tool = tool;
        this.next = null;
    }

    // Getters and setters
    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }
}
