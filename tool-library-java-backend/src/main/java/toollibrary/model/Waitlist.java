package toollibrary.model;

import jakarta.persistence.*;

// This class is a queue for keeping track of who gets the tool next

@Entity
@Table(name = "waitlist")
public class Waitlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // who gets the tool after the user
    @OneToOne
    @JoinColumn(name = "next")
    private Waitlist next;

    // TODO add a column for the user
}
