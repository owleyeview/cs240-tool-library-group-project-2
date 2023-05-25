package toollibrary.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

// Objects of this class are nodes in a queue for keeping track of who gets the tool next

@Entity
@Table(name = "waitlist")
public class Waitlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // if the tool is deleted, delete this too
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(optional = false)
    private Tool tool;

    // TODO add a column for the user

    public Waitlist(Tool tool) {
        if (tool == null) {
            throw new NullPointerException();
        }
        this.tool = tool;
    }

    public long getId() {
        return id;
    }

    public Tool getTool() {
        return tool;
    }
}
