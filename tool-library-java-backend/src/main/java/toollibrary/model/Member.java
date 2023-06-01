package toollibrary.model;


import jakarta.persistence.*;
import toollibrary.api.passwordEnc;

// Member object that stores the information for each member
@Entity // This annotation indicates that the class is an entity and is mapped to a database table.
@Table(name = "members") // The name of the database table to be used for these objects.
public class Member {

    @Id // indicating that this is the primary key in the database
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    public Member(String username, String password) {
        this.username = username;
        this.passwordHash = passwordEnc.getHashSHA1(password);
    }

    public Member(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
