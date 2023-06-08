package toollibrary.component;
import toollibrary.model.Tool;

// This class contains information about a tool,
// along with information about whether the tool can be checked out
// and whether the tool belongs to the user asking about it
public class ToolData {
    private Tool tool;
    private boolean available;
    private boolean mine;
    private boolean canReturn;

    public ToolData(Tool tool, boolean available, boolean mine, boolean canReturn) {
        this.tool = tool;
        this.available = available;
        this.mine = mine;
        this.canReturn = canReturn;
    }

    public Tool getTool() {
        return tool;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean isMine() {
        return mine;
    }

    public boolean isCanReturn() {
        return canReturn;
    }
}
