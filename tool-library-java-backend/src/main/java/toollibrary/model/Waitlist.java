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
    @JoinColumn
    private Tool tool;

    // if the user is deleted, delete this too
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(optional = false)
    @JoinColumn
    private Member member;

    public Waitlist() { }

    public Waitlist(Tool tool, Member member) {
        if (tool == null || member == null) {
            throw new NullPointerException();
        }
        this.tool = tool;
        this.member = member;
    }

    public long getId() {
        return id;
    }

    public Tool getTool() {
        return tool;
    }

    public Member getMember() {
        return member;
    }
}
