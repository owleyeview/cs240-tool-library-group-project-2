package toollibrary.model;


import jakarta.persistence.*;

// Tool object that stores the information for each tool
@Entity // This annotation indicates that the class is an entity and is mapped to a database table.
@Table(name = "tools") // The name of the database table to be used for these objects.
public class Tool {

    @Id // indicating that this is the primary key in the database
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tool_name")
    private String toolName;

    @Column(name = "tool_description")
    private String toolDescription;

    @Column(name = "tool_category")
    private String toolCategory;

    @Column(name = "tool_location")
    private String toolLocation;

    @ManyToOne
    @JoinColumn
    private Member owner;

    @ManyToOne
    @JoinColumn
    private Member checkedOutTo;

    // Constructors

    // 5 parameter constructor
    public Tool(String toolName, String toolDescription, String toolCategory, String toolLocation) {
        this.toolName = toolName;
        this.toolDescription = toolDescription;
        this.toolCategory = toolCategory;
        this.toolLocation = toolLocation;
    }

    // default constructor
    public Tool() {

    }

    public long getId() {
        return id;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String name) {
        this.toolName = name;
    }

    public String getToolDescription() {
        return toolDescription;
    }

    public void setToolDescription(String description) {
        this.toolDescription = description;
    }

    public String getToolCategory() {
        return toolCategory;
    }

    public void setToolCategory(String category) {
        this.toolCategory = category;
    }

    public String getToolLocation() {
        return toolLocation;
    }

    public void setToolLocation(String location) {
        this.toolLocation = location;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public Member getCheckedOutTo() {
        return checkedOutTo;
    }

    public void setCheckedOutTo(Member checkedOutTo) {
        this.checkedOutTo = checkedOutTo;
    }

    public boolean isToolIsAvailable() {
        // TODO have it check if it is availible
        return true;
    }
}
